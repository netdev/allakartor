package se.netdev.allakartor.operations;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;
import com.sogeti.droidnetworking.external.MD5;

public class WriteReviewOperation extends NetworkOperation {
	private static final String RESULT = "result";
	private static final String OK = "ok";
	
	public interface WriteReviewCallback {
        void done(final Exception exception);
    }
	
	public WriteReviewOperation(final String email, final String password, final String mapName,
			final String venueId, final String text, final float grade,
			final WriteReviewCallback callback) {	
        
		try {
			setUrlString(ApplicationEx.SECURE_API_URL + "?key="+ ApplicationEx.API_KEY + "&webpage=" + mapName
					+ "&review=" + venueId + "&useremail=" + URLEncoder.encode(email, "UTF-8") + "&password=" + MD5.encodeString(password)
					+ "&review_text=" + URLEncoder.encode(text, "UTF-8") + "&review_grade=" + grade);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 is unknown");
		}

        setHttpMethod(HttpMethod.GET);
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
            	try {
            		JSONObject jsonObject = new JSONObject(operation.getResponseString());
            		
            		String result = jsonObject.getString(RESULT);
            		
            		if (result.equals(OK)) {
            			callback.done(null);
            		} else {
            			callback.done(new Exception(result));
            		}
            	} catch (final JSONException exception) {
            		callback.done(exception);
            	}
            }

            @Override
            public void onError(final NetworkOperation operation) {
                callback.done(new Exception(operation.getUrlString()
                		+ " failed with status " + operation.getHttpStatusCode()));
            }
        });
	}
}
