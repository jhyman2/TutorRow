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

public class MainActivity extends Activity implements OnClickListener {

	Button btnLogin, btnSignUp;
	//Helper h = new Helper();
	EditText userName, passWord;
	boolean globalSuccess = true;
	JSONParser jsonParser = new JSONParser();
	private static String signInURL = "http://www.tutorapp.herobo.com/login.php";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		userName = (EditText)findViewById(R.id.etEmailLogin);
		passWord = (EditText)findViewById(R.id.etPassword);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty())
				{
					globalSuccess = false;
				}
				else
				{
					AsyncTask<String, String, String> s = new LoginUser().execute();
				}
				
				if (globalSuccess){
					Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
					Log.d("global success", "ssss");
					startIntent();
				}else{
					Log.d("global fail", "ssss");
					Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
				}
			}
		});
		btnSignUp.setOnClickListener(this);
		//h.getData();
	}
	
	public void startIntent(){
		Intent i2 = new Intent(this, Dashboard.class);
		startActivity(i2);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		/*case R.id.btnLogin:
			String user = userName.getText().toString();
			String pass = passWord.getText().toString();
			// query "SELECT pass from users WHERE username equals user"
			// if (query = pass) then go to dash else Toast("Invalid")
			Intent i2 = new Intent(this, Dashboard.class);
			startActivityForResult(i2, 0);
			break;
			*/
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
	            params1.add(new BasicNameValuePair("username", name1));
	            params1.add(new BasicNameValuePair("password", password1));
	            Log.d("Before tits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(signInURL,
	                    "POST", params1);
	            
	            Log.d("Create Response", json.toString());
	            globalSuccess = false;
	            try {
	            	Log.d("json shit", json.toString());
	                //int success = json.getInt("success");
	            	String success = json.toString();
	                if (success.contains("login")) {
	                    // successful
	                	globalSuccess = true;
	                    Log.d("Try", "success");	    	           
	                    return "success";
	                } else {
	                    // failed
	                	Log.d("Try", "Fail");
	                	globalSuccess = false;
	                }
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());
	            	globalSuccess = false;
	            }
	            globalSuccess=false;
				return null;
	            
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}

		
	}
