package se.netdev.allakartor.test.adapters;

import java.util.ArrayList;
import java.util.List;

import se.netdev.allakartor.R;
import se.netdev.allakartor.adapters.ReviewsAdapter;
import se.netdev.allakartor.entities.Review;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.Activity;

import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class ReviewsAdapterTest {
    @Test
    public void testReviewsAdapter() {
    	ReviewsAdapter adapter = new ReviewsAdapter(new Activity(), new ArrayList<Review>());
    	
    	List<Review> reviews = new ArrayList<Review>();
    	
    	Review firstReview = new Review();
    	firstReview.setDate("10 dagar sedan");
    	firstReview.setDescription("Väldigt fint café.");
    	firstReview.setGrade(5.0f);
    	firstReview.setUserName("mardah");
    	
    	reviews.add(firstReview);
    	
    	Review secondReview = new Review();
    	secondReview.setDate("20 dagar sedan");
    	secondReview.setDescription("Väldigt fin pizzeria.");
    	secondReview.setGrade(4.0f);
    	secondReview.setUserName("jessnils");
    	
    	reviews.add(secondReview);
    	
    	adapter.setReviews(reviews);
    	
    	assertTrue(adapter.getCount() == 2);
    	
    	assertTrue(adapter.getItem(0).equals(firstReview));
    	assertTrue(adapter.getItem(1).equals(secondReview));
    	
    	View view = adapter.getView(0, null, null);
    	
    	TextView description = (TextView) view.findViewById(R.id.description);
    	TextView date = (TextView) view.findViewById(R.id.date);
    	TextView userName = (TextView) view.findViewById(R.id.user_name);
    	RatingBar grade = (RatingBar) view.findViewById(R.id.grade);
    	
    	assertTrue(description.getText().equals("Väldigt fint café."));
    	assertTrue(date.getText().equals("10 dagar sedan"));
    	assertTrue(userName.getText().equals("mardah"));
    	assertTrue(grade.getRating() == 5.0f);
    }
}
