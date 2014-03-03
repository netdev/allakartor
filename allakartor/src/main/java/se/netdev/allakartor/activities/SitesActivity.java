package se.netdev.allakartor.activities;

import java.util.ArrayList;

import se.netdev.allakartor.R;
import se.netdev.allakartor.entities.Site;
import se.netdev.allakartor.fragments.SitesDialogFragment;
import se.netdev.allakartor.fragments.SitesDialogFragment.SitesDialogListener;
import se.netdev.allakartor.fragments.SitesListFragment;
import se.netdev.allakartor.managers.SettingsManager;

import android.os.Bundle;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

public class SitesActivity extends BaseActivity implements SitesDialogListener {
	private static final String LIST = "list";
	private static final String DIALOG = "dialog";
	private static final String ALL_SITES = "allSites";
	private static final String SELECTED_SITES = "selectedSites";
	
	private ArrayList<Site> allSites;
	private ArrayList<Site> selectedSites;
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		
		allSites = new ArrayList<Site>();
        
        allSites.add(new Site(getString(R.string.badkartan), "badkartan"));
        allSites.add(new Site(getString(R.string.cafekartan), "cafekartan"));
        allSites.add(new Site(getString(R.string.campingkartan), "campingkartan"));
        allSites.add(new Site(getString(R.string.flygkartan), "flygkartan"));
        allSites.add(new Site(getString(R.string.gymnasiekartan), "gymnasiekartan"));
        allSites.add(new Site(getString(R.string.hotellkartan), "hotellkartan"));
        allSites.add(new Site(getString(R.string.kyrkokartan), "kyrkokartan"));
        allSites.add(new Site(getString(R.string.pizzakartan), "pizzakartan"));
        allSites.add(new Site(getString(R.string.restaurangkartan), "restaurangkartan"));
        allSites.add(new Site(getString(R.string.sushikartan), "sushikartan"));
        allSites.add(new Site(getString(R.string.vandrarhemskartan), "vandrarhemskartan"));
        allSites.add(new Site(getString(R.string.vintagekartan), "vintagekartan"));
        allSites.add(new Site(getString(R.string.wifikartan), "wifikartan"));
        
		selectedSites = SettingsManager.getInstance().getSelectedSites(this);
		
		if (selectedSites.size() == 0) {
			selectedSites.addAll(allSites);
		}
		
		if (savedInstanceState != null) {
			SitesListFragment fragment = (SitesListFragment)getFragmentManager().findFragmentById(android.R.id.content);
			
			if (fragment != null && fragment.getTag().equals(LIST)) {
	    		fragment.setSites(selectedSites);
			}
		} else {
			SitesListFragment fragment = new SitesListFragment();
			
			Bundle args = new Bundle();
		    args.putParcelableArrayList(SELECTED_SITES, selectedSites);
		    fragment.setArguments(args);
		    
		    getFragmentManager().beginTransaction().add(android.R.id.content, fragment, LIST).commit();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.sites_actions, menu);
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            SitesDialogFragment fragment = new SitesDialogFragment();
	            
	            Bundle args = new Bundle();
			    args.putParcelableArrayList(ALL_SITES, allSites);
			    args.putParcelableArrayList(SELECTED_SITES, selectedSites); 
			    fragment.setArguments(args);
			    
	            fragment.show(getFragmentManager(), DIALOG);
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onDialogPositiveClick(final ArrayList<Site> selectedSites) {
		this.selectedSites = selectedSites;
		
		SettingsManager.getInstance().setSelectedSites(this, selectedSites);
		
		Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
		
		if (fragment != null && fragment.getTag().equals(LIST)) {
    		((SitesListFragment)fragment).setSites(selectedSites);
		}
	}
}
