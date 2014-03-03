package se.netdev.allakartor.test.operations;

import com.sogeti.droidnetworking.NetworkEngine;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.RobolectricBackgroundExecutorService;

import java.io.File;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
public abstract class OperationTest {
    @Before
    public void setup() {
        NetworkEngine.getInstance().setSharedNetworkQueue(new RobolectricBackgroundExecutorService());

        Robolectric.getBackgroundScheduler().pause();
        Robolectric.getUiThreadScheduler().pause();
    }

    protected String readJsonFile(final String filename) {
        try {
            String str = FileUtils.readFileToString(new File("src/test/res/raw/" + filename));
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
