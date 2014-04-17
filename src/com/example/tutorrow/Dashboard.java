package com.example.tutorrow;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Dashboard extends Activity implements OnClickListener {
	

	Button tutorMore, learnMore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dashboard);
		
		Bundle extras = getIntent().getExtras();		
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DashAlertFragment alertFragment = new DashAlertFragment();
		DashCourseStudentListFragment studentCourses = new DashCourseStudentListFragment();
		DashCourseTutorListFragment tutorCourses = new DashCourseTutorListFragment();
		ft.add(R.id.fragDashCoursesTutor, tutorCourses);
		ft.add(R.id.fragDashCoursesStudent, studentCourses);
		ft.add(R.id.fragDashAlerts, alertFragment);
		ft.commit();
		
		tutorMore = (Button)findViewById(R.id.btnTutorMoreClasses);
		learnMore = (Button)findViewById(R.id.btnGetMoreTutoring);
		tutorMore.setOnClickListener(this);
		learnMore.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.btnGetMoreTutoring:
			
			Intent i = new Intent(getApplicationContext(), MoreClasses.class);
		//	i.putExtra("classesTutoring", classesTutoring);
		//	i.putExtra("classesLearning", classesLearning);
			i.putExtra("purpose", "Learner");
			startActivity(i);
			
			break;
		case R.id.btnTutorMoreClasses:
			Intent i2 = new Intent(getApplicationContext(), MoreClasses.class);
		//	i.putExtra("classesTutoring", classesTutoring);
		//	i.putExtra("classesLearning", classesLearning);
			i2.putExtra("purpose", "Tutor");
			startActivity(i2);
			break;
		}
		
	}
	
	
}
