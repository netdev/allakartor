package se.netdev.allakartor.test.adapters;

import java.util.ArrayList;
import java.util.List;

import se.netdev.allakartor.R;
import se.netdev.allakartor.adapters.SitesAdapter;
import se.netdev.allakartor.entities.Site;

import android.view.View;
import android.widget.ImageView;
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
public class SitesAdapterTest {
    @Test
	public void testSitesAdapter() {
		SitesAdapter adapter = new SitesAdapter(new Activity(), new ArrayList<Site>());
    	
    	List<Site> sites = new ArrayList<Site>();
    	
    	sites.add(new Site("Badkartan.se", "badkartan"));
    	sites.add(new Site("Cafekartan.se", "cafekartan"));
    	
    	adapter.setSites(sites);
    	
    	assertTrue(adapter.getCount() == 2);
    	
    	assertTrue(adapter.getItem(0).equals(sites.get(0)));
    	assertTrue(adapter.getItem(1).equals(sites.get(1)));
    	
    	View view = adapter.getView(0, null, null);
    	
    	TextView name = (TextView) view.findViewById(R.id.name);
    	ImageView icon = (ImageView) view.findViewById(R.id.icon);
    	
    	assertTrue(name.getText().equals("Badkartan.se"));
    	assertTrue(icon.getDrawable() != null);
    	
    	view = adapter.getView(1, null, null);
    	
    	name = (TextView) view.findViewById(R.id.name);
    	icon = (ImageView) view.findViewById(R.id.icon);
    	
    	assertTrue(name.getText().equals("Cafekartan.se"));
    	assertTrue(icon.getDrawable() != null);
    }
}
