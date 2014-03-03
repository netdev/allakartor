package se.netdev.allakartor.test.adapters;

import java.util.ArrayList;

import se.netdev.allakartor.R;
import se.netdev.allakartor.adapters.VenuesAdapter;
import se.netdev.allakartor.entities.VenueRef;

import android.view.View;
import android.widget.ImageView;
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
public class VenuesAdapterTest {
    @Test
	public void testVenuesAdapter() {
		VenuesAdapter adapter = new VenuesAdapter(new Activity(), "cafekartan", new ArrayList<VenueRef>());
    	
		ArrayList<VenueRef> venues = new ArrayList<VenueRef>();
    	
    	VenueRef firstVenue = new VenueRef();
    	firstVenue.setDistance("30 meter bort");
    	firstVenue.setGrade(3.0f);
    	firstVenue.setName("Gustav III:s kafé");
    	
    	VenueRef secondVenue = new VenueRef();
    	secondVenue.setDistance("77 meter bort");
    	secondVenue.setGrade(3.0f);
    	secondVenue.setName("Café de Maré");
    	
    	venues.add(firstVenue);
    	venues.add(secondVenue);
    	
    	adapter.setVenues(venues);
    	
    	assertTrue(adapter.getCount() == 2);
    	
    	assertTrue(adapter.getItem(0).equals(venues.get(0)));
    	assertTrue(adapter.getItem(1).equals(venues.get(1)));
    	
    	View view = adapter.getView(0, null, null);
    	
    	TextView venueName = (TextView) view.findViewById(R.id.venue_name);
    	ImageView image = (ImageView) view.findViewById(R.id.image);
    	TextView distance = (TextView) view.findViewById(R.id.distance);
    	RatingBar grade = (RatingBar) view.findViewById(R.id.grade);

    	assertTrue(venueName.getText().equals("Gustav III:s kafé"));
    	assertTrue(image.getDrawable() != null);
    	assertTrue(distance.getText().equals("30 meter bort"));
    	assertTrue(grade.getRating() == 3.0f);
    	
    	view = adapter.getView(1, null, null);
    	
    	venueName = (TextView) view.findViewById(R.id.venue_name);
    	image = (ImageView) view.findViewById(R.id.image);
    	distance = (TextView) view.findViewById(R.id.distance);
    	grade = (RatingBar) view.findViewById(R.id.grade);
    	
    	assertTrue(venueName.getText().equals("Café de Maré"));
    	assertTrue(image.getDrawable() != null);
    	assertTrue(distance.getText().equals("77 meter bort"));
    	assertTrue(grade.getRating() == 3.0f);
    }
}
