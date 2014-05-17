package com.example.tutorrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
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


public class SignUpActivity extends Activity {

	EditText username, pass, confirmPass, email, phone, fullName;
	Button signUp;
	JSONParser jsonParser = new JSONParser();
	private static String signUpURL = "http://www.tutorrow.com/tutorrow/new_user.php";
	boolean globalSuccess = true;
	User u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		username = (EditText) findViewById(R.id.etSignUpUsername);
		pass = (EditText) findViewById(R.id.etSignUpPassword);
		confirmPass = (EditText) findViewById(R.id.etSignUpConfirmPassword);
		email = (EditText) findViewById(R.id.etSignUpEmail);
		phone = (EditText) findViewById(R.id.etSignUpPhone);
		fullName = (EditText) findViewById(R.id.etSignUpFullName);
		signUp = (Button) findViewById(R.id.btnSignUpActivity);

		signUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Dynamically check password match (check box as you type)

				if (pass.getText().toString()
						.equals(confirmPass.getText().toString())
						&& !username.getText().toString().matches("")
						&& !pass.getText().toString().matches("")
						&& !confirmPass.getText().toString().matches("")
						&& !email.getText().toString().matches("")
						&& !phone.getText().toString().matches("")
						&& !fullName.getText().toString().matches("")) {
					AsyncTask<String, String, String> s = new RegisterUser()
					.execute();

					if (globalSuccess) {
						Toast.makeText(getApplicationContext(),
								"Account created sucesfully", Toast.LENGTH_LONG)
								.show();
						Log.d("global success", "ssss");
					} else {
						Log.d("global fail", "ssss");
						Toast.makeText(getApplicationContext(),
								"Error: Account could not be created",
								Toast.LENGTH_LONG).show();
						finish();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Passwords do not match or fields are empty",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void goToLogIn() {
		Intent dash = new Intent(this, Dashboard.class);
		startActivity(dash);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	class RegisterUser extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String name1 = username.getText().toString();
			String password1 = pass.getText().toString();
			String confirmPassWord1 = confirmPass.getText().toString();
			String email1 = email.getText().toString();
			String phone1 = phone.getText().toString();
			String fullName1 = fullName.getText().toString();

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("username", name1));
			params1.add(new BasicNameValuePair("password", Helper
					.MD5_Hash(password1)));
			params1.add(new BasicNameValuePair("name", fullName1));
			params1.add(new BasicNameValuePair("email", email1));
			params1.add(new BasicNameValuePair("phone", phone1));

			JSONObject json = jsonParser.makeHttpRequest(signUpURL, "POST",
					params1);

			Log.d("Create Response", json.toString());
			globalSuccess = true;
			try {
				String success = json.toString();
				String userId = json.getString("userId");
				u = new User(userId, fullName1, email1, phone1, name1);

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
			writeToFile(u);
			goToLogIn();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

	/*
	 * Reads the user data as a serialized object in datafile.txt and returns it
	 */
	public User readFromFile() {
		FileInputStream courseFile = null;
		File file = null;
		try {
			String filePath = this.getFilesDir().getPath().toString()
					+ "/datafile.txt";
			file = new File(filePath);
			if (!file.exists()) {
			}
			courseFile = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			Log.d("Errr", "1");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Errr", "2");
			e.printStackTrace();
		}
		ObjectInputStream courseObj = null;
		try {
			courseObj = new ObjectInputStream(courseFile);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			Log.d("Errr", "3");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("Errr", "4");
			e.printStackTrace();
		}
		try {
			return (User) courseObj.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("read", "5");
		}
		return null;
	}

	/*
	 * Serializes the user data and writes its object to a file
	 */
	public String writeToFile(User u) {
		String s = "";

		FileOutputStream courseFile = null;
		ObjectOutputStream courseObj = null;
		File file = null;
		try {
			String filePath = this.getFilesDir().getPath().toString()
					+ "/datafile.txt";
			file = new File(filePath);
			if (!file.exists()) {
				if (!file.createNewFile()) {
					s += "File could not be created";
				}
			}
			courseFile = new FileOutputStream(filePath);
			courseObj = new ObjectOutputStream(courseFile);

			if (courseObj != null && courseFile != null) {
				courseObj.writeObject(u);
			} else {
				s += "There was an error with the file";
			}

		} catch (Exception e) {
			s += e.toString();
		} finally {
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