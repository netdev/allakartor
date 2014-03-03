package se.netdev.allakartor.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import se.netdev.allakartor.VenuesListener;
import se.netdev.allakartor.activities.VenueActivity;
import se.netdev.allakartor.entities.VenueRef;

public class VenuesMapFragment extends MapFragment implements OnInfoWindowClickListener {
	private static final String MAP_NAME = "mapName";
	private static final String VENUE_ID = "venueId";
	private static final String NAME = "name";
	private static final int MAP_PADDING = 100;
	
	private String mapName;
	private Map<Marker, VenueRef> markers;
	private GoogleMap map;
	private VenuesListener listener;
	
	public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            listener = (VenuesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement VenuesListener");
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mapName = getArguments().getString(MAP_NAME);
		
		markers = new HashMap<Marker, VenueRef>();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (listener.getVenues() != null) {
			setVenues(listener.getVenues());
		}
	}
		
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		map = getMap();
		map.setOnInfoWindowClickListener(this);
		
		return view;
	}

	public void setVenues(final List<VenueRef> venues) {
		markers.clear();
		map.clear();
		
		LatLngBounds.Builder builder = LatLngBounds.builder();
		
		for (VenueRef venue : venues) {
			LatLng latLng = new LatLng(venue.getLatitude(), venue.getLongitude());
			
			builder.include(latLng);
			
			markers.put(map.addMarker(new MarkerOptions().position(latLng)
						.title(venue.getName()).snippet(venue.getDistance())), venue);
		}
		
		final LatLngBounds bounds = builder.build();
		
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING));
			}
		});
	}

	@Override
	public void onInfoWindowClick(final Marker marker) {
		VenueRef venue = markers.get(marker);
		
		if (venue == null) {
			return;
		}
		
		Intent intent = new Intent(getActivity(), VenueActivity.class);
	    intent.putExtra(MAP_NAME, mapName);
	    intent.putExtra(VENUE_ID, venue.getVenueId());
	    intent.putExtra(NAME, venue.getName());
	    
	    getActivity().startActivity(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		map = null;
	}
}
