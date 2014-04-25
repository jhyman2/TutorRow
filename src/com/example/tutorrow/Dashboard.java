package com.example.tutorrow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends Activity implements OnClickListener {
	
	Button tutorMore, learnMore;
	TextView userNameText;
	public static User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dashboard);
		Log.d("pre-read", "before reading");
		readFromFile();
		Log.d("post-read", "post read");
		Bundle extras = getIntent().getExtras();	
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DashAlertFragment alertFragment = new DashAlertFragment();
		DashCourseStudentListFragment studentCourses = new DashCourseStudentListFragment();
		Bundle bundle = new Bundle();
		DashCourseTutorListFragment tutorCourses = new DashCourseTutorListFragment();
		ft.add(R.id.fragDashCoursesTutor, tutorCourses);
		ft.add(R.id.fragDashCoursesStudent, studentCourses);
		ft.add(R.id.fragDashAlerts, alertFragment);
		ft.commit();
		
		/*String username = extras.getString("user");
		
		userNameText = (TextView)findViewById(R.id.tvDashboardUserTextName);
		userNameText.setText("Welcome " + username + "!");*/
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    //  SharedPreferences.Editor editor = prefs.edit();
		String storedPreference = prefs.getString("storedInt", "Not there");
		userNameText = (TextView)findViewById(R.id.tvDashboardUserTextName);
		Log.d("beforetext", "before text");
		userNameText.setText("Welcome " + u.getName() + "!");
		Log.d("aftertext", "after text");
		tutorMore = (Button)findViewById(R.id.btnTutorMoreClasses);
		learnMore = (Button)findViewById(R.id.btnGetMoreTutoring);
		tutorMore.setOnClickListener(this);
		learnMore.setOnClickListener(this);	
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
			User us = (User)courseObj.readObject();
			u = us;
			return us;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			Log.d("shit", "5");
		}
		return null;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
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
}