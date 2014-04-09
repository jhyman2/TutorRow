package com.example.tutorrow;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Dashboard extends Activity implements OnClickListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dashboard);
		Log.d("before", "shit");
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DashAlertFragment alertFragment = new DashAlertFragment();
		DashCourseStudentListFragment studentCourses = new DashCourseStudentListFragment();
		DashCourseTutorListFragment tutorCourses = new DashCourseTutorListFragment();
		ft.add(R.id.fragDashCoursesTutor, tutorCourses);
		ft.add(R.id.fragDashCoursesStudent, studentCourses);
		ft.add(R.id.fragDashAlerts, alertFragment);
		ft.commit();
		Log.d("after", "shit");
	/*	FragmentManager fm2 = getFragmentManager();
		FragmentTransaction ft2 = fm.beginTransaction();
		DashCourseStudentListFragment clFragment2 = new DashCourseStudentListFragment();
		ft2.add(R.id.fragDashCoursesStudent, clFragment2);
		ft2.commit();
		
		FragmentManager fm3 = getFragmentManager();
		FragmentTransaction ft3 = fm.beginTransaction();
		DashCourseTutorListFragment clFragment3 = new DashCourseTutorListFragment();
		ft3.add(R.id.fragDashCoursesTutor, clFragment3);
		ft3.commit();
*/
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}
