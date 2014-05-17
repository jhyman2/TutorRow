package com.example.tutorrow;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GcmIntentService extends IntentService {

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();

        if (extras == null) {
            return;
        }
        
        String alert = extras.getString("notification");
        createNotification(extras);
	}
	
	public void createNotification(Bundle extras){
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		.setContentTitle("This is the title")
		.setContentText("This is the text");
	    Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("notification", true);
        
	}

}
