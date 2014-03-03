package se.netdev.allakartor.operations;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;
import se.netdev.allakartor.entities.Picture;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;

public class GetPicturesOperation extends NetworkOperation {
	private static final String PICTURES = "pictures";
	
	public interface GetPicturesCallback {
        void done(final ArrayList<Picture> pictures, final Exception exception);
    }
			
	public GetPicturesOperation(final String mapName, final String venueId, final GetPicturesCallback callback) {
		setUrlString(ApplicationEx.API_URL + "?key="+ ApplicationEx.API_KEY
				+ "&webpage=" + mapName + "&pictures=" + venueId);

        setHttpMethod(HttpMethod.GET);
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
            	ArrayList<Picture> pictures = new ArrayList<Picture>();
            	
            	try {
            		JSONObject jsonObject = new JSONObject(operation.getResponseString());
            	
            		JSONArray jsonArray = jsonObject.getJSONArray(PICTURES);

            		for (int index = 0; index < jsonArray.length(); index++){
            			pictures.add(new Picture(jsonArray.getJSONObject(index)));
            		}
            		
            		callback.done(pictures, null);
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
