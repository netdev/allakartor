package se.netdev.allakartor.activities;

import java.util.ArrayList;

import se.netdev.allakartor.R;
import se.netdev.allakartor.entities.Review;
import se.netdev.allakartor.fragments.ReviewsListFragment;
import se.netdev.allakartor.managers.SettingsManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;

public class ReviewsActivity extends BaseActivity {
	private static final String LIST = "list";
	private static final String REVIEWS = "reviews";
	
	private ArrayList<Review> reviews;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			reviews = savedInstanceState.getParcelableArrayList(REVIEWS);
			
			Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
			
			if (fragment != null && fragment.getTag().equals(LIST)) {
	    		((ReviewsListFragment)fragment).setReviews(reviews);
			}
        } else {
            reviews = getIntent().getExtras().getParcelableArrayList(REVIEWS);
            
            ReviewsListFragment fragment = new ReviewsListFragment();
            
            Bundle args = new Bundle();
		    args.putParcelableArrayList(REVIEWS, reviews);
		    fragment.setArguments(args);

		    getFragmentManager().beginTransaction().add(android.R.id.content, fragment, LIST).commit();
        }
		
		actionBar.setTitle(R.string.reviews);
	}
	
	@Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.reviews_actions, menu);
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_new_review:
	            if (SettingsManager.getInstance().isLoggedIn(this)) {
	                Intent intent = new Intent(this, WriteReviewActivity.class);
                    startActivity(intent);
	            } else {
	                Intent intent = new Intent(this, LoginActivity.class);
	                startActivity(intent);
	            }
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putParcelableArrayList(REVIEWS, reviews);
    }
}
