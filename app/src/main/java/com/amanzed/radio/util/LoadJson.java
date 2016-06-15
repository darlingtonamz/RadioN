package com.amanzed.radio.util;

import android.util.Log;
import android.widget.Toast;

import com.amanzed.radio.db.DBHelper;
import com.amanzed.radio.list.Radio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;import java.lang.String;
import java.util.ArrayList;


public class LoadJson {
    String serverData = "";
    JSONObject jsonObject;
    public LoadJson(String str){
        serverData = str;
    }
    public void getJson(){
        try {
            jsonObject = new JSONObject(serverData);
            Log.e("radion", "JSON: "+ jsonObject);
        }catch (JSONException e){
            Log.e("radion", "ERROR: "+ e +"\n"+serverData);
        }
    }

    public ArrayList<Radio> populate(DBHelper mydb) {
        try {
            JSONArray response = jsonObject.getJSONArray("radios");
            int n = response.length();
            if (response.length() > mydb.numberOfRadioRows()) {
                Log.d("radio", "Larger source: source:" + response);
                mydb.deleteAll();
                //radioList.clear();
                for (int i = 0; i < n; i++) {
                    JSONObject f = (JSONObject) response.get(i);
                    mydb.insertRadioWithID(f.getString("id"),
                            f.getString("title"),
                            f.getString("link1"),
                            f.getString("link2"),
                            f.getString("description"),
                            "Nigeria");
                    /*Radio rd = new Radio(Integer.valueOf(f.getString("id")),
                            f.getString("name"),
                            f.getString("url"),
                            f.getString("website"),
                            f.getString("about"),
                            f.getString("country"));*/
                }
                //Toast.makeText(this, "Radio stations loaded...", Toast.LENGTH_SHORT).show()
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mydb.getAllRadios();
    }
}
