package se.netdev.allakartor.test.operations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;

import org.robolectric.tester.org.apache.http.HttpEntityStub;

public class TestHttpResponse extends org.robolectric.tester.org.apache.http.TestHttpResponse {
    private TestHttpEntity httpEntity = new TestHttpEntity();
    private String responseBody;

    public TestHttpResponse(int statusCode, String responseBody) {
        super(statusCode, responseBody);
        this.responseBody = responseBody;
    }

    @Override public HttpEntity getEntity() {
        return httpEntity;
    }

    public class TestHttpEntity extends HttpEntityStub {
        @Override public long getContentLength() {
            return responseBody.length();
        }

        @Override public InputStream getContent() throws IOException, IllegalStateException {
            return new ByteArrayInputStream(responseBody.getBytes());
        }

        @Override public void writeTo(OutputStream outputStream) throws IOException {
            outputStream.write(responseBody.getBytes());
        }

        @Override public void consumeContent() throws IOException {
        }

        @Override
        public Header getContentType() {
            return new Header() {

                @Override
                public String getValue() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public String getName() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public HeaderElement[] getElements() throws ParseException {
                    // TODO Auto-generated method stub
                    return null;
                }
            };
        }
        @Override
        public Header getContentEncoding() {
            return new Header() {

                @Override
                public String getValue() {
                    // TODO Auto-generated method stub
                    return "gzip";
                }

                @Override
                public String getName() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public HeaderElement[] getElements() throws ParseException {
                    HeaderElement[] elements = new HeaderElement[0];

                    return elements;
                }
            };
        }
    }
}
