package com.example.weather;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {
	private ArrayList<Item> objects;

	public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, null);
		}

		Item i = objects.get(position);

		if (i != null) {

			TextView timetext = (TextView) v.findViewById(R.id.timetext);
			TextView timetextdata = (TextView) v.findViewById(R.id.timetextdata);
			TextView conditiontext = (TextView) v.findViewById(R.id.conditiontext);
			TextView conditiontextdata = (TextView) v.findViewById(R.id.conditiontextdata);
			TextView temptext = (TextView) v.findViewById(R.id.temptext);
			TextView temptextdata = (TextView) v.findViewById(R.id.temptextdata);
			TextView humiditytext = (TextView) v.findViewById(R.id.humiditytext);
			TextView humiditytextdata = (TextView) v.findViewById(R.id.humiditytextdata);
			ImageView icon = (ImageView) v.findViewById(R.id.icon);

			if (timetext != null){
				timetext.setText("Time: ");
			}
			if (timetextdata != null){
				timetextdata.setText(i.getTime());
			}
			if (conditiontext != null){
				conditiontext.setText("Condition: ");
			}
			if (conditiontextdata != null){
				conditiontextdata.setText(i.getCondition());
			}
			if(temptext != null){
				temptext.setText("Temp: ");
			}
			if (temptextdata != null){
				temptextdata.setText(i.getTemp()+"°");
			}
			if (humiditytext != null){
				humiditytext.setText("Humidity: ");
			}
			if (humiditytextdata != null){
				humiditytextdata.setText(i.getHumidity());
			}
			if (icon != null){
				icon.setImageBitmap(i.getIcon());
			}
		}

		return v;

	}

}
