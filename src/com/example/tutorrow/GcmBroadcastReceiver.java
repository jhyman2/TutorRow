package com.example.tutorrow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class GcmBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
	       ComponentName comp = new ComponentName(arg0.getPackageName(),
	                GcmIntentService.class.getName());
	        arg0.startService(arg1.setComponent(comp));
	        setResultCode(Activity.RESULT_OK);		
	}

}
