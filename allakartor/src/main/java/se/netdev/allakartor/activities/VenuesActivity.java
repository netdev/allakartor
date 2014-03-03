package se.netdev.allakartor.activities;

import java.util.ArrayList;
import java.util.concurrent.Future;

import com.sogeti.droidnetworking.NetworkEngine;

import se.netdev.allakartor.R;
import se.netdev.allakartor.TabListener;
import se.netdev.allakartor.VenuesListener;
import se.netdev.allakartor.entities.VenueRef;
import se.netdev.allakartor.fragments.VenuesListFragment;
import se.netdev.allakartor.fragments.VenuesMapFragment;
import se.netdev.allakartor.managers.UserLocationManager;
import se.netdev.allakartor.managers.UserLocationManager.OnLocationChangedListener;
import se.netdev.allakartor.operations.GetVenuesOperation;
import se.netdev.allakartor.operations.GetVenuesOperation.GetVenuesCallback;

import android.app.ActionBar;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class VenuesActivity extends BaseActivity implements OnLocationChangedListener, VenuesListener {
	private static final String TAG = "VenuesActivity";
	private static final String MAP_NAME = "mapName";
	private static final String MAP_TITLE = "mapTitle";
	private static final String MAP_TAB = "map";
	private static final String LIST_TAB = "list";
	private static final String SELECTED_TAB = "selectedTab";
	
	private String mapName;
	private String mapTitle;
	private int selectedTab;
	private Future<?> future;
	private ArrayList<VenueRef> venues;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) {
            mapName = savedInstanceState.getString(MAP_NAME);
            mapTitle = savedInstanceState.getString(MAP_TITLE);
            selectedTab = savedInstanceState.getInt(SELECTED_TAB);
        } else {
            Bundle extras = getIntent().getExtras();
            mapName = extras.getString(MAP_NAME);
            mapTitle = extras.getString(MAP_TITLE);
            selectedTab = 0;
        }
		
		actionBar.setTitle(mapTitle);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Bundle args = new Bundle();
		args.putString(MAP_NAME, mapName);
		
		ActionBar.Tab tab = actionBar.newTab().setText(R.string.list).setTabListener(
				new TabListener<VenuesListFragment>(this, LIST_TAB, VenuesListFragment.class, args));
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText(R.string.map).setTabListener(
				new TabListener<VenuesMapFragment>(this, MAP_TAB, VenuesMapFragment.class, args));
		actionBar.addTab(tab);
		
		actionBar.setSelectedNavigationItem(selectedTab);
		
		requestVenues();
	}
	
	public String getMapName() {
		return mapName;
	}

	private void requestVenues() {
		if (future != null) {
			future.cancel(true);
		}
		
		GetVenuesOperation operation = new GetVenuesOperation(mapName,
				UserLocationManager.getInstance().getCurrentBestLocation(), new GetVenuesCallback() {
			@Override
			public void done(final ArrayList<VenueRef> venues, Exception exception) {
				if (exception == null) {
					VenuesActivity.this.venues = venues;
					
					Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
					
					if (fragment != null && fragment.getTag().equalsIgnoreCase("list")) {
			    		((VenuesListFragment)fragment).setVenues(venues);
			    	} else if (fragment != null && fragment.getTag().equalsIgnoreCase("map")) {
			    		((VenuesMapFragment)fragment).setVenues(venues);
			    	}
				} else {
					Log.d(TAG, "GetVenuesOperation: " + exception.getMessage());
				}
			}
		});

		future = NetworkEngine.getInstance().enqueueOperation(operation);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		UserLocationManager.getInstance().removeListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		UserLocationManager.getInstance().addListener(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (future != null) {
			future.cancel(true);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
        outState.putString(MAP_NAME, mapName);
        outState.putString(MAP_TITLE, mapTitle);
        outState.putInt(SELECTED_TAB, actionBar.getSelectedTab().getPosition());
    }
 
	public void onLocationChanged(final Location location) {
		requestVenues();
	}

	public ArrayList<VenueRef> getVenues() {
		return venues;
	}
}
