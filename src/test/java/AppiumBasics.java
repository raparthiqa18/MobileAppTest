import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AppiumBasics extends BaseTest{


    public void startAppActivity(){
        try{
            openActivity("io.appium.android.apis", "io.appium.android.apis.preference.PreferenceDependencies");
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void keys(){
        try{
            driver.findElement(AppiumBy.accessibilityId("App")).click();
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            driver.pressKey(new KeyEvent(AndroidKey.HOME));
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public void alertMessages(){
        try{
            driver.findElement(AppiumBy.accessibilityId("App")).click();
            driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Alert Dialogs\"]")).click();
            driver.findElement(By.id("io.appium.android.apis:id/two_buttons2ultra")).click();
            Assert.assertEquals(driver.findElement(By.id("android:id/alertTitle")).getText(), "Header title");
            Thread.sleep(2000);
            driver.findElement(By.id("android:id/button1")).click();
            driver.findElement(AppiumBy.accessibilityId("List dialog")).click();
            Thread.sleep(2000);
            Assert.assertEquals(driver.findElements(AppiumBy.className("android.widget.TextView")).size(), 5);
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.TextView[@text='Command one']")).click();
            Thread.sleep(2000);
//            driver.findElement(By.id("io.appium.android.apis:id/screen")).click();
            ;
            clickUsingCoord(796, 1767);
            driver.findElement(AppiumBy.accessibilityId("Single choice list")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Satellite']")).click();
            driver.findElement(By.id("android:id/button1")).click();
            driver.findElement(AppiumBy.accessibilityId("Repeat alarm")).click();
            Assert.assertEquals(driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Every Tuesday']")).getAttribute("checked"), "true");
            driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Every Wednesday']")).click();
            Assert.assertEquals(driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Every Wednesday']")).getAttribute("checked"), "true");
            driver.findElement(By.id("android:id/button1")).click();
            Thread.sleep(2000);
            driver.findElement(AppiumBy.accessibilityId("Text Entry dialog")).click();
            driver.findElement(By.id("io.appium.android.apis:id/username_edit")).sendKeys("Test");
            driver.setClipboardText("Rakesh");//copy in this step and paste in next step
            driver.findElement(By.id("io.appium.android.apis:id/password_edit")).sendKeys(driver.getClipboardText());
            driver.findElement(By.id("android:id/button1")).click();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void portraitToLandscape() {
        try{
            driver.findElement(AppiumBy.accessibilityId("Views")).click();
            DeviceRotation landscape = new DeviceRotation(0,0,90);
            driver.rotate(landscape);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DragDropDemoTest() {
        try{
            driver.findElement(AppiumBy.accessibilityId("Views")).click();
            driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
            WebElement dragElement = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
            dragAndDrop(dragElement, 620, 544);
            Assert.assertEquals(driver.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText(), "Dropped!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SwipeDemoTest() {
        try{
            driver.findElement(AppiumBy.accessibilityId("Views")).click();
            driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
            driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"1. Photos\"]")).click();
            Assert.assertEquals(driver.findElement(By.xpath("(//android.widget.ImageView)[1]")).getAttribute("focusable"), "true");
            WebElement firstImage = driver.findElement(By.xpath("(//android.widget.ImageView)[1]"));
            swipeAction(firstImage, "left");
            Thread.sleep(2000);
            Assert.assertEquals(driver.findElement(By.xpath("(//android.widget.ImageView)[1]")).getAttribute("focusable"), "false");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ScrollDemoTest() {
        try{
            driver.findElement(AppiumBy.accessibilityId("Views")).click();
            //driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"))"));
            //scrollTo("WebView");
            scrollToCoordinates();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void longPressPeople() {
        try{
            driver.findElement(AppiumBy.accessibilityId("Views")).click();
            driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
            driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"1. Custom Adapter\"]")).click();
            WebElement element = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));

            longPressAction(element);

            String menuText =  driver.findElement(By.id("android:id/title")).getText();
            Assert.assertEquals(menuText, "Sample menu");
            Assert.assertTrue(driver.findElement(By.id("android:id/title")).isDisplayed());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void wifiSettings() {
        try{

            // By.id, By.xpath, By.name
            //AppiumBy.accessibilityId, AppiumBy.classname, AppiumBy.androidUIAutomator
            driver.findElement(AppiumBy.accessibilityId("Preference")).click();
            // xpath syntax: //tagname[@attribute:'value']
            driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"3. Preference dependencies\"]")).click();
            driver.findElement(By.id("android:id/checkbox")).click();
            driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click(); // "(//tagname)[index]"
            String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
            Assert.assertEquals(alertTitle, "WiFi settings");
            driver.findElement(By.id("android:id/edit")).sendKeys("Rakesh");
            //driver.findElement(By.id("android:id/button1")).click();
            driver.findElements(By.className("android.widget.Button")).get(1).click();



        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
