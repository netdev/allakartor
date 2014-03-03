package se.netdev.allakartor.test.operations;

import com.sogeti.droidnetworking.NetworkEngine;

import org.json.JSONException;
import org.junit.Test;
import org.robolectric.Robolectric;

import se.netdev.allakartor.operations.WriteReviewOperation;
import se.netdev.allakartor.operations.WriteReviewOperation.WriteReviewCallback;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;

public class WriteReviewOperationTest extends OperationTest {
    private static final String REVIEW_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc euismod congue nibh, vitae ullamcorper nisl ullamcorper a. Aliquam vel orci et eros eleifend mattis. Fusce et odio non augue mollis.";
    private static final String REVIEW_TEXT_ENCODED = "Lorem+ipsum+dolor+sit+amet%2C+consectetur+adipiscing+elit.+Nunc+euismod+congue+nibh%2C+vitae+ullamcorper+nisl+ullamcorper+a.+Aliquam+vel+orci+et+eros+eleifend+mattis.+Fusce+et+odio+non+augue+mollis.";
    private static final String REVIEW_TEXT_SHORT = "Lorem ipsum dolor sit amet";
    private static final String REVIEW_TEXT_SHORT_ENCODED = "Lorem+ipsum+dolor+sit+amet";

    @Test
    public void testWriteValidReview() throws Throwable {
        WriteReviewOperation operation = new WriteReviewOperation("info@allakartorapp.se", "allakartor123", "cafekartan", "15847", REVIEW_TEXT, 4.0f, new WriteReviewCallback() {
            @Override
            public void done(Exception exception) {
                assertTrue(exception == null);
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("write_review_ok.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&review=15847&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db&review_text=" + REVIEW_TEXT_ENCODED + "&review_grade=4.0 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testWriteTooShortReview() throws Throwable {
        WriteReviewOperation operation = new WriteReviewOperation("info@allakartorapp.se", "allakartor123", "cafekartan", "15847", REVIEW_TEXT_SHORT, 4.0f, new WriteReviewCallback() {
            @Override
            public void done(Exception exception) {
                assertTrue(exception != null);
                assertTrue(exception.getMessage().equals("error_review_too_short"));
            }
        });

        NetworkEngine.getInstance().enqueueOperation(operation);

        TestHttpResponse response = new TestHttpResponse(200, readJsonFile("write_review_too_short.json"));
        Robolectric.addPendingHttpResponse(response);

        Robolectric.getBackgroundScheduler().runOneTask();
        Robolectric.getUiThreadScheduler().runOneTask();

        HttpRequest sentHttpRequest = Robolectric.getSentHttpRequest(0);

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&review=15847&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db&review_text=" + REVIEW_TEXT_SHORT_ENCODED + "&review_grade=4.0 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testWriteValidReviewAndCorruptData() throws Throwable {
        WriteReviewOperation operation = new WriteReviewOperation("info@allakartorapp.se", "allakartor123", "cafekartan", "15847", REVIEW_TEXT, 4.0f, new WriteReviewCallback() {
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

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&review=15847&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db&review_text=" + REVIEW_TEXT_ENCODED + "&review_grade=4.0 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }

    @Test
    public void testWriteValidReviewAndServerError() throws Throwable {
        WriteReviewOperation operation = new WriteReviewOperation("info@allakartorapp.se", "allakartor123", "cafekartan", "15847", REVIEW_TEXT, 4.0f, new WriteReviewCallback() {
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

        assertEquals("GET https://www.allakartor.se/api?key=mada2053&webpage=cafekartan&review=15847&useremail=info%40allakartorapp.se&password=93d5629ec7b1bb38530170a16fbd22db&review_text=" + REVIEW_TEXT_ENCODED + "&review_grade=4.0 HTTP/1.1", sentHttpRequest.getRequestLine().toString());
    }
}
