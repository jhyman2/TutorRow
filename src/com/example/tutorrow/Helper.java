package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class Helper {
	
	public static User readUserFromFile(String filePath){
		FileInputStream courseFile = null;
		File file = null;
		ObjectInputStream courseObj = null;

		try{
			file = new File(filePath);

			if (!file.exists()) {
				Log.d("noFile", "wtttttttf");
				Log.d("filepath", filePath + "");
				Log.d("fileee", file.exists() + "");
				return null;
			}
			Log.d("file", "Exists?");
			courseFile = new FileInputStream(filePath);
			courseObj = new ObjectInputStream(courseFile);
			User u = (User)courseObj.readObject();
			courseObj.close();
			return u;

		}catch(Exception e){
			Log.d("Read File", "Uh oh: " + e.getMessage());
			return null;
		}finally{
			try {
				courseObj.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("IOException", e.getMessage());
				return null;
			}
		}
	}
	
	public static String writeToFile(User u, String filePath){
		String s = "";
		
		FileOutputStream courseFile = null;
		ObjectOutputStream courseObj = null;
		File file = null;
		try{
//				String filePath = this.getFilesDir().getPath().toString() + userDataFile;
				file = new File(filePath);
				if (!file.exists()){
					if (!file.createNewFile()) {
						s += "File could not be created";
					}
				}
				courseFile = new FileOutputStream(filePath);
				courseObj = new ObjectOutputStream(courseFile);
				
		        if (courseObj != null && courseFile != null){
					courseObj.writeObject(u);
		        }else{
		        	s += "There was an error with the file";
		        }
				
			
		}catch(Exception e){
			s += e.toString();
		}finally{
			try {
				courseObj.close();
				courseFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				s += "closing error";
			}
		}
		
		return s + " finished writing.";
	}
	
}
