package se.netdev.allakartor.operations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sogeti.droidnetworking.NetworkOperation;
import com.sogeti.droidnetworking.NetworkEngine.HttpMethod;

public class GetImageOperation extends NetworkOperation {
    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    
    private Bitmap bitmap;
    
    public interface GetImageOperationCallback {
        void done(final Bitmap bitmap, final Exception exception);
    }
            
    public GetImageOperation(final String url, final int maxWidth, final int maxHeight,
            final GetImageOperationCallback callback) {
        setUrlString(url);
        setUseGzip(false);
        setHttpMethod(HttpMethod.GET);
        
        setParser(new ResponseParser() {
            @Override
            public void parse(final InputStream is, final long size) {
                if (size > MAX_FILE_SIZE) {
                    return;
                }

                int bufferSize = (int) size;

                // Wrapping the InputStream in a BufferedInputStream in order to
                // use reset
                BufferedInputStream bis = new BufferedInputStream(is, bufferSize);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPurgeable = true;

                // Decode to check the size of the image
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(bis, null, options);

                try {
                    bis.reset();
                } catch (IOException e) {
                    return;
                }

                options.inJustDecodeBounds = false;
                options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);

                bitmap = BitmapFactory.decodeStream(bis, null, options);

                if (bitmap == null) {
                    return;
                }
            }
        });
        
        setListener(new OperationListener() {
            @Override
            public void onCompletion(final NetworkOperation operation) {
                callback.done(bitmap, null);
            }

            @Override
            public void onError(final NetworkOperation operation) {
                callback.done(null, new Exception(operation.getUrlString()
                        + " failed with status " + operation.getHttpStatusCode()));
            }
        });
    }
    
    private static int calculateInSampleSize(
            final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
