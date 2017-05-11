package com.ecar.epark.edroidloaer.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DroidSpManager {
	private final String PREFS_NAME="com.ecar.epark.edroidloaer.db.dbs";
	private DroidSpManager dbPreferences;
	private SharedPreferences shareData;

	public final String SpCurrentLocationCityCode = "current_location_city_code";

	public DroidSpManager(Context context){
		shareData = context.getSharedPreferences(PREFS_NAME, 0);
	}


	public void setJarVersionByName(String jarName,String jarVersion) {
		Editor editor = shareData.edit();
		editor.putString(jarName, jarVersion);
		editor.commit();
	}

	public String getJarVersionByName(String jarName) {
		String jarVersion = shareData.getString(jarName, "");
		return jarVersion;
	}



}
