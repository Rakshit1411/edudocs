package com.example.rakshitsharma.edutiate.GetAllData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.rakshitsharma.edutiate.Authentication.login;

/**
 * Created by Rakshit on 10/8/2017.
 */

public class SaveSettings {
    Context context;
    SharedPreferences sharedPreferences;
    public static String userID="";
    public static String userCode="";
    public SaveSettings(Context context){

        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }
    public void saveUserSettings(String userID,String userCode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID",userID);
        editor.putString("userCode",userCode);
        editor.commit();
        loadSettings();
    }
    public void loadSettings(){
        userID = sharedPreferences.getString("userID","0");
        userCode = sharedPreferences.getString("userCode","0");
    }
    public void erase(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
