package se.netdev.allakartor.managers;

import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONException;

import se.netdev.allakartor.entities.Site;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

public class SettingsManager {
	private static final String TAG = "SettingsManager";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String SELECTED_SITES = "selectedSites";
	
	private static final int FLAGS = 0;
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = new byte[] {(byte) 0x21, (byte) 0x1c, (byte) 0xf1, (byte) 0x19,
                                                  (byte) 0xd1, (byte) 0x61, (byte) 0xc9, (byte) 0xc1,
                                                  (byte) 0xc2, (byte) 0x71, (byte) 0x6b, (byte) 0xb2,
                                                  (byte) 0x1b, (byte) 0x1e, (byte) 0x29, (byte) 0xe6};
	
	private static SettingsManager instance;
	
	public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }
	
	public ArrayList<Site> getSelectedSites(final Context context) {
		ArrayList<Site> selectedSites = new ArrayList<Site>();
		
		try {
			JSONArray jsonArray = new JSONArray(PreferenceManager.getDefaultSharedPreferences(context).getString(SELECTED_SITES, "[]"));
		
			for (int i = 0; i < jsonArray.length(); i++) {
				Site site = new Site(jsonArray.getJSONObject(i));
				selectedSites.add(site);
			}
		} catch (JSONException e) {
			Log.e(TAG, "Could not load sites.");
		}
		
        return selectedSites;
    }

    public void setSelectedSites(final Context context, final ArrayList<Site> selectedSites) {
    	try {
	        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
	        
	        JSONArray jsonArray = new JSONArray();
	        
	        for (Site site : selectedSites) {
	        	jsonArray.put(site.serialize());
	        }
	        
	        editor.putString(SELECTED_SITES, jsonArray.toString());
	        editor.commit();
    	} catch (JSONException e) {
    		Log.e(TAG, "Could not save sites.");
    	}
    }
    
    public void setCurrentUser(final Context context, final String username, final String password) {
    	SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    	
    	editor.putString(USERNAME, username);
    	editor.putString(PASSWORD, encrypt(password));
    	
    	editor.commit();
    }
    
    public void logoutCurrentUser(final Context context) {
    	SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

    	editor.remove(USERNAME);
    	editor.remove(PASSWORD);

    	editor.commit();
    }
    
    public String getUsername(final Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getString(USERNAME, null);
    }
    
    public String getPassword(final Context context) {
    	String encryptedPassword = PreferenceManager.getDefaultSharedPreferences(context).getString(PASSWORD, null);

        if (encryptedPassword != null) {
            return decrypt(encryptedPassword);
        } else {
            return null;
        }
    }
    
    public boolean isLoggedIn(final Context context) {
        return getUsername(context) != null && getPassword(context) != null;
    }
    
    private String encrypt(final String password) {
        try {
            Key key = new SecretKeySpec(KEY, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeToString(c.doFinal(password.getBytes("UTF-8")), FLAGS);
        } catch (Exception e) {
            return null;
        }
    }

    private String decrypt(final String encryptedPassword) {
        try {
            Key key = new SecretKeySpec(KEY, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.decode(encryptedPassword, FLAGS)), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }
}
