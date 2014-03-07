package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

public class Helper {
	
	public static String data = "";
	
	public void getData(){
		new Thread(new Runnable() {
		    public void run() {
		JSONArray jArray = null;

		String result = null;

		StringBuilder sb = null;

		InputStream is = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();

		     //Why to use 10.0.2.2
		     HttpPost httppost = new HttpPost("http://www.tutorapp.herobo.com/myFile.php");
		     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		     }catch(Exception e){
		    	// data += "Error converting result "+e.toString();
		         Log.e("log_tag", "Error in http connection"+e.toString());
		    }
		//convert response to string
		try{
		      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		       sb = new StringBuilder();
		       sb.append(reader.readLine() + "\n");

		       String line="0";
		       while ((line = reader.readLine()) != null) {
		                      sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		        }catch(Exception e){
    			//       data += "Error converting result "+e.toString();
		              Log.e("log_tag", "Error converting result "+e.toString());
		        }

		try{
		      jArray = new JSONArray(result);
		      JSONObject json_data=null;
			  data = "";
		      for(int i=0;i<jArray.length();i++){
		             json_data = jArray.getJSONObject(i);
		             data +=json_data.getString("username") + json_data.getString("password") + json_data.getString("email") + json_data.getString("phone") + json_data.getString("name");//here "Name" is the column name in database
		         }
		      System.out.println(jArray.length());
		      System.out.println(json_data.getString("password"));
		      }
		      catch(JSONException e1){
		       ;
		      } catch (ParseException e1) {
		   e1.printStackTrace();
		 }
		    }}).start();
		
	}

}
