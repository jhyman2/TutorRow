package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tutorrow.SignUpActivity.RegisterUser;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This is the starting point of the app. 
 */
public class MainActivity extends Activity implements OnClickListener {

	Button btnLogin, btnSignUp;
	//Helper h = new Helper();
	EditText userName, passWord;
	boolean globalSuccess = false;
	JSONParser jsonParser = new JSONParser();
//	private static String signInURL = "http://www.tutorapp.herobo.com/login.php";
	private static String signInURL = "http://www.tutorrow.com/tutorrow/login.php";
	//public static User appUser;
	SharedPreferences prefs;
	private static String userDataFile;
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//userDataFile = this.getFilesDir().getPath().toString() + "/datafile.txt";
		//if (Helper.readUserFromFile(userDataFile) != null){
		
		//TODO: NEED TO FIX THIS SO THAT IT CHECKS FOR USER ALREADY LOGGED IN (COOKIE'S N CREME)
	/*	if (readFromFile() != null){
			Log.d("Before intent", "Prior to calling");
			startIntent();
		}
	*/	Log.d("After if statement", "After");
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		userName = (EditText)findViewById(R.id.etEmailLogin);
		passWord = (EditText)findViewById(R.id.etPassword);
		btnLogin.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}
	
	public void startIntent(){
		Log.d("Beginning of start", "Intent");
		if (u != null){
			Log.d("In U", "Utero");
			Intent i2 = new Intent(this, Dashboard.class);
			Log.d("pre-starting", "before act 2");
			startActivity(i2);
			Log.d("Started", "Activity 2");
			finish();
		} else{
			Toast.makeText(getApplicationContext(), "User not found???", Toast.LENGTH_LONG).show();
		}
	}
	
	public User readFromFile(){
		FileInputStream courseFile = null;
		File file = null;
		try {
			String filePath = this.getFilesDir().getPath().toString() + "/datafile.txt";
		    file = new File(filePath);
		    if (!file.exists()) {
		    	Log.d("shit", "Jesus");
		    	startIntent();
		    }
			courseFile = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
        	Log.d("shit", "1");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("shit", "2");
				e.printStackTrace();
		}
		ObjectInputStream courseObj = null;
		try {
			courseObj = new ObjectInputStream(courseFile);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			Log.d("shit", "3");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("shit", "4");
			e.printStackTrace();
		}
		try {
			User u = (User)courseObj.readObject();
			return u;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			Log.d("shit", "5");
		}
		return null;
	}

	
	
	public String writeToFile(User u){
		String s = "";
		
		FileOutputStream courseFile = null;
		ObjectOutputStream courseObj = null;
		File file = null;
		try{
				String filePath = this.getFilesDir().getPath().toString() + "/datafile.txt";
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
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.btnLogin:
			if (userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty())
			{
				Log.d("Blank", "true");
				globalSuccess = false;
			}
			else
			{
				Log.d("Blank", "false");
				AsyncTask<String, String, String> s = new LoginUser().execute();
			}
			
			break;
			
		case R.id.btnSignUp:
			Intent signUpIntent = new Intent(this, SignUpActivity.class);
			startActivity(signUpIntent);
			break;
			}

			
		}
		
	class LoginUser extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
			 String name1 = userName.getText().toString();
	         String password1 = passWord.getText().toString();
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         // TODO: MD5 or SHA256 the Password
	         	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	            params1.add(new BasicNameValuePair("email", name1));
	            params1.add(new BasicNameValuePair("password", password1));
	            Log.d("Before hits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(signInURL,
	                    "POST", params1);
	            Log.d("Create Response", json.toString());
	            
	            try {
	            	Log.d("json shit", json.toString());
	            	String success = json.toString();
	                if (success.contains("1")) {
	                    // successful
	                	globalSuccess = true;
	                    Log.d("Try", "success");	    	     	                    
	                    
	                    String id = json.getString("id");
	                    Log.d("ID_ANDSTUFF", id);
	                    String phone = json.getString("phone");
	                    Log.d("phone", phone);
	                    String email = json.getString("email");
	                    Log.d("email", email);
	                    String name = json.getString("name");
	                    Log.d("name", name);
	                    String username = json.getString("username");
	                    Log.d("username", username);
	                    JSONArray jStudentArray = json.getJSONArray("studentClasses");
	                    JSONArray jTutorArray = json.getJSONArray("tutorClasses");
	                //    JSONObject jsonClassName = jArray.getJSONObject(0);
	                    //	public User(String id, String fullName, String email, String phone, String username){
	                    u = new User(id, name, email, phone, username);
	                    if (jStudentArray != null){
	                    	for (int i = 0; i < jStudentArray.length(); i++){
	                    		Log.d("Classes", jStudentArray.getJSONObject(i).getString("name"));
	                    		Log.d("number", jStudentArray.getJSONObject(i).getString("number"));
	                    		Log.d("dep", jStudentArray.getJSONObject(i).getString("department"));
	                    		u.addClassStudent(jStudentArray.getJSONObject(i).getString("name"), jStudentArray.getJSONObject(i).getString("number"), jStudentArray.getJSONObject(i).getString("department"));
	                    	}
	                    } 
	                    if (jTutorArray != null){
	                    	for (int i = 0; i < jTutorArray.length(); i++){
	                    		Log.d("Classes", jTutorArray.getJSONObject(i).getString("name"));
	                    		Log.d("number", jTutorArray.getJSONObject(i).getString("number"));
	                    		Log.d("dep", jTutorArray.getJSONObject(i).getString("department"));
	                    		u.addClassTutor(jTutorArray.getJSONObject(i).getString("name"), jTutorArray.getJSONObject(i).getString("number"), jTutorArray.getJSONObject(i).getString("department"));
	                    	}
	                    }
	                    
	                    
//	                    Log.d("COCK", jsonClassName.getString("name"));
	                  
	                    
	                   /* List<String> jShit = new ArrayList<String>();
	                    JSONObject userJSON = json.getJSONObject("user");
	                    String jsonID = userJSON.getString("id");
	                    String jsonEmail = userJSON.getString("email");
	                    String jsonPhone = userJSON.getString("phone");
	                    String jsonName = userJSON.getString("name");
	                    String jsonUsername = userJSON.getString("username");
	                    
	                    Log.d("phpID", jsonID+"");
	                    Log.d("phpEmail",jsonEmail+""); 
	                    Log.d("phpPhone", jsonPhone+"");
	                    Log.d("phpName",jsonName+""); 
	                    Log.d("phpUser", jsonUsername+"");*/
	                    //Toast.makeText(getApplicationContext(), "ID: " + jsonID, Toast.LENGTH_LONG).show();
	                   /* String jsonUsername = json.getString("username");
	                    String jsonEmail = json.getString("email");
	                    String jsonPhone = json.getString("phone");
	                    
	                    Toast.makeText(getApplicationContext(), "ID: " + jsonID + " User: " + jsonUsername, Toast.LENGTH_LONG).show();
	                    */
	                    // GET DATA FROM PHP FILE AND PUT EXTRAS INTO DASHBOARD
	                    return "success";
	                } else {
	                    // failed
	    				Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
	    				globalSuccess = false;
	                	Log.d("Try", "Fail");
	                	return "failure";
	                }
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());
	            	globalSuccess = false;
	            	return "failure";
	            }	
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (globalSuccess){
				Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                writeToFile(u);
				startIntent();
			}else{
				Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}	
	}		
}
