package com.example.tutorrow;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MoreClassesSelectNumberFragment extends ListFragment{

	ArrayAdapter<String> aa;
	Fragment f;	
	ArrayList<String> al;
	ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		String[] values = new String[] { "CMSC 201", "CMSC 202", "CMSC 203",
		        "CMSC 313", "CMSC 311", "CMSC 341", "CMSC 411", "CMSC 421"};
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		return inflater.inflate(R.layout.activity_more_classes_major, container, false);
	}
	
	 @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
		 Toast.makeText(getActivity(), l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
	  }
	
}
