package se.netdev.allakartor.test.operations;

import android.location.Location;

import com.sogeti.droidnetworking.NetworkEngine;

import org.json.JSONException;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;

import java.util.ArrayList;

import se.netdev.allakartor.entities.VenueRef;
import se.netdev.allakartor.operations.GetVenuesOperation;
import se.netdev.allakartor.operations.GetVenuesOperation.GetVenuesCallback;

public class GetVenuesOperationTest extends OperationTest {
    @Test
    public void testGetVenuesWithLocation() throws Throwable {
        Location location = new Location("");
        location.setLatitude(59.3300);
        location.setLongitude(18.0700);

        GetVenuesOperation operation = new GetVenuesOperation("cafekartan", location, new GetVenuesCallback() {
            @Override
            public void done(ArrayList<VenueRef> venues, Exception exception) {
                assertFalse(venues == null);
                assertTrue(exception == null);

                assertTrue(venues.size() == 20);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("get_venues.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&lat=59.33&long=18.07 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testGetVenuesWithoutLocation() throws Throwable {
        GetVenuesOperation operation = new GetVenuesOperation("cafekartan", null, new GetVenuesCallback() {
            @Override
            public void done(ArrayList<VenueRef> venues, Exception exception) {
                assertFalse(venues == null);
                assertTrue(exception == null);

                assertTrue(venues.size() == 20);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("get_venues.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&lat=59.33&long=18.07 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testGetVenuesWithServerError() throws Throwable {
        GetVenuesOperation operation = new GetVenuesOperation("cafekartan", null, new GetVenuesCallback() {
            @Override
            public void done(ArrayList<VenueRef> venues, Exception exception) {
                assertTrue(venues == null);
                assertTrue(exception != null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(404, "");
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&lat=59.33&long=18.07 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testGetVenuesWithCorruptData() throws Throwable {
        GetVenuesOperation operation = new GetVenuesOperation("cafekartan", null, new GetVenuesCallback() {
            @Override
            public void done(ArrayList<VenueRef> venues, Exception exception) {
                assertTrue(venues == null);
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

        assertEquals("GET http://www.allakartor.se/api?key=mada2053&webpage=cafekartan&lat=59.33&long=18.07 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }
}
