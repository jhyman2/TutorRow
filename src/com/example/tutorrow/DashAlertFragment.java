package com.example.tutorrow;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



public class DashAlertFragment extends ListFragment {
	
	Fragment f;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] values = new String[] { "CMSC 201", "CMSC 202", "CMSC 203",
		        "CMSC 313", "CMSC 311", "CMSC 341", "CMSC 411", "CMSC 421"};
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		return inflater.inflate(R.layout.activity_dash_alerts, container, false);
	}

}
