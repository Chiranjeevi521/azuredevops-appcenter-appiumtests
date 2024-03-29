package com.microsoft.appcenter.appium;

import org.junit.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
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
import java.time.Duration;

public class EnhancedAndroidDriverTest {

    public static final byte[] PNG = OutputType.BYTES.convertFromBase64Png("amZsanNmbGtkc2pmbGtzZGpmbGtkc2ZqbGtkc2ZqZGxza2ZqZHNsa2Zq");
    private static AppiumMock appiumMock;
    private MemoryEventReporter reporter = new MemoryEventReporter();
    Duration fiveSeconds = Duration.ofSeconds(5);

//    @Rule
//    public Watcher watcher = new Watcher(reporter);
    
    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    private static EnhancedAndroidDriver<MobileElement> driver;

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
        driver = Factory.createAndroidDriver(new URL("http://localhost:8001"), new DesiredCapabilities());
    }

    @After
    public void TearDown(){
        driver.label("Stopping App");
        driver.quit();
    }


    @Test
    public void testGetScreenshotAs() throws Exception {
        driver.getScreenshotAs(OutputType.FILE);

    }


    @Test 
    public void screenLockTest() throws InterruptedException {
    	
    	driver.lockDevice();
    	Thread.sleep(5000);
    	driver.getScreenshotAs(OutputType.FILE);
    	driver.unlockDevice();
    	driver.getScreenshotAs(OutputType.FILE);
    }
    
    
  @Test	
  public void closeAndReopenTest() throws InterruptedException {
  	
	  driver.closeApp();
	  driver.getScreenshotAs(OutputType.FILE);
	  driver.launchApp();
	  driver.getScreenshotAs(OutputType.FILE);
  	}
    
    
  @Test
  public void resetApp() throws InterruptedException {
  	
	  driver.resetApp();
	  driver.getScreenshotAs(OutputType.FILE);
  	}
    
    
  @Test 	
  public void orientationTest() throws InterruptedException {
  	driver.rotate(ScreenOrientation.LANDSCAPE);
  	driver.getScreenshotAs(OutputType.FILE);
  	Thread.sleep(5000);
  	driver.rotate(ScreenOrientation.PORTRAIT);
  	driver.getScreenshotAs(OutputType.FILE);
  	Thread.sleep(10000);
  	}

}