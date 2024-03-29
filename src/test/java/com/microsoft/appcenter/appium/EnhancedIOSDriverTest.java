package com.microsoft.appcenter.appium;

import org.junit.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

import com.microsoft.appcenter.appium.Factory;

import io.appium.java_client.MobileElement;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import org.junit.rules.TestWatcher;
import org.junit.Rule;

public class EnhancedIOSDriverTest {

    public static final byte[] PNG = OutputType.BYTES.convertFromBase64Png("amZsanNmbGtkc2pmbGtzZGpmbGtkc2ZqbGtkc2ZqZGxza2ZqZHNsa2Zq");
    private static AppiumMock appiumMock;
    private MemoryEventReporter reporter = new MemoryEventReporter();

//    @Rule
//    public Watcher watcher = new Watcher(reporter);
    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    private static EnhancedIOSDriver<MobileElement> driver;

    @BeforeClass
    public static void mockAppium() throws IOException {
        appiumMock = new AppiumMock();
        appiumMock.start();
    }

    @AfterClass
    public static void stopAppium() throws IOException {
        appiumMock.stop();
    }

    @Before
    public void setUp() throws MalformedURLException {
        driver = Factory.createIOSDriver(new URL("http://localhost:8001"), new DesiredCapabilities());
    }

    @After
    public void reset() {
        reporter.clear();
    }

    @Test
    public void testMark() throws Exception {
        driver.label("Lallerkok");
        //assertTrue("Expected Lallerkok", reporter.getReports().stream().anyMatch(e -> e.getLabel() != null && e.getLabel().equals("Lallerkok")));
    }

    @Test
    public void testGetScreenshotAs() throws Exception {
        driver.getScreenshotAs(OutputType.BASE64);
        //assertTrue("Expected screenshot", reporter.getReports().stream().anyMatch(e -> e.getScreenshotPath() != null && new File(e.getScreenshotPath()).exists()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyLabelsNotAllowed() {
        driver.label("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullLabelsNotAllowed() {
        driver.label(null);
    }

    @Test
    public void test256CharLabelsAreAllowed() {
        String longLabel = createLabelOfSize(256);
        driver.label(longLabel);
        //expected 128 to be allowed
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVeryLongLabelsAreNotAllowed() {
        String longLabel = createLabelOfSize(257);
        driver.label(longLabel);
    }

    private String createLabelOfSize(int i) {
        StringBuilder sb = new StringBuilder(i);
        while (i-- > 0) {
            sb.append("a");
        }
        return sb.toString();
    }
}