package se.netdev.allakartor.test.operations;

import com.sogeti.droidnetworking.NetworkEngine;

import org.json.JSONException;
import org.junit.Test;
import org.robolectric.Robolectric;

import se.netdev.allakartor.operations.CreateUserOperation;
import se.netdev.allakartor.operations.CreateUserOperation.CreateUserCallback;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;

public class CreateUserOperationTest extends OperationTest {
    @Test
    public void testCreateValidUser() throws Throwable {
        CreateUserOperation operation = new CreateUserOperation("support@allakartorapp.se", "allakartor123", "Lund", "226 55", "", "Martin Dahl", new CreateUserCallback() {
            @Override
            public void done(Exception exception) {
                assertTrue(exception == null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("create_user_ok.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&action=create_user&username=Martin+Dahl&user_city=Lund&user_zip_code=226+55&user_desciption=&useremail=support%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testCreateInvalidUser() throws Throwable {
        CreateUserOperation operation = new CreateUserOperation("support@allakartorapp.se", "allakartor123", "Lund", "226 55", "", "Martin Dahl", new CreateUserCallback() {
            @Override
            public void done(Exception exception) {
                assertTrue(exception != null);
                assertTrue(exception.getMessage().equals("error_email_in_use"));
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("create_user_email_in_use.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&action=create_user&username=Martin+Dahl&user_city=Lund&user_zip_code=226+55&user_desciption=&useremail=support%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testCreateValidUserWithCorruptData() throws Throwable {
        CreateUserOperation operation = new CreateUserOperation("support@allakartorapp.se", "allakartor123", "Lund", "226 55", "", "Martin Dahl", new CreateUserCallback() {
            @Override
            public void done(Exception exception) {
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

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&action=create_user&username=Martin+Dahl&user_city=Lund&user_zip_code=226+55&user_desciption=&useremail=support%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testCreateValidUserWithServerError() throws Throwable {
        CreateUserOperation operation = new CreateUserOperation("support@allakartorapp.se", "allakartor123", "Lund", "226 55", "", "Martin Dahl", new CreateUserCallback() {
            @Override
            public void done(Exception exception) {
                assertTrue(exception != null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(404, "");
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&action=create_user&username=Martin+Dahl&user_city=Lund&user_zip_code=226+55&user_desciption=&useremail=support%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }
}