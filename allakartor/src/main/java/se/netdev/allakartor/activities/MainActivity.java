package se.netdev.allakartor.activities;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	private static final int REQUEST_CODE = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        
        if (ConnectionResult.SUCCESS != errorCode) {
			GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_CODE);
		}
        
        Intent intent = new Intent(this, SitesActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    	finish();
	}
}
