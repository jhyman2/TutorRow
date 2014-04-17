package com.example.tutorrow;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MoreClasses extends Activity {

	TextView tutorOrLearn;
	Button confirmMajor, confirmNumber, confirmTutor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_classes);
		tutorOrLearn = (TextView)findViewById(R.id.tvTutorOrLearn);
		Bundle extras = getIntent().getExtras();
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
		//ft.add(R.id.fragAddClassMajor, tutorCourses);
	/*	ft.add(R.id.fragAddClassNumber, studentCourses);
		ft.add(R.id.fragAddClassTutors, alertFragment);
	*/	ft.commit();
		
	}
}
