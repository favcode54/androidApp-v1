package org.favcode54.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Intija on 4/15/2018.
 */

public class PersistentStorageUtils {

    private Context context;

    public PersistentStorageUtils(Context context){ this.context = context; }

    //Used to store simple values with keys. e.g user ID
    public void quick_store(String key, String value){
        if(key == null || value == null){   return; }
        SharedPreferences sp = context.getSharedPreferences("general", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    //Used to retrieve simple values stored with the quick_store function above
    public String quick_retrieve(String key){
        if(key == null){    return "";  }
        return context.getSharedPreferences("general", Context.MODE_PRIVATE).getString(key, "");
    }

    public static String getAppVersion(Context context){
        try {

            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName.trim();

        }catch (Exception e){
            return "";
        }
    }

    public void cacheUserDetails(JsonObject details) {
        quick_store("user_details", details.toString());
    }
    public JsonObject getUserDetails(){
        String temp = quick_retrieve("user_details");

        if(!temp.isEmpty() && temp.contains("{")){
            return new JsonParser().parse(temp).getAsJsonObject();
        }else{
            return new JsonObject();
        }
    }
    public String getFirstName(){
        JsonElement temp = getUserDetails().get("first_name");
        return temp instanceof JsonNull ? "" : temp.getAsString();
    }
    public String getLastName(){
        JsonElement temp = getUserDetails().get("last_name");
        return temp instanceof JsonNull ? "" : temp.getAsString();
    }
    public String getFullName(){
        return String.format("%s %s", getFirstName(), getLastName());
    }
    public String getUserID(){
        return quick_retrieve("user_id");
    }
    public String getProfilePicture(){
        JsonElement pic_name = getUserDetails().get("picture");
        return "https://portal.favcode54.org/account/pictures/" + (pic_name instanceof JsonNull ? "" : pic_name.getAsString());
    }

    public void cacheAllCourses(JsonArray courses){
        quick_store("all_courses", courses.toString());
    }

    public JsonArray getAllCourses(){
        String temp = quick_retrieve("all_courses");

        if(!temp.isEmpty() && temp.contains("{")){
            return new JsonParser().parse(temp).getAsJsonArray();
        }else{
            return new JsonArray();
        }
    }

    public void cacheUserCourses(JsonObject courses){
        quick_store("user_courses", courses.toString());
    }

    public JsonObject getUserCourses(){
        String temp = quick_retrieve("user_courses");

        if(!temp.isEmpty() && temp.contains("{")){
            return new JsonParser().parse(temp).getAsJsonObject();
        }else{
            return new JsonObject();
        }
    }

    public String getUserAddress() {
        JsonObject user_details = getUserDetails();
        JsonElement temp = user_details.get("state"),
                temp2 = user_details.get("country");

        return (temp instanceof JsonNull || temp2 instanceof JsonNull) ? "" : (temp.getAsString() + ", " + temp2.getAsString());
    }

    public boolean isLoggedIn(){
        return !getUserID().isEmpty() && !getFirstName().isEmpty();
    }

    public boolean isFirstRun() {
        if(quick_retrieve("first_run").isEmpty()){
            quick_store("first_run", "nope");
            return true;
        }else{
            return false;
        }
    }
}
