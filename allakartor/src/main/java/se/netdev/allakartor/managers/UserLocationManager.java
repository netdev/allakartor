package se.netdev.allakartor.managers;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class UserLocationManager {
    public interface OnLocationChangedListener {
        void onLocationChanged(final Location location);
    }

    private ArrayList<OnLocationChangedListener> listeners;

    private static UserLocationManager instance;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentBestLocation;

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    public static UserLocationManager getInstance() {
        if (instance == null) {
            instance = new UserLocationManager();
        }
        return instance;
    }

    public Location getCurrentBestLocation() {
        return currentBestLocation;
    }

    public void addListener(final OnLocationChangedListener listener) {
        listeners.add(listener);
        
        if (listeners.size() == 1) {
            // Catch IllegalArgumentException that sometimes occurs for certain Android versions
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 100, locationListener);
            } catch (IllegalArgumentException e) {}

            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 100, locationListener);
            } catch (IllegalArgumentException e) {}
        }
    }
    
    public void removeListener(final OnLocationChangedListener listener) {
        listeners.remove(listener);

        if (listeners.size() == 0) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void init(final Context context) {
        listeners = new ArrayList<OnLocationChangedListener>();

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        currentBestLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (isBetterLocation(location, currentBestLocation)) {
                    currentBestLocation = location;
                }

                for (int i = 0; i < listeners.size(); i++) {
                    OnLocationChangedListener listener = listeners.get(i);
                    listener.onLocationChanged(currentBestLocation);
                }
            }

            public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

            public void onProviderEnabled(final String provider) {}

            public void onProviderDisabled(final String provider) {}
        };
    }

    private boolean isBetterLocation(final Location location, final Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    private boolean isSameProvider(final String provider1, final String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}

