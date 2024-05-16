import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class browserBaseTest {
    public AppiumDriverLocalService service;
    public AndroidDriver driver;

    @BeforeClass
    public void configureAppium() throws URISyntaxException, MalformedURLException {
        // To start the Appium server automatically, instead of triggering it manually
        service = new AppiumServiceBuilder()
                .usingPort(4724)
                .withIPAddress("127.0.0.1")
                .withAppiumJS(new File("/Users/USER/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
                .build();
        service.start();
        // Configure the options for UIAutomator2
        UiAutomator2Options options = new UiAutomator2Options();
        options.setChromedriverExecutable("/Users/USER/Downloads/APKFiles/resources/chromedriver.exe");
        options.setCapability("browserName", "Chrome");
        // Create an AndroidDriver instance
        driver = new AndroidDriver(new URI("http://127.0.0.1:4724").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}


