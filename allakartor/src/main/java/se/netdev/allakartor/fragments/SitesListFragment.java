package se.netdev.allakartor.fragments;

import java.util.ArrayList;

import se.netdev.allakartor.adapters.SitesAdapter;
import se.netdev.allakartor.entities.Site;
import android.os.Bundle;
import android.app.ListFragment;

public class SitesListFragment extends ListFragment {
	private static final String SELECTED_SITES = "selectedSites";
	
    private SitesAdapter adapter;
        
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ArrayList<Site> sites = getArguments().getParcelableArrayList(SELECTED_SITES);
        adapter = new SitesAdapter(getActivity(), sites);  
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        getListView().setOnItemClickListener(adapter);
        
        setListAdapter(adapter); 
    }

	public void setSites(final ArrayList<Site> sites) {
		adapter.setSites(sites);
	}
}
