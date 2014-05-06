package com.example.tutorrow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tutorrow.MainActivity.LoginUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MoreClasses extends Activity{

	TextView tutorOrLearn;
	User u;
	String currentNumber;
	String currentMatch;
	String currentDepartment;
	Button confirmMajor, confirmNumber, confirmTutor;
	JSONclassParsing jsonParser = new JSONclassParsing();
	private static String addClassURL = "http://www.tutorrow.com/tutorrow/addclass.php";
	ArrayList<String> departments = new ArrayList<String>();
	ArrayList<String> courseNums = new ArrayList<String>();
	ArrayList<String> matches = new ArrayList<String>();
	Bundle extras;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_classes);
		readFromFile();
		tutorOrLearn = (TextView)findViewById(R.id.tvTutorOrLearn);
		extras = getIntent().getExtras();
		if (extras != null){
			tutorOrLearn.setText(extras.getString("purpose"));
		}
		confirmMajor = (Button)findViewById(R.id.btnConfirmMajor);
		confirmNumber = (Button)findViewById(R.id.btnConfirmNumber);
		confirmTutor = (Button)findViewById(R.id.btnConfirmTutor);
		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DashAlertFragment alertFragment = new DashAlertFragment();
		DashCourseStudentListFragment studentCourses = new DashCourseStudentListFragment();
		DashCourseTutorListFragment tutorCourses = new DashCourseTutorListFragment();
		ft.commit();
		Log.d("Beginning","of more classes");

		AsyncTask<String, String, String> s = new AddClass().execute();
		confirmMajor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	          SimpleAlertMajor();
			}
			
		});
		confirmNumber.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub\
				Log.d("Beginning of num", "Before");
				if (currentDepartment != null){
					Log.d("Inside your num", "Inside");
					AsyncTask<String, String, String> s = new AddClassNumber().execute();
				}else{
					Toast.makeText(getApplicationContext(), "You must select a department first.", Toast.LENGTH_LONG).show();
				}
			}	
		});
		
		confirmTutor.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (currentNumber != null){
					AsyncTask<String, String, String> s = new AddClassMatch().execute(extras.getString("purpose"));
				}				
			}			
		});
	}
	
	public User readFromFile(){
		FileInputStream courseFile = null;
	//	File file = null;
		try {
			String filePath = this.getFilesDir().getPath().toString() + "/datafile.txt";
	//	    file = new File(filePath);
			courseFile = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
        	Log.d("shit", "1");
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
			User us = (User)courseObj.readObject();
			u = us;
			courseObj.close();
			return us;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			Log.d("shit", "5");
		}
		return null;
	}
	
	 private void SimpleAlertNumber() {  
	     Builder builder = new AlertDialog.Builder(this);  
	     builder.setTitle("Select A Course Number");  
	     String[] selectNumberOld = new String[courseNums.size()];
	     selectNumberOld = courseNums.toArray(selectNumberOld);
	     final String[] selectNumber = selectNumberOld;
	     builder.setSingleChoiceItems(selectNumber, -1,  
	         new DialogInterface.OnClickListener() {  
	           @Override  
	           public void onClick(DialogInterface dialog, int which) {  
	             Toast.makeText(MoreClasses.this,  
	                 selectNumber[which] + " Selected",  
	                 Toast.LENGTH_LONG).show();  
	             currentNumber = selectNumber[which];
	             dialog.dismiss();  
	           }  
	         });  
	     builder.setNegativeButton("cancel",  
	         new DialogInterface.OnClickListener() {  
	           @Override  
	           public void onClick(DialogInterface dialog, int which) {  
	             dialog.dismiss();  
	           }  
	         });  
	     AlertDialog alert = builder.create();  
	     alert.show();  
	   }  
	
	  private void SimpleAlertMajor() {  
		     Builder builder = new AlertDialog.Builder(this);  
		     builder.setTitle("Select A Major");  
		     String[] selectMajorOld = new String[departments.size()];
		     selectMajorOld = departments.toArray(selectMajorOld);
		     final String[] selectMajor = selectMajorOld;
		     builder.setSingleChoiceItems(selectMajor, -1,  
		         new DialogInterface.OnClickListener() {  
		           @Override  
		           public void onClick(DialogInterface dialog, int which) {  
		             Toast.makeText(MoreClasses.this,  
		                 selectMajor[which] + " Selected",  
		                 Toast.LENGTH_LONG).show();  
		             currentDepartment = selectMajor[which];
		             dialog.dismiss();  
		           }  
		         });  
		     builder.setNegativeButton("cancel",  
		         new DialogInterface.OnClickListener() {  
		           @Override  
		           public void onClick(DialogInterface dialog, int which) {  
		             dialog.dismiss();  
		           }  
		         });  
		     AlertDialog alert = builder.create();  
		     alert.show();  
		   }  
	  private void SimpleAlertTutor() {  
		     Builder builder = new AlertDialog.Builder(this);  
		     
		     if (extras.getString("purpose").equals("Learner")){
		     builder.setTitle("Select A Tutor");  
		     }else{
		    	 builder.setTitle("Select A Student");
		     }
		     String[] selectMatchOld = new String[matches.size()];
		     selectMatchOld = matches.toArray(selectMatchOld);
		     final String[] selectMatch = selectMatchOld;
		     builder.setSingleChoiceItems(selectMatch, -1,  
		         new DialogInterface.OnClickListener() {  
		           @Override  
		           public void onClick(DialogInterface dialog, int which) {  
		             Toast.makeText(MoreClasses.this,  
		                 selectMatch[which] + " Selected",  
		                 Toast.LENGTH_LONG).show();  
		             currentMatch = selectMatch[which];
		             dialog.dismiss();  
		           }  
		         });  
		     builder.setNegativeButton("cancel",  
		         new DialogInterface.OnClickListener() {  
		           @Override  
		           public void onClick(DialogInterface dialog, int which) {  
		             dialog.dismiss();  
		           }  
		         });  
		     AlertDialog alert = builder.create();  
		     alert.show();  
		   }  
	  
	class AddClass extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         // TODO: MD5 or SHA256 the Password
            	Log.d("B1", "SSS");
	         	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	         	Log.d("B2", "SSS");
	            params1.add(new BasicNameValuePair("id", u.getID()));
	            Log.d("Before hits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(addClassURL,
	                    "POST", params1);
	            JSONArray jDepartments = null;
	            try {
					jDepartments = json.getJSONArray("department");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            Log.d("Create Response", jDepartments.toString());
	            try {
	           // 	Log.d("json shit", json.toString());
	            	String success = jDepartments.toString();
	                    // successful
	                    Log.d("Try", "success");	    	     	                    
	                    Log.d("This is JSON!!!", success + "");
	                    
	                    for (int i = 0; i < jDepartments.length(); i++){
	                    	Log.d("deparrr", jDepartments.getJSONObject(i).getString("department"));
	                    	departments.add(jDepartments.getJSONObject(i).getString("department"));
	                    }
	                    return "success";
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());return "failure";
	            }	
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
	
	class AddClassNumber extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         // TODO: MD5 or SHA256 the Password
            	Log.d("B1num", "SSS");
	         	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	         	Log.d("B2num", "SSS");
	            params1.add(new BasicNameValuePair("department", currentDepartment));
	            Log.d("Before hits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(addClassURL,
	                    "POST", params1);
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
	           // 	Log.d("json shit", json.toString());
	            	String success = jDepartments.toString();
	                    // successful
	                    Log.d("Try", "success");	    	     	                    
	                    Log.d("This is JSON!!!", success + "");
	                    courseNums.clear();
	                    for (int i = 0; i < jDepartments.length(); i++){
	                    	Log.d("deparrr", jDepartments.getJSONObject(i).getString("coursenum"));
	                    	courseNums.add(jDepartments.getJSONObject(i).getString("coursenum"));
	                    }
	                    return "success";
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());return "failure";
	            }	
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			SimpleAlertNumber();
		       super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}	
	}
	
	class AddClassMatch extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         // TODO: MD5 or SHA256 the Password
				String currPurpose = params[0];
				Log.d("current purpose", currPurpose);
            	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            	if (currPurpose.equals("Learner")){
            		params1.add(new BasicNameValuePair("purpose", "student"));
            	}else{
            		params1.add(new BasicNameValuePair("purpose", "tutor"));
            	}
	         	params1.add(new BasicNameValuePair("department", currentDepartment));
	         	params1.add(new BasicNameValuePair("number", currentNumber));
	            JSONObject json = jsonParser.makeHttpRequest(addClassURL,
	                    "POST", params1);
	            Log.d("Json infffoo", json.toString());
	            JSONArray jDepartments = null;
	            try {
					jDepartments = json.getJSONArray("matches");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            Log.d("Create Response", jDepartments.toString());
	            try {
	           // 	Log.d("json shit", json.toString());
	            	String success = jDepartments.toString();
	                    // successful
	                    Log.d("Try", "success");	    	     	                    
	                    Log.d("This is JSON!!!", success + "");
	                    matches.clear();
	                    for (int i = 0; i < jDepartments.length(); i++){
	                    	Log.d("deparrr", jDepartments.getJSONObject(i).getString("match"));
	                    	matches.add(jDepartments.getJSONObject(i).getString("match"));
	                    }
	                    return "success";
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());return "failure";
	            }	
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			SimpleAlertTutor();
		       super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}	
	}
}