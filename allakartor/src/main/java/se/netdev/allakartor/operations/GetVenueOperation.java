package se.netdev.allakartor.operations;

import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;
import se.netdev.allakartor.entities.Venue;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;

public class GetVenueOperation extends NetworkOperation {
	public interface GetVenueCallback {
        void done(final Venue venue, final Exception exception);
    }
	
	public GetVenueOperation(final String mapName, final String venueId, final GetVenueCallback callback) {	
		setUrlString(ApplicationEx.API_URL + "?key="+ ApplicationEx.API_KEY
				+ "&webpage=" + mapName + "&venue=" + venueId);

        setHttpMethod(HttpMethod.GET);
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
            	try {
            		JSONObject jsonObject = new JSONObject(operation.getResponseString());
            		
            		Venue venue = new Venue(jsonObject);

            		callback.done(venue, null);
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
