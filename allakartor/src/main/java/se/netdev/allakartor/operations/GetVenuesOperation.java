package se.netdev.allakartor.operations;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;
import se.netdev.allakartor.entities.VenueRef;

import android.location.Location;

import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;
import com.sogeti.droidnetworking.NetworkOperation;

public class GetVenuesOperation extends NetworkOperation {
	private static final String VENUES = "venues";
	private static final double DEFAULT_LATITUDE = 59.3300;
	private static final double DEFAULT_LONGITUDE = 18.0700;
	
	public interface GetVenuesCallback {
        void done(final ArrayList<VenueRef> venues, final Exception exception);
    }
			
	public GetVenuesOperation(final String mapName, final Location location, final GetVenuesCallback callback) {
		double latitude = DEFAULT_LATITUDE;
		double longitude = DEFAULT_LONGITUDE;
		
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
		
		setUrlString(ApplicationEx.API_URL + "?key="+ ApplicationEx.API_KEY
				+ "&webpage=" + mapName + "&lat=" + latitude + "&long=" + longitude);

        setHttpMethod(HttpMethod.GET);
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
            	ArrayList<VenueRef> venues = new ArrayList<VenueRef>();
            	
            	try {
            		JSONObject jsonObject = new JSONObject(operation.getResponseString());
            	
            		JSONArray jsonArray = jsonObject.getJSONArray(VENUES);

            		for (int index = 0; index < jsonArray.length(); index++){
            			venues.add(new VenueRef(jsonArray.getJSONObject(index)));
            		}
            		
            		callback.done(venues, null);
            	} catch (final JSONException exception) {
            		callback.done(null, exception);
            	}
            }

            @Override
            public void onError(final NetworkOperation operation) {
                callback.done(null, new Exception(operation.getUrlString()
                		+ " failed with status " + operation.getHttpStatusCode()));
            }
        });
	}
}
