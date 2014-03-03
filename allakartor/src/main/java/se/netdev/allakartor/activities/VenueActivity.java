package se.netdev.allakartor.activities;

import java.util.concurrent.Future;

import se.netdev.allakartor.entities.Venue;
import se.netdev.allakartor.fragments.VenueOptionsListFragment;
import se.netdev.allakartor.operations.GetVenueOperation;
import se.netdev.allakartor.operations.GetVenueOperation.GetVenueCallback;

import com.sogeti.droidnetworking.NetworkEngine;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;

public class VenueActivity extends BaseActivity {
	private static final String TAG = "VenueActivity";
	private static final String MAP_NAME = "mapName";
	private static final String VENUE_ID = "venueId";
	private static final String NAME = "name";
	private static final String LIST = "list";
	
	private String mapName;
	private String venueId;
	private String name;
	private Future<?> future;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
			mapName = savedInstanceState.getString(MAP_NAME);
            venueId = savedInstanceState.getString(VENUE_ID);
            name = savedInstanceState.getString(NAME);
        } else {
            Bundle extras = getIntent().getExtras();
            mapName = extras.getString(MAP_NAME);
            venueId = extras.getString(VENUE_ID);
            name = extras.getString(NAME);
		    
		    VenueOptionsListFragment fragment = new VenueOptionsListFragment();
		    getFragmentManager().beginTransaction().add(android.R.id.content, fragment, LIST).commit();
        }
		
		actionBar.setTitle(name);
	
		GetVenueOperation operation = new GetVenueOperation(mapName, venueId, new GetVenueCallback() {
			@Override
			public void done(Venue venue, Exception exception) {
				if (exception == null) {
					Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
					
					if (fragment != null && fragment.getTag().equals(LIST)) {
			    		((VenueOptionsListFragment)fragment).setVenue(venue);
					}
				} else {
					Log.d(TAG, "GetVenueOperation: " + exception.getMessage());
				}
			}
		});

		future = NetworkEngine.getInstance().enqueueOperation(operation);
	}
	
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString(MAP_NAME, mapName);
        outState.putString(VENUE_ID, venueId);  
        outState.putString(NAME, name);
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (future != null) {
			future.cancel(true);
		}
	}
}
