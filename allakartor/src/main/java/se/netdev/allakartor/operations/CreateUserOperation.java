package se.netdev.allakartor.operations;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import se.netdev.allakartor.ApplicationEx;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;
import com.sogeti.droidnetworking.external.MD5;

public class CreateUserOperation extends NetworkOperation {
	private static final String RESULT = "result";
	private static final String OK = "ok";
	
	public interface CreateUserCallback {
        void done(final Exception exception);
    }
	
	public CreateUserOperation(final String email, final String password, final String city,
			final String zip, final String description, final String username, final CreateUserCallback callback)  {
	    
		try {
		    setUrlString(ApplicationEx.SECURE_API_URL + "?key="+ ApplicationEx.API_KEY + "&action=create_user"
		    		+ "&username=" + URLEncoder.encode(username, "UTF-8")
		    		+ "&user_city=" + URLEncoder.encode(city, "UTF-8")
		    		+ "&user_zip_code=" + URLEncoder.encode(zip, "UTF-8")
		    		+ "&user_desciption=" + URLEncoder.encode(description, "UTF-8")
		    		+ "&useremail=" + URLEncoder.encode(email, "UTF-8")
		    		+ "&password=" + MD5.encodeString(password));
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
