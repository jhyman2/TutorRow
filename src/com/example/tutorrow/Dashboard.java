package com.example.tutorrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.tutorrow.MainActivity.LoginUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Dashboard extends Activity implements OnClickListener {

	Button tutorMore, learnMore;
	TextView userNameText;
	public static User u;

	boolean globalSuccess = false;
	JSONParser jsonParser = new JSONParser();
	private static String signInURL = "http://www.tutorrow.com/tutorrow/getUser.php";

	FragmentManager fm;
	FragmentTransaction ft;
	DashAlertFragment alertFragment;
	DashCourseStudentListFragment studentCourses;
	DashCourseTutorListFragment tutorCourses;

	public void initializeData() {
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		alertFragment = new DashAlertFragment();
		studentCourses = new DashCourseStudentListFragment();
		tutorCourses = new DashCourseTutorListFragment();

		ft.detach(alertFragment);
		ft.detach(studentCourses);
		ft.detach(tutorCourses);

		ft.attach(alertFragment);
		ft.attach(studentCourses);
		ft.attach(tutorCourses);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dashboard);

		Log.d("pre-read", "before reading");
		readFromFile();
		Log.d("post-read", "post read");

		fm = getFragmentManager();
		ft = fm.beginTransaction();
		alertFragment = new DashAlertFragment();
		studentCourses = new DashCourseStudentListFragment();
		tutorCourses = new DashCourseTutorListFragment();
		ft.add(R.id.fragDashCoursesTutor, tutorCourses);
		ft.add(R.id.fragDashCoursesStudent, studentCourses);
		ft.add(R.id.fragDashAlerts, alertFragment);
		ft.commit();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String storedPreference = prefs.getString("storedInt", "Not there");
		userNameText = (TextView) findViewById(R.id.tvDashboardUserTextName);
		Log.d("beforetext", "before text");
		userNameText.setText("Welcome " + u.getName() + "!");
		Log.d("aftertext", "after text");
		tutorMore = (Button) findViewById(R.id.btnTutorMoreClasses);
		learnMore = (Button) findViewById(R.id.btnGetMoreTutoring);
		tutorMore.setOnClickListener(this);
		learnMore.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		AsyncTask<String, String, String> s = new LoginUser().execute();
		Log.d("ON RESUME", "RESUMING");
		super.onResume();
	}

	public boolean logout() {
		File file = new File(this.getFilesDir().getPath().toString()
				+ "/datafile.txt");
		return file.delete();
	}

	public User readFromFile() {
		FileInputStream courseFile = null;
		try {
			String filePath = this.getFilesDir().getPath().toString()
					+ "/datafile.txt";
			Log.d("Dash filepath", filePath);
			courseFile = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectInputStream courseObj = null;
		try {
			courseObj = new ObjectInputStream(courseFile);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			User us = (User) courseObj.readObject();
			u = us;
			return us;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnGetMoreTutoring:
			Intent i = new Intent(getApplicationContext(), MoreClasses.class);
			i.putExtra("purpose", "Learner");
			startActivity(i);
			break;
		case R.id.btnTutorMoreClasses:
			Intent i2 = new Intent(getApplicationContext(), MoreClasses.class);
			i2.putExtra("purpose", "Tutor");
			startActivity(i2);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu item) {
		// Handle item selection
		item.add("Log out");
		return super.onCreateOptionsMenu(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return logout();
	}

	class LoginUser extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String name1 = u.getEmail();
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("email", name1));
			Log.d("Before hits", params1.toString());

			JSONObject json = jsonParser.makeHttpRequest(signInURL, "POST",
					params1);
			Log.d("Create Response", json.toString());

			try {
				String success = json.toString();
				if (success.contains("1")) {
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
					JSONArray jStudentArray = json
							.getJSONArray("studentClasses");
					JSONArray jTutorArray = json.getJSONArray("tutorClasses");
					u = new User(id, name, email, phone, username);

					if (jStudentArray != null) {
						for (int i = 0; i < jStudentArray.length(); i++) {
							Log.d("Classes", jStudentArray.getJSONObject(i)
									.getString("name"));
							Log.d("number", jStudentArray.getJSONObject(i)
									.getString("number"));
							Log.d("dep", jStudentArray.getJSONObject(i)
									.getString("department"));
							u.addClassStudent(
									jStudentArray.getJSONObject(i).getString(
											"name"),
									jStudentArray.getJSONObject(i).getString(
											"number"),
									jStudentArray.getJSONObject(i).getString(
											"department"));
						}
					}
					if (jTutorArray != null) {
						for (int i = 0; i < jTutorArray.length(); i++) {
							Log.d("Classes", jTutorArray.getJSONObject(i)
									.getString("name"));
							Log.d("number", jTutorArray.getJSONObject(i)
									.getString("number"));
							Log.d("dep", jTutorArray.getJSONObject(i)
									.getString("department"));
							u.addClassTutor(
									jTutorArray.getJSONObject(i).getString(
											"name"),
									jTutorArray.getJSONObject(i).getString(
											"number"),
									jTutorArray.getJSONObject(i).getString(
											"department"));
						}
					}
					return "success";
				}
				// Unsuccessful login
				else {
					Toast.makeText(getApplicationContext(),
							"Error loading user", Toast.LENGTH_LONG).show();
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
			initializeData();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}