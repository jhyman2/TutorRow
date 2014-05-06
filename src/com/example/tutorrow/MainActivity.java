package com.example.tutorrow;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.NameNotFoundException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tutorrow.SignUpActivity.RegisterUser;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This is the starting point of the app. 
 */
public class MainActivity extends Activity implements OnClickListener {

	Button btnLogin, btnSignUp;
	EditText userName, passWord;
	boolean globalSuccess = false;
	JSONParser jsonParser = new JSONParser();
	private static String signInURL = "http://www.tutorrow.com/tutorrow/login.php";
	//public static User appUser;
	SharedPreferences prefs;
	private static String userDataFile;
	User u;
	
	// GCM Data
    static final String TAG = "GCMDemo";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    String regid;
    Context context;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
	String SENDER_ID = "603533971856";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		Log.d("GCM", "1");
		// Check device for Play Services APK.
		if (checkPlayServices()) {
			Log.d("GCM", "2");
			
			// If this check succeeds, proceed with normal processing.
			// Otherwise, prompt user to get valid Play Services APK.
        	Log.d("GCM", "8");
			gcm = GoogleCloudMessaging.getInstance(this);
            Log.d("GCM", "7");
			regid = getRegistrationId(context);
            //String msg = "Device registered, registration ID=" + regid;
            if (regid.isEmpty()){
            	Log.d("GCM", "5");
            	registerInBackground();
            	Log.d("GCM", "6");
            }else{
            	Log.d("Already registered", "With GCM");
//            	finish();
            }
            Log.d("GCM", "3");
    		
		
		//TODO: NEED TO FIX THIS SO THAT IT CHECKS FOR USER ALREADY LOGGED IN (COOKIE'S N CREME)
	/*	if (readFromFile() != null){
			Log.d("Before intent", "Prior to calling");
			startIntent();
		}
	*/	Log.d("After if statement", "After");
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		userName = (EditText)findViewById(R.id.etEmailLogin);
		passWord = (EditText)findViewById(R.id.etPassword);
		btnLogin.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
		}else{
			Log.d("GCM", "4");
		}
	}
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("No GCM!", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    checkPlayServices();
	}
	
	/**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = 0;
		try {
			appVersion = getAppVersion(context);
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = 0;
		try {
			currentVersion = getAppVersion(context);
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend(regid);

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            	
            }
        }.execute(null, null, null);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     * @throws android.content.pm.PackageManager.NameNotFoundException 
     */
    private static int getAppVersion(Context context) throws android.content.pm.PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager()
		        .getPackageInfo(context.getPackageName(), 0);
		return packageInfo.versionCode;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(final String id) {
      // Your implementation here.
    	User registerUser = readFromFile();
    	final String userEmail = registerUser.getEmail();
    	Log.d("USer email", userEmail);
    	class JesusTask extends AsyncTask<String, String, String>{

			@Override
			protected String doInBackground(String... params) {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	            params1.add(new BasicNameValuePair("email", userEmail));	
	            params1.add(new BasicNameValuePair("GCMid", id));
	            JSONObject json = jsonParser.makeHttpRequest("http://www.tutorrow.com/tutorrow/registerGCM.php",
	                    "POST", params1);
	            Log.d("Registration GCM", json.toString());
				return null;
			}	
    	}    	
    	JesusTask j = (JesusTask) new JesusTask().execute();
    }

	public void startIntent(){
		Log.d("Beginning of start", "Intent");
		if (u != null){
			Log.d("In U", "Utero");
			Intent i2 = new Intent(this, Dashboard.class);
			Log.d("pre-starting", "before act 2");
			startActivity(i2);
			Log.d("Started", "Activity 2");
			finish();
		} else{
			Toast.makeText(getApplicationContext(), "User not found???", Toast.LENGTH_LONG).show();
		}
	}
	
	/*
	 * Reads the user data as a serialized object in datafile.txt and returns it
	 * 
	 */
	public User readFromFile(){
		FileInputStream courseFile = null;
		File file = null;
		try {
			String filePath = this.getFilesDir().getPath().toString() + "/datafile.txt";
		    file = new File(filePath);
		    if (!file.exists()) {
		    	Log.d("shit", "Jesus");
		    	startIntent();
		    }
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
			User u = (User)courseObj.readObject();
			return u;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			Log.d("shit", "5");
		}
		return null;
	}

	
	
	public String writeToFile(User u){
		String s = "";
		
		FileOutputStream courseFile = null;
		ObjectOutputStream courseObj = null;
		File file = null;
		try{
				String filePath = this.getFilesDir().getPath().toString() + "/datafile.txt";
				file = new File(filePath);
				if (!file.exists()){
					if (!file.createNewFile()) {
						s += "File could not be created";
					}
				}
				courseFile = new FileOutputStream(filePath);
				courseObj = new ObjectOutputStream(courseFile);
				
		        if (courseObj != null && courseFile != null){
					courseObj.writeObject(u);
		        }else{
		        	s += "There was an error with the file";
		        }
				
			
		}catch(Exception e){
			s += e.toString();
		}finally{
			try {
				courseObj.close();
				courseFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				s += "closing error";
			}
		}
		
		return s + " finished writing.";
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.btnLogin:
			if (userName.getText().toString().isEmpty() || passWord.getText().toString().isEmpty())
			{
				Log.d("Blank", "true");
				globalSuccess = false;
			}
			else
			{
				Log.d("Blank", "false");
				AsyncTask<String, String, String> s = new LoginUser().execute();
			}
			
			break;
			
		case R.id.btnSignUp:
			Intent signUpIntent = new Intent(this, SignUpActivity.class);
			startActivity(signUpIntent);
			break;
			}

			
		}
		
	class LoginUser extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {	
			 String name1 = userName.getText().toString();
	         String password1 = passWord.getText().toString();
	         
	         // TODO: PHP SCRIPT WILL CHECK IF USERNAME EXISTS AS WELL AS PASSWORD MATCHING	 
	         // TODO: MD5 or SHA256 the Password
	         	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	            params1.add(new BasicNameValuePair("email", name1));
	            params1.add(new BasicNameValuePair("password", password1));
	            Log.d("Before hits", params1.toString());
	            
	            JSONObject json = jsonParser.makeHttpRequest(signInURL,
	                    "POST", params1);
	            Log.d("Create Response", json.toString());
	            
	            try {
	            	Log.d("json shit", json.toString());
	            	String success = json.toString();
	                if (success.contains("1")) {
	                    // successful
	                	globalSuccess = true;
	                    Log.d("Try", "success");	    	     	                    
	                    
	                    String id = json.getString("id");
	                    Log.d("ID_ANDSTUFF", id);
	                    String phone = json.getString("phone");
	                    Log.d("phone", phone);
	                    String email = json.getString("email");
	                    Log.d("email", email);
	                    String name = json.getString("name");
	                    Log.d("name", name);
	                    String username = json.getString("username");
	                    Log.d("username", username);
	                    JSONArray jStudentArray = json.getJSONArray("studentClasses");
	                    JSONArray jTutorArray = json.getJSONArray("tutorClasses");
	                //    JSONObject jsonClassName = jArray.getJSONObject(0);
	                    //	public User(String id, String fullName, String email, String phone, String username){
	                    u = new User(id, name, email, phone, username);
	                    if (jStudentArray != null){
	                    	for (int i = 0; i < jStudentArray.length(); i++){
	                    		Log.d("Classes", jStudentArray.getJSONObject(i).getString("name"));
	                    		Log.d("number", jStudentArray.getJSONObject(i).getString("number"));
	                    		Log.d("dep", jStudentArray.getJSONObject(i).getString("department"));
	                    		u.addClassStudent(jStudentArray.getJSONObject(i).getString("name"), jStudentArray.getJSONObject(i).getString("number"), jStudentArray.getJSONObject(i).getString("department"));
	                    	}
	                    } 
	                    if (jTutorArray != null){
	                    	for (int i = 0; i < jTutorArray.length(); i++){
	                    		Log.d("Classes", jTutorArray.getJSONObject(i).getString("name"));
	                    		Log.d("number", jTutorArray.getJSONObject(i).getString("number"));
	                    		Log.d("dep", jTutorArray.getJSONObject(i).getString("department"));
	                    		u.addClassTutor(jTutorArray.getJSONObject(i).getString("name"), jTutorArray.getJSONObject(i).getString("number"), jTutorArray.getJSONObject(i).getString("department"));
	                    	}
	                    }
	                    
	                    
//	                    Log.d("COCK", jsonClassName.getString("name"));
	                  
	                    
	                   /* List<String> jShit = new ArrayList<String>();
	                    JSONObject userJSON = json.getJSONObject("user");
	                    String jsonID = userJSON.getString("id");
	                    String jsonEmail = userJSON.getString("email");
	                    String jsonPhone = userJSON.getString("phone");
	                    String jsonName = userJSON.getString("name");
	                    String jsonUsername = userJSON.getString("username");
	                    
	                    Log.d("phpID", jsonID+"");
	                    Log.d("phpEmail",jsonEmail+""); 
	                    Log.d("phpPhone", jsonPhone+"");
	                    Log.d("phpName",jsonName+""); 
	                    Log.d("phpUser", jsonUsername+"");*/
	                    //Toast.makeText(getApplicationContext(), "ID: " + jsonID, Toast.LENGTH_LONG).show();
	                   /* String jsonUsername = json.getString("username");
	                    String jsonEmail = json.getString("email");
	                    String jsonPhone = json.getString("phone");
	                    
	                    Toast.makeText(getApplicationContext(), "ID: " + jsonID + " User: " + jsonUsername, Toast.LENGTH_LONG).show();
	                    */
	                    // GET DATA FROM PHP FILE AND PUT EXTRAS INTO DASHBOARD
	                    return "success";
	                } else {
	                    // failed
	    				Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
	    				globalSuccess = false;
	                	Log.d("Try", "Fail");
	                	return "failure";
	                }
	            } catch (Exception e) {
	            	Log.d("Exception", e.toString());
	            	globalSuccess = false;
	            	return "failure";
	            }	
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (globalSuccess){
				Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                writeToFile(u);
				startIntent();
			}else{
				Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}	
	}		
}