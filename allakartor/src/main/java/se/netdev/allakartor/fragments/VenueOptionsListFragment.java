package se.netdev.allakartor.fragments;

import se.netdev.allakartor.R;
import se.netdev.allakartor.activities.VenueActivity;
import se.netdev.allakartor.adapters.VenueOptionsAdapter;
import se.netdev.allakartor.entities.Venue;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class VenueOptionsListFragment extends ListFragment {
    private VenueOptionsAdapter adapter;
    private TextView description;
	private ImageView image;
	private RatingBar grade;
        
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        VenueActivity activity = (VenueActivity) getActivity();

        adapter = new VenueOptionsAdapter(activity);
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
        
        View headerView = getHeaderView();
		
		description = (TextView) headerView.findViewById(R.id.description);
		image = (ImageView) headerView.findViewById(R.id.image);
		grade = (RatingBar) headerView.findViewById(R.id.grade);
		
        getListView().addHeaderView(headerView);
        getListView().setOnItemClickListener(adapter);
        
        setListAdapter(adapter);
    }
    
    public void setVenue(final Venue venue) {
		description.setText(venue.getDescription());
		image.setImageDrawable(getResources().getDrawable(R.drawable.no_image_100));
		grade.setRating(4.0f);
		adapter.setVenue(venue);
	}
	
	private View getHeaderView() {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View headerView = inflater.inflate(R.layout.venue_header, null);
		
		return headerView;
	}
}
