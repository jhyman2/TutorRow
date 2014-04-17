package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.app.Activity;
import android.content.Intent;
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
	private static String signInURL = "http://www.tutorapp.herobo.com/login.php";
	public User appUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		userName = (EditText)findViewById(R.id.etEmailLogin);
		passWord = (EditText)findViewById(R.id.etPassword);
		btnLogin.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
		//h.getData();
	}
	
	public void startIntent(){
		Intent i2 = new Intent(this, Dashboard.class);
		startActivity(i2);
		finish();
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
	                    
	                    appUser = new User(json.getString("id"), json.getString("name"), json.getString("email")); // CHECK THIS WITH PHIL
	                    
	                    JSONArray jArray = json.getJSONArray("studentClasses");
	                //    JSONObject jsonClassName = jArray.getJSONObject(0);
	                    
	                    if (jArray != null){
	                    	for (int i = 0; i < jArray.length(); i++){
	                    		Log.d("Classes", jArray.getJSONObject(i).getString("name"));
	                    		Log.d("number", jArray.getJSONObject(i).getString("number"));
	                    		Log.d("dep", jArray.getJSONObject(i).getString("department"));
	                    		appUser.addClassStudent(jArray.getJSONObject(i).getString("name"), jArray.getJSONObject(i).getString("number"), jArray.getJSONObject(i).getString("description"));
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
