package com.example.weather;
import android.graphics.Bitmap;


public class Item {
	private long id;
	private String temp;
	private String humidity;
	private String time;
	private String condition;
	private String iconUrl;
	private Bitmap icon;
	
	public Item(){
		
	}
	public Item(String temp, String humidity, String time, String condition, Bitmap icon, String iconUrl){
		this.temp = temp;
		this.time = time;
		this.humidity = humidity;
		this.condition = condition;
		this.icon = icon;
		this.iconUrl = iconUrl;
	}
	public void setTemp(String temp){
		this.temp = temp;
	}
	public String getTemp() {
		return temp;
	}
	public void setHumidity(String humid) {
		this.humidity = humid;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public void setCondition(String cond) {
		this.condition = cond;
	}
	public String getCondition() {
		return condition;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setIconUrl(String url) {
		this.iconUrl = url;
	}
	public String getIconUrl() {
		return this.iconUrl;
	}
}
