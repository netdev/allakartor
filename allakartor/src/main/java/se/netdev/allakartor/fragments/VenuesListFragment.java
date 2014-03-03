package se.netdev.allakartor.fragments;

import java.util.ArrayList;

import se.netdev.allakartor.VenuesListener;
import se.netdev.allakartor.adapters.VenuesAdapter;
import se.netdev.allakartor.entities.VenueRef;
import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;

public class VenuesListFragment extends ListFragment {
	private static final String MAP_NAME = "mapName";
	
	private VenuesListener listener;
	private VenuesAdapter adapter;
	
	public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            listener = (VenuesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement VenuesListener");
        }
    }
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String mapName = getArguments().getString(MAP_NAME);
		
		adapter = new VenuesAdapter(getActivity(), mapName, new ArrayList<VenueRef>());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setOnItemClickListener(adapter);
		
		setListAdapter(adapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();

		if (listener.getVenues() != null) {
			setVenues(listener.getVenues());
		}
	}
	
	public void setVenues(final ArrayList<VenueRef> venues) {
		adapter.setVenues(venues);	
	}
}
