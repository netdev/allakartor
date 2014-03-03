package se.netdev.allakartor.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.netdev.allakartor.R;
import se.netdev.allakartor.entities.Site;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SitesDialogFragment extends DialogFragment {
	private static final String ALL_SITES = "allSites";
	private static final String SELECTED_SITES = "selectedSites";
	
	private ArrayList<Site> selectedSites;
	
	public interface SitesDialogListener {
        public void onDialogPositiveClick(final ArrayList<Site> selectedSites);
    }

    private SitesDialogListener listener;
	
	public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            listener = (SitesDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SitesDialogListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		final ArrayList<Site> allSites = getArguments().getParcelableArrayList(ALL_SITES);
		
		// Don't make changes to the original list if the user cancels the dialog
		final ArrayList<Site> unmodifiableSites = getArguments().getParcelableArrayList(SELECTED_SITES);
		
		if (savedInstanceState != null) {
			selectedSites = savedInstanceState.getParcelableArrayList(SELECTED_SITES);
		} else {
			selectedSites = new ArrayList<Site>(unmodifiableSites);
		}
		
		CharSequence[] items = new CharSequence[allSites.size()];
		boolean[] checkedItems = new boolean[allSites.size()];
		
		for (int i = 0; i < allSites.size(); i++) {
			items[i] = allSites.get(i).getTitle();
			checkedItems[i] = false;
			
			for (Site site : selectedSites) {
				if (site.equals(allSites.get(i))) {
					checkedItems[i] = true;
					break;
				}
			}
		}

	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    builder.setTitle(R.string.select_maps);	

	    builder.setMultiChoiceItems(items, checkedItems,
	    	new DialogInterface.OnMultiChoiceClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                	if (isChecked) {
                    	selectedSites.add(allSites.get(which));
                	} else {
            			for (Site site : selectedSites) {
            				if (site.equals(allSites.get(which))) {
            					selectedSites.remove(site);
            					break;
            				}
            			}
                   }
               }
	    	}
	    );
	    
       builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
    	   @Override
           public void onClick(final DialogInterface dialog, final int id) {
    		   Collections.sort(selectedSites, new Comparator<Site>() {
    			   @Override
    			   public int compare(final Site lhs, final Site rhs) {
    				   return lhs.getTitle().compareTo(rhs.getTitle());
    			   }  
    		   });
    		   
    		   listener.onDialogPositiveClick(selectedSites);
           }
       });
       
       builder.setNegativeButton(R.string.cancel, null);
       
       return builder.create();
	}
	
	@Override
	public void onSaveInstanceState(final Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putParcelableArrayList(SELECTED_SITES, selectedSites);
	}
}
