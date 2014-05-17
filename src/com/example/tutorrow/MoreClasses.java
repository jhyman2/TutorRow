package com.example.tutorrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tutorrow.MainActivity.LoginUser;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MoreClasses extends Activity {

	TextView tutorOrLearn;
	User u;
	String currentNumber;
	String currentMatch;
	String currentDepartment;
	Button confirmMajor, confirmNumber, confirmTutor;
	TextView majorSelected, numberSelected, tutorSelected;
	JSONclassParsing jsonParser = new JSONclassParsing();
	private static String addClassURL = "http://www.tutorrow.com/tutorrow/addclass.php";
	ArrayList<String> departments = new ArrayList<String>();
	ArrayList<String> courseNums = new ArrayList<String>();
	ArrayList<User> matches = new ArrayList<User>();
	Bundle extras;
	String purpose;
	final Context context = this;

	// ExpandableListView Variables
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_classes);
		readFromFile();
		tutorOrLearn = (TextView) findViewById(R.id.tvTutorOrLearn);
		extras = getIntent().getExtras();
		if (extras != null) {
			purpose = extras.getString("purpose");
			if (purpose.equals("Learner")) {
				tutorOrLearn.setText("Find yourself a tutor");
			} else {
				tutorOrLearn.setText("Find more classes to tutor");
			}
		}

		Log.d("Beginning", "of more classes");
		AsyncTask<String, String, String> s = new AddClass().execute();
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				if (groupPosition == 0) {
					currentDepartment = listDataChild.get(
							listDataHeader.get(groupPosition)).get(
									childPosition);
					expListView.collapseGroup(0);
					AsyncTask<String, String, String> s = new AddClassNumber()
					.execute();
				} else if (groupPosition == 1) {
					currentNumber = listDataChild.get(
							listDataHeader.get(groupPosition)).get(
									childPosition);

					if (purpose.equals("Learner")) {
						u.addClassStudent(currentDepartment, currentNumber,
								"STUDENT");
					} else {
						u.addClassTutor(currentDepartment, currentNumber,
								"TUTOR");
					}

					expListView.collapseGroup(1);
					AsyncTask<String, String, String> s = new AddClassMatch()
					.execute(purpose);
				} else {
					expListView.collapseGroup(0);
					expListView.collapseGroup(1);
					// DIALOGUE BOX
					final User tUser = matches.get(childPosition);
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.custom_dialog);
					dialog.setTitle("How to contact " + tUser.getName());
					// set the custom dialog components - text, image and button
					TextView sms = (TextView) dialog.findViewById(R.id.CDsms);
					sms.setText(tUser.getPhone());
					ImageView image = (ImageView) dialog
							.findViewById(R.id.CDimageSms);
					image.setImageResource(R.drawable.sms);
					image.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent smsIntent = new Intent(Intent.ACTION_VIEW);
							smsIntent.setType("vnd.android-dir/mms-sms");
							smsIntent.putExtra("address", tUser.getPhone());
							smsIntent.putExtra(
									"sms_body",
									"Hello "
											+ tUser.getName()
											+ "! I need tutoring in "
											+ currentDepartment
											+ " "
											+ currentNumber
											+ " and I found you on TutorRow.  Let's get together soon.");
							Intent mailer = Intent.createChooser(smsIntent,
									null);
							startActivity(mailer);
						}
					});

					TextView sms2 = (TextView) dialog
							.findViewById(R.id.CDPhone);
					sms2.setText(tUser.getPhone());
					ImageView image2 = (ImageView) dialog
							.findViewById(R.id.CDimagePhone);
					image2.setImageResource(R.drawable.phone);
					image2.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							String uri = "tel:" + tUser.getPhone().trim();
							Intent intent = new Intent(Intent.ACTION_DIAL);
							intent.setData(Uri.parse(uri));
							Intent mailer = Intent.createChooser(intent, null);
							startActivity(mailer);
						}
					});

					TextView sms3 = (TextView) dialog
							.findViewById(R.id.CDEmail);
					sms3.setText(tUser.getEmail());
					ImageView image3 = (ImageView) dialog
							.findViewById(R.id.CDimageEmail);
					image3.setImageResource(R.drawable.sendemail);
					image3.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("message/rfc822");
							intent.putExtra(Intent.EXTRA_EMAIL,
									new String[] { tUser.getEmail() });
							intent.putExtra(Intent.EXTRA_SUBJECT, "Tutor Row: "
									+ currentDepartment + " " + currentNumber);
							intent.putExtra(
									Intent.EXTRA_TEXT,
									"Hello "
											+ tUser.getName()
											+ "! I need tutoring in "
											+ currentDepartment
											+ " "
											+ currentNumber
											+ " and I found you on TutorRow.  Let's get together soon.");
							Intent mailer = Intent.createChooser(intent, null);
							startActivity(mailer);
						}
					});

					Button dialogButton = (Button) dialog
							.findViewById(R.id.CDdialogButtonOK);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.show();
				}

				return false;
			}
		});

		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Log.d("Group expand", "GGGG");
			}
		});

		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Log.d("Group collapse", "GGS");
			}
		});
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
			Log.d("MOREclasses filepath", filePath);
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
				e.printStackTrace();
				s += "closing error";
			}
		}

		return s + " finished writing.";
	}

	public User readFromFile() {
		FileInputStream courseFile = null;
		try {
			String filePath = this.getFilesDir().getPath().toString()
					+ "/datafile.txt";
			courseFile = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectInputStream courseObj = null;
		try {
			courseObj = new ObjectInputStream(courseFile);
		} catch (StreamCorruptedException e) {
			Log.d("shit", "3");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("shit", "4");
			e.printStackTrace();
		}
		try {
			User us = (User) courseObj.readObject();
			u = us;
			courseObj.close();
			return us;
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("shit", "5");
		}
		return null;
	}

	class AddClass extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			Log.d("B1", "SSS");
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			Log.d("B2", "SSS");
			params1.add(new BasicNameValuePair("id", u.getID()));
			Log.d("Before hits", params1.toString());

			JSONObject json = jsonParser.makeHttpRequest(addClassURL, "POST",
					params1);
			JSONArray jDepartments = null;
			try {
				jDepartments = json.getJSONArray("department");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Log.d("Create Response", jDepartments.toString());
			try {
				String success = jDepartments.toString();
				Log.d("Try", "success");
				Log.d("This is JSON!!!", success + "");

				for (int i = 0; i < jDepartments.length(); i++) {
					Log.d("deparrr",
							jDepartments.getJSONObject(i).getString(
									"department"));
					departments.add(jDepartments.getJSONObject(i).getString(
							"department"));
				}
				return "success";
			} catch (Exception e) {
				Log.d("Exception", e.toString());
				return "failure";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			listDataHeader.add("Departments");
			listDataChild.put(listDataHeader.get(0), departments); // Header,
			// Child
			// data
			expListView.expandGroup(0);

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

	class AddClassNumber extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			Log.d("B1num", "SSS");
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			Log.d("B2num", "SSS");
			params1.add(new BasicNameValuePair("department", currentDepartment));
			Log.d("Before hits", params1.toString());

			JSONObject json = jsonParser.makeHttpRequest(addClassURL, "POST",
					params1);
			Log.d("Getting response", "shittt");
			Log.d("Json infffoo", json.toString());
			JSONArray jDepartments = null;
			try {
				jDepartments = json.getJSONArray("number");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.d("Create Response", jDepartments.toString());
			try {
				String success = jDepartments.toString();
				// successful
				Log.d("Try", "success");
				Log.d("This is JSON!!!", success + "");
				courseNums.clear();
				for (int i = 0; i < jDepartments.length(); i++) {
					Log.d("deparrr",
							jDepartments.getJSONObject(i)
							.getString("coursenum"));
					courseNums.add(jDepartments.getJSONObject(i).getString(
							"coursenum"));
				}
				return "success";
			} catch (Exception e) {
				Log.d("Exception", e.toString());
				return "failure";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			listDataHeader.add("Course numbers");
			listDataChild.put(listDataHeader.get(1), courseNums); // Header,
			// Child
			// data
			expListView.expandGroup(1);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	class AddClassMatch extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String currPurpose = params[0];
			Log.d("current purpose", currPurpose);
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			if (currPurpose.equals("Learner")) {
				params1.add(new BasicNameValuePair("purpose", "student"));
			} else {
				params1.add(new BasicNameValuePair("purpose", "tutor"));
			}
			params1.add(new BasicNameValuePair("department", currentDepartment));
			params1.add(new BasicNameValuePair("number", currentNumber));
			params1.add(new BasicNameValuePair("userID", u.getID()));
			JSONObject json = jsonParser.makeHttpRequest(addClassURL, "POST",
					params1);
			Log.d("Json info", json.toString());
			JSONArray jDepartments = null;
			try {
				jDepartments = json.getJSONArray("users");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Log.d("Create Response", jDepartments.toString());
			try {
				String success = jDepartments.toString();
				Log.d("Try", "success");
				Log.d("This is JSON!!!", success + "");
				matches.clear();

				for (int i = 0; i < jDepartments.length(); i++) {
					Log.d("deparrr",
							jDepartments.getJSONObject(i).getString("name"));
					matches.add(new User(jDepartments.getJSONObject(i)
							.getString("id"), jDepartments.getJSONObject(i)
							.getString("name"), jDepartments.getJSONObject(i)
							.getString("email"), jDepartments.getJSONObject(i)
							.getString("phone"), jDepartments.getJSONObject(i)
							.getString("username")));
				}

				return "success";
			} catch (Exception e) {
				Log.d("Exception", e.toString());
				return "failure";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(),
					currentDepartment + " " + currentNumber + " added!",
					Toast.LENGTH_LONG).show();
			if (matches.size() == 0 && purpose.equals("Learner")) {
				showNoTutors();
			}

			ArrayList<String> formatMatches = new ArrayList<String>();
			Random random = new Random();

			for (User x : matches) {
				int randomNum = random.nextInt((5 - 1) + 1) + 1;
				String r = randomNum + "";
				formatMatches.add(x.getUsername() + " \tRating: " + r);
			}
			listDataHeader.add("Available Tutors");
			listDataChild.put(listDataHeader.get(2), formatMatches); // Header,
			// Child
			// data
			if (purpose.equals("Tutor")) {
				endProgram();
			} else {

				expListView.expandGroup(2);
				super.onPostExecute(result);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

	private void showNoTutors() {
		Builder builder = new AlertDialog.Builder(this);
		String[] selectMajorOld = new String[departments.size()];
		selectMajorOld = departments.toArray(selectMajorOld);
		builder.setMessage("No Tutors/Students Available :(");
		builder.setNegativeButton("go back",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// exit the program
				endProgram();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void endProgram() {
		super.finish();
	}

	class LoginUser extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String name1 = u.getEmail();
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("email", name1));
			Log.d("Before hits", params1.toString());

			JSONObject json = jsonParser.makeHttpRequest(
					"http://www.tutorrow.com/tutorrow/getUser.php", "POST",
					params1);
			Log.d("Create Response", json.toString());

			try {
				String success = json.toString();
				if (success.contains("1")) {
					// successful
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
			writeToFile(u);
			endProgram();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	@Override
	protected void onStop() {
		AsyncTask<String, String, String> s = new LoginUser().execute();
		super.onStop();
	}
}