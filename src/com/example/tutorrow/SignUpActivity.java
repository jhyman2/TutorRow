package com.example.tutorrow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class SignUpActivity extends Activity{

	EditText username, pass, confirmPass, email, phone, fullName;
	Button signUp;
	JSONParser jsonParser = new JSONParser();
//	private static String signUpURL = "http://www.tutorapp.herobo.com/new_user.php";
	private static String signUpURL = "http://www.tutorrow.com/tutorrow/new_user.php";
	boolean globalSuccess = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		username = (EditText)findViewById(R.id.etSignUpUsername);
		pass = (EditText)findViewById(R.id.etSignUpPassword);
		confirmPass = (EditText)findViewById(R.id.etSignUpConfirmPassword);
		email = (EditText)findViewById(R.id.etSignUpEmail);
		phone = (EditText)findViewById(R.id.etSignUpPhone);
		fullName = (EditText)findViewById(R.id.etSignUpFullName);
		signUp = (Button)findViewById(R.id.btnSignUpActivity);
		
		signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Dynamically check password match (check box as you type)
				
				if (pass.getText().toString().equals(confirmPass.getText().toString())){
					AsyncTask<String, String, String> s = new RegisterUser().execute();
					
					if (globalSuccess){
						Toast.makeText(getApplicationContext(), "Account created sucesfully", Toast.LENGTH_LONG).show();
						Log.d("global success", "ssss");
						goToLogIn();
					}else{
						Log.d("global fail", "ssss");
						Toast.makeText(getApplicationContext(), "Error: Account could not be created", Toast.LENGTH_LONG).show();
						finish();
					}
					
				}else{
					Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	public void goToLogIn(){
		Intent dash = new Intent(this, Dashboard.class);
		startActivity(dash);
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}



	class RegisterUser extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
			 String name1 = username.getText().toString();
	         String password1 = pass.getText().toString();
	         String confirmPassWord1 = confirmPass.getText().toString();
	         String email1 = email.getText().toString();
	         String phone1 = phone.getText().toString();
	         String fullName1 = fullName.getText().toString();
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	            params1.add(new BasicNameValuePair("username", name1));
	            params1.add(new BasicNameValuePair("password", password1));
	            params1.add(new BasicNameValuePair("name", fullName1));
	            params1.add(new BasicNameValuePair("email", email1));
	            params1.add(new BasicNameValuePair("phone", phone1));
	            Log.d("Before tits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(signUpURL,
	                    "POST", params1);
	            
	            Log.d("Create Response", json.toString());
	            globalSuccess = true;
	            try {
	            	Log.d("json shit", json.toString());
	                //int success = json.getInt("success");
	            	String success = json.toString();
	                if (success.contains("success")) {
	                    // successful
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