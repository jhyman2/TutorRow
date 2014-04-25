package com.example.tutorrow;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DashCourseTutorListFragment extends ListFragment {

	Fragment f;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		String[] values = Dashboard.u.getClassesTutorFormat();
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		return inflater.inflate(R.layout.activity_dash_course_tutor_list, container, false);
		
	}

}
