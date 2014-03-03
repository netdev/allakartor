package se.netdev.allakartor.test.operations;

import com.sogeti.droidnetworking.NetworkEngine;

import org.json.JSONException;
import org.junit.Test;
import org.robolectric.Robolectric;

import se.netdev.allakartor.operations.LoginOperation;
import se.netdev.allakartor.operations.LoginOperation.LoginCallback;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;

public class LoginOperationTest extends OperationTest {
    @Test
    public void testLoginWithValidUser() throws Throwable {
        LoginOperation operation = new LoginOperation("info@allakartorapp.se", "allakartor123", "cafekartan", new LoginCallback() {
            @Override
            public void done(boolean loggedIn, Exception exception) {
                assertTrue(loggedIn == true);
                assertTrue(exception == null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("login_with_valid_user.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&action=login&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testLoginWithInvalidUser() throws Throwable {
        LoginOperation operation = new LoginOperation("info@allakartorapp.se", "allakartor321", "cafekartan", new LoginCallback() {
            @Override
            public void done(boolean loggedIn, Exception exception) {
                assertTrue(loggedIn == false);
                assertTrue(exception == null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("login_with_invalid_user.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&action=login&useremail=info%40allakartorapp.se&password=547d9da63f74d3c483901be2a5c87d56 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testLoginWithValidUserAndServerError() throws Throwable {
        LoginOperation operation = new LoginOperation("info@allakartorapp.se", "allakartor123", "cafekartan", new LoginCallback() {
            @Override
            public void done(boolean loggedIn, Exception exception) {
                assertTrue(loggedIn == false);
                assertTrue(exception != null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(404, "");
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&action=login&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testLoginWithValidUserAndCorruptData() throws Throwable {
        LoginOperation operation = new LoginOperation("info@allakartorapp.se", "allakartor123", "cafekartan", new LoginCallback() {
            @Override
            public void done(boolean loggedIn, Exception exception) {
                assertTrue(loggedIn == false);
                assertTrue(exception != null);
                assertTrue(exception instanceof JSONException);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, "{}");
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&action=login&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }
}
