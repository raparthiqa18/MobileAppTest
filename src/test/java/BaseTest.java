import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
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


        // Configure the options for XCUITEST
        /*
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhone 13 Pro");
        options.setApp("/Users/USER/Downloads/APKFiles/resources/General-Store.apk");
        options.setPlatformVersion("15.5");
        //WDA is the Appium - Web driver Agent -> Middle man for iOS Apps
        options.setWdaLaunchTimeout(Duration.ofSeconds(10));
        driver = new IOSDriver(new URI("http://127.0.0.1:4724").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        */

        // Configure the options for UIAutomator2
        UiAutomator2Options options = new UiAutomator2Options();
//        options.setDeviceName("emulator-5554"); // emulator
//        options.setAutomationName("UIAutomator2");
//        options.setApp("/Users/USER/Downloads/APKFiles/resources/ApiDemos-debug.apk"); // To setup the api DEMOS app
        options.setApp("/Users/USER/Downloads/APKFiles/resources/General-Store.apk");
        options.setChromedriverExecutable("/Users/USER/Downloads/APKFiles/resources/chromedriver.exe");
        // Create an AndroidDriver instance
        driver = new AndroidDriver(new URI("http://127.0.0.1:4724").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    public double getFormattedAmount(String amount){
        return Double.parseDouble(amount.substring(1));
    }

    public void openActivity(String appPackage, String appActivity){

//        Activity activity = new Activity(appPackage, appActivity);
        driver.executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", appPackage + "/" + appActivity
        ));

    }

    public void clickUsingCoord(int x, int y){
        driver.executeScript("mobile: clickGesture", ImmutableMap.of(
                "x", x,
                "y", y
        ));
    }

    public void dragAndDrop(WebElement element, int x, int y){
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", x,
                "endY", y
        ));
    }
    public void swipeAction(WebElement element, String direction){
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75
        ));
    }

    public void longPressAction(WebElement element){
        //https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(), "duration", 2000)
        );
    }

    //If we know the text till where we need to scroll
    public void scrollTo(String text){
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+ text + "\"))"));
    }

    //If we need to scroll to a particular coordinate
    public void scrollToCoordinates(){
           boolean canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 1.0
            ));
    }

    public void scrollToEnd(){
        boolean canScrollMore=false;
        do{
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 1.0
            ));
        }while(canScrollMore);

    }

    public void iOSLongPress(WebElement ele){
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        params.put("duration", 5);
        driver.executeScript("mobile:touchAndHold", params);
    }

    public void iOSScroll(WebElement ele){
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) ele).getId());
        params.put("direction", "down");
        driver.executeScript("mobile:scroll", params);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}
