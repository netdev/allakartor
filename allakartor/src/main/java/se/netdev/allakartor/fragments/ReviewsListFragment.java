package se.netdev.allakartor.fragments;

import java.util.ArrayList;

import se.netdev.allakartor.adapters.ReviewsAdapter;
import se.netdev.allakartor.entities.Review;

import android.app.ListFragment;
import android.os.Bundle;

public class ReviewsListFragment extends ListFragment {
	private static final String REVIEWS = "reviews";
	
	private ReviewsAdapter adapter;
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ArrayList<Review> reviews = getArguments().getParcelableArrayList(REVIEWS);
		adapter = new ReviewsAdapter(getActivity(), reviews);
	}
	
	@Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        setListAdapter(adapter);
    }
	
	public void setReviews(final ArrayList<Review> reviews) {
		adapter.setReviews(reviews);	
	}
}
