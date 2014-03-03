package se.netdev.allakartor.operations;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;
import com.sogeti.droidnetworking.external.MD5;

public class LoginOperation extends NetworkOperation {
	private static final String RESULT = "result";
	private static final String OK = "ok";
	
	public interface LoginCallback {
        void done(final boolean loggedIn, final Exception exception);
    }
	
	public LoginOperation(final String email, final String password, final String mapName, final LoginCallback callback) {
		try {
			setUrlString(ApplicationEx.SECURE_API_URL + "?key="+ ApplicationEx.API_KEY + "&webpage=" + mapName
					+ "&action=login&useremail=" + URLEncoder.encode(email, "UTF-8") + "&password=" + MD5.encodeString(password));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 is unknown");
		}

        setHttpMethod(HttpMethod.GET);
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
            	try {
            		JSONObject jsonObject = new JSONObject(operation.getResponseString());
            		
            		if (jsonObject.getString(RESULT).equals(OK)) {
            			callback.done(true, null);
            		} else {
            			callback.done(false, null);
            		}
            	} catch (final JSONException exception) {
            		callback.done(false, exception);
            	}
            }

            @Override
            public void onError(final NetworkOperation operation) {
                callback.done(false, new Exception(operation.getUrlString()
                		+ " failed with status " + operation.getHttpStatusCode()));
            }
        });
	}
}
