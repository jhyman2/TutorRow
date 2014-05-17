package com.example.tutorrow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.tutorrow.Dashboard.LoginUser;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DashCourseStudentListFragment extends ListFragment {

	ArrayAdapter<String> aa;
	Fragment f;
	ArrayList<String> al;
	ListView lv;
	String[] values;
	boolean globalSuccess = false;
	JSONParser jsonParser = new JSONParser();
	private static String signInURL = "http://www.tutorrow.com/tutorrow/getUser.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		values = Dashboard.u.getClassesStudentFormat();
		if (values.length == 0) {
			values = new String[] { "No courses available" };
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		return inflater.inflate(R.layout.activity_dash_course_student_list,
				container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO: implement remove class or find new tutors
	}

	@Override
	public void onResume() {
		AsyncTask<String, String, String> s = new LoginUser().execute();
		super.onResume();
	}

	class LoginUser extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String name1 = Dashboard.u.getEmail();

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("email", name1));
			Log.d("Before hits", params1.toString());

			JSONObject json = jsonParser.makeHttpRequest(signInURL, "POST",
					params1);
			Log.d("Create Response", json.toString());

			try {
				Log.d("json shit", json.toString());
				String success = json.toString();
				if (success.contains("1")) {
					// successful
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
					User u = new User(id, name, email, phone, username);

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
					if (jStudentArray.length() != 0) {
						values = u.getClassesStudentFormat();
					} else {
						values = new String[] { "No courses in list" };
					}
					Log.d("Student classes format", u.getClassesStudent()
							.toString());
					return "success";
				}
				// Unsuccessful login
				else {
					Log.d("Try", "Fail");
					return "failure";
				}
			} catch (Exception e) {
				Log.d("Exception", e.toString());
				return "failure";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			updateValues();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	public void updateValues() {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}
