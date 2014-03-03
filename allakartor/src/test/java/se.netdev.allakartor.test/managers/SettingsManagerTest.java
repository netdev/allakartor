package se.netdev.allakartor.test.managers;

import java.util.ArrayList;

import se.netdev.allakartor.entities.Site;
import se.netdev.allakartor.managers.SettingsManager;

import android.app.Activity;

import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SettingsManagerTest {
    Activity activity;

    @Before
    public void setup() {
        activity = new Activity();
    }

    @Test
    public void testSetCurrentUser() {
    	SettingsManager.getInstance().setCurrentUser(activity, "info@allakartorapp.se", "allakartor123");

        assertTrue(SettingsManager.getInstance() != null);

        assertTrue(activity != null);

        assertTrue(SettingsManager.getInstance().getUsername(activity) != null);
        assertTrue(SettingsManager.getInstance().getPassword(activity) != null);
    	
    	assertTrue(SettingsManager.getInstance().getUsername(activity).equals("info@allakartorapp.se"));
    	assertTrue(SettingsManager.getInstance().getPassword(activity).equals("allakartor123"));
    }

    @Test
    public void testIsLoggedIn() {
    	SettingsManager.getInstance().setCurrentUser(activity, "info@allakartorapp.se", "allakartor123");
    	
    	assertTrue(SettingsManager.getInstance().isLoggedIn(activity) == true);
    }

    @Test
    public void testLogoutCurrentUser() {
    	SettingsManager.getInstance().setCurrentUser(activity, "info@allakartorapp.se", "allakartor123");
    	
    	assertTrue(SettingsManager.getInstance().isLoggedIn(activity) == true);
    	
    	SettingsManager.getInstance().logoutCurrentUser(activity);
    	
    	assertTrue(SettingsManager.getInstance().isLoggedIn(activity) == false);
    }

    @Test
    public void testSetSelectedSites() {
    	ArrayList<Site> sites = new ArrayList<Site>();
        
        sites.add(new Site("Badkartan.se", "badkartan"));
        sites.add(new Site("Cafekartan.se", "cafekartan"));
        
        SettingsManager.getInstance().setSelectedSites(activity, sites);
        
        assertTrue(SettingsManager.getInstance().getSelectedSites(activity).size() == 2);
        
        sites.clear();
        
        SettingsManager.getInstance().setSelectedSites(activity, sites);
        
        assertTrue(SettingsManager.getInstance().getSelectedSites(activity).size() == 0);
    }
}
