package com.example.weather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherAsync extends AsyncTask<Void, Void, ArrayList<Item>> {
	private Context mContext;

    public WeatherAsync(Context context) {
        mContext = context;
    }
	
	public ArrayList<Item> doInBackground(Void... params) {
		ArrayList<Item> result = null;
		try {
			result = getHourlyForcast(getLocation());
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private ArrayList<Item> getHourlyForcast(Map<String, String> location) throws IOException, ParseException{
		MySQLiteHelper dbHandler = new MySQLiteHelper(mContext, null, null, 1);
		ArrayList<Item> dbItems = dbHandler.getItems();
		String Time;
		boolean hourly = true;
		for(Item item : dbItems){
			Time = item.getTime();
			Time = Time.replaceFirst(" (?<=(AM|PM) ).*?(?=on )on", ",");
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a, MMMM d, yyyy");
			Date d1 = formatter.parse(Time);
			Date d2 = new Date();
			if(d2.getTime()>d1.getTime()){
				hourly = false;
				break;
			}
		}
		
		if(!hourly && !dbItems.isEmpty()){
			ArrayList<Item> returnItems = new ArrayList<Item>();
			for(Item item : dbItems){
				item.setIcon(getBitmap(item.getIconUrl()));
				returnItems.add(item);
			}
			Log.i("WOO", "WOOOWOOW");
			return returnItems;
		}
		if(!dbItems.isEmpty()){
			Log.i("WHOA", "WOWEE");
			dbHandler.deleteAll(mContext);
		}
		String key = "aa5c2938f09029f6";
		String sURL = String.format("http://api.wunderground.com/api/%s/hourly/q/%s/%s.json",key,location.get("state"), location.get("city"));
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject rootobj = root.getAsJsonObject();
		ArrayList<Item> items = new ArrayList<Item>();
		for(JsonElement hour:rootobj.get("hourly_forecast").getAsJsonArray()){
			String time = ((JsonObject) hour).get("FCTTIME").getAsJsonObject().get("pretty").getAsString();
			String temp = ((JsonObject) hour).get("temp").getAsJsonObject().get("english").getAsString();
			String humidity = ((JsonObject) hour).get("humidity").getAsString();
			String condition = ((JsonObject) hour).get("condition").getAsString();
			String iconUrl = ((JsonObject) hour).get("icon_url").getAsString();
			Bitmap icon = getBitmap(iconUrl);
			Item item = new Item(temp, humidity, time, condition, icon, iconUrl);
			items.add(item);
			dbHandler.addItem(item);
		}
		return items;
	}
	
	private Bitmap getBitmap(String url){
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
	}
	
	private Map<String, String> getLocation() throws IOException{
		String key = "aa5c2938f09029f6";
		String sURL = String.format("http://api.wunderground.com/api/%s/geolookup/q/autoip.json", key);
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject rootobj = root.getAsJsonObject();
		
		String city = rootobj.get("location").getAsJsonObject().get("city").getAsString();
		String state = rootobj.get("location").getAsJsonObject().get("state").getAsString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("city", city);
		map.put("state", state);
		return map;
	}
}