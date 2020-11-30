package com.a1techandroid.studentconsultant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.gson.Gson;

public class SharedPrefrences {

    private static final int MODE_PRIVATE = 0;

    public static void setUserType(int userType, Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userType", userType);
        editor.apply();
    }

    public static int getUserType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        int type = prefs.getInt("name", 0);
        return type;
    }

    public static void saveUSer(UserModel model, Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public static UserModel getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        UserModel obj = gson.fromJson(json, UserModel.class);
        return obj;
    }
}