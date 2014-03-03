package se.netdev.allakartor.test.operations;

import com.sogeti.droidnetworking.NetworkEngine;

import org.junit.Test;
import org.robolectric.Robolectric;

import se.netdev.allakartor.entities.Venue;
import se.netdev.allakartor.operations.GetVenueOperation;
import se.netdev.allakartor.operations.GetVenueOperation.GetVenueCallback;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;

import org.json.JSONException;

public class GetVenueOperationTest extends OperationTest {
    @Test
    public void testGetVenue() throws Throwable {
        GetVenueOperation operation = new GetVenueOperation("cafekartan", "15919", new GetVenueCallback() {
            @Override
            public void done(Venue venue, Exception exception) {
                assertFalse(venue == null);
                assertTrue(exception == null);
                assertTrue(venue.getName().equals("Kungliga Operans Str\u00F6mkaf\u00E9"));
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("get_venue.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&venue=15919 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testGetVenueWithServerError() throws Throwable {
        GetVenueOperation operation = new GetVenueOperation("cafekartan", "15919", new GetVenueCallback() {
            @Override
            public void done(Venue venue, Exception exception) {
                assertTrue(venue == null);
                assertTrue(exception != null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(404, "");
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&venue=15919 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testGetVenueWithCorruptData() throws Throwable {
        GetVenueOperation operation = new GetVenueOperation("cafekartan", "15919", new GetVenueCallback() {
            @Override
            public void done(Venue venue, Exception exception) {
                assertTrue(venue == null);
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

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&venue=15919 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }
}
