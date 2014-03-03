package se.netdev.allakartor;

import se.netdev.allakartor.managers.UserLocationManager;

import com.sogeti.droidnetworking.NetworkEngine;

import android.app.Application;

public class ApplicationEx extends Application {
	public static final String API_URL = "http://www.allakartor.se/api";
	public static final String SECURE_API_URL = "https://www.allakartor.se/api";
	public static final String API_KEY = "<Your API Key>";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		NetworkEngine.getInstance().setUseCache(true);
		NetworkEngine.getInstance().init(this);
		
		UserLocationManager.getInstance().init(this);
	}
}
