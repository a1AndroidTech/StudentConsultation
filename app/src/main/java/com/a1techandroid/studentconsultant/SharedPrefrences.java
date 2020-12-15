package com.a1techandroid.studentconsultant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.a1techandroid.studentconsultant.Models.RequestModel;
import com.a1techandroid.studentconsultant.Models.StudentProfileModel;
import com.a1techandroid.studentconsultant.Models.UserModel;
import com.google.gson.Gson;

public class SharedPrefrences {

    private static final int MODE_PRIVATE = 0;



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

    public static void saveUSerProfile(StudentProfileModel model, Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("stProfile", json);
        editor.apply();
    }

    public static StudentProfileModel getUserProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("stProfile", 0);
        Gson gson = new Gson();
        String json = prefs.getString("stProfile", "");
        StudentProfileModel obj = gson.fromJson(json, StudentProfileModel.class);
        return obj;
    }

     public static void saveApprovedREquest(RequestModel model, Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("req", json);
        editor.apply();
    }

    public static RequestModel getApproved(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("req", 0);
        Gson gson = new Gson();
        String json = prefs.getString("req", "");
        RequestModel obj = gson.fromJson(json, RequestModel.class);
        return obj;
    }


}
