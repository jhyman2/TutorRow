package com.example.tutorrow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



public class DashAlertFragment extends ListFragment {
	
	boolean globalSuccess;
	Fragment f;
	JSONParser jsonParser = new JSONParser();
	private static String getAlertsURL = "http://www.tutorapp.herobo.com/getAlerts.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		String[] values = new String[] { "No recent alerts"};
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		return inflater.inflate(R.layout.activity_dash_alerts, container, false);
	}

}
