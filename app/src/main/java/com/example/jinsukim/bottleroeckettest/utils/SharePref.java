package com.example.jinsukim.bottleroeckettest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static SharePref sharePref = new SharePref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String CACHE_STROE_DATA = "CACHE_STROE_DATA";

    private SharePref() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static SharePref getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void saveStoreData(String data) {
        editor.putString(CACHE_STROE_DATA, data);
        editor.commit();
    }

    public String getStoreData() {
        return sharedPreferences.getString(CACHE_STROE_DATA, "");
    }


    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
