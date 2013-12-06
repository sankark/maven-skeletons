package common.util;

import gherkin.deps.com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class ResponseParser {

	public static Map<String, String> parseJson(String response) throws Exception {
		Map<String, String> responseMap = null;
		try{
	      JSONObject jsonObject = new JSONObject(response);
	      Iterator<String> iterator = jsonObject.keys();
	      
	      if(iterator != null) {
	    	 responseMap = new HashMap<String, String>(); 
	         while(iterator.hasNext()) {
	            String key = (String) iterator.next();
	            String value = jsonObject.get(key).toString();
	            responseMap.put(key, value);
	         }

	      }
		} catch(JSONException jsonException) {
			    throw jsonException;
		} catch(Exception exception) {
             throw exception;
		}
		return responseMap;
	}
}
