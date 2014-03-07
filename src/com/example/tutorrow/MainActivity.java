package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button btnLogin, btnSignUp;
	Helper h = new Helper();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		btnLogin.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
		h.getData();
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.btnLogin:
			Intent i2 = new Intent(this, Dashboard.class);
			startActivityForResult(i2, 0);
			break;
			
		case R.id.btnSignUp:
			Toast.makeText(getApplicationContext(), Helper.data, Toast.LENGTH_LONG).show();
			break;
			}

			
		}
		
		
	}
