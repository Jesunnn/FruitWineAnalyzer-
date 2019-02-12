package com.example.jesunn.twinez;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONfunctions {
    private static final String ERROR_TAG = "E_JSONGfunctions";
    private static final String DEBUG_TAG = "D_JSONGfunctions";

    public static JSONObject getUpdateWineData(String ph, String alcohol_content, String temperature, String volatile_acid) {
        try{
            JSONObject temp = new JSONObject();
            temp.put("ph", ph);
            temp.put("alcohol_content", alcohol_content);
            temp.put("temperature", temperature);
            temp.put("volatile_acid", volatile_acid);
            Log.d(DEBUG_TAG, "JSON Update Object Created");
            return temp;
        }catch(JSONException ex){
            Log.e(ERROR_TAG, "Something went wrong with JSON data creation");
        }
        return null;
    }

}
