import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerce_tc_1 extends BaseTest{

    public void fillForm() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Samantha");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//android.widget.RadioButton[@text = 'Female']")).click();
        driver.findElement(By.id("android:id/text1")).click();
        scrollTo("Argentina");
        driver.findElement(By.xpath("//android.widget.TextView[@text = 'Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click(); //1st method
//        driver.findElement(By.xpath("(android.widget.TextView[@text='ADD TO CART)[1]")).click();//2nd method
        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text","Cart" ));
        List<WebElement> prices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int productPrice_Count = prices.size();
        double sum =0;
        for(int j=0; j<productPrice_Count; j++){
            String pricestr = prices.get(j).getText();
            double price = getFormattedAmount(pricestr);
            sum = sum + price;
        }
        double total_price= getFormattedAmount(driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText());
        System.out.println("Actual price: " + sum + "; Expected price: " + total_price);
        Assert.assertEquals(sum, total_price);
        longPressAction(driver.findElement(By.id("com.androidsample.generalstore:id/termsButton")));
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle")).getText(), "Terms Of Conditions");
        driver.findElement(By.id("android:id/button1")).click();


    }

//    @Test(dependsOnMethods="fillForm")
    public void hybrid() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(2000);
        Set<String> contexts = driver.getContextHandles();
        for(String contextName : contexts){
            System.out.println("Context Name: " + contextName);
        }

        Thread.sleep(4000);
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("Apple iphone15");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        System.out.println("Before switching context: " + driver.getContext());

        try {
            driver.context("NATIVE_APP");
            System.out.println("Switched to NATIVE_APP context successfully");
        } catch (NoSuchContextException e) {
            System.out.println("Failed to switch to NATIVE_APP context: " + e.getMessage());
            // Handle the exception or take appropriate action
        }

        System.out.println("After switching context: " + driver.getContext());
        Set<String> contextHandles = driver.getContextHandles();
        if (contextHandles.contains("NATIVE_APP")) {
            System.out.println("Switched to NATIVE_APP context successfully");
        } else {
            System.out.println("Failed to switch to NATIVE_APP context");
        }
        Thread.sleep(2000);
        System.out.println("Check if successfully navigated to native app: " + driver.findElements(By.id("com.androidsample.generalstore:id/nameField")).size());
    }


    public void jordan6rings() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Samantha");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//android.widget.RadioButton[@text = 'Female']")).click();
        driver.findElement(By.id("android:id/text1")).click();
        scrollTo("Argentina");
        driver.findElement(By.xpath("//android.widget.TextView[@text = 'Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        scrollTo("Jordan 6 Rings");
        int product_count = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for(int i =0; i<product_count; i++){
            String prod_name=driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if (prod_name.equalsIgnoreCase("Jordan 6 Rings")){
                driver.findElement(By.id("com.androidsample.generalstore:id/productAddCart")).click();
                break;
            }
        }
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        Thread.sleep(20000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text","Cart" ));
        String lastPageProduct = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(lastPageProduct, "Jordan 6 Rings");
    }

    public void toatsMsg() throws InterruptedException {

        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        System.out.println("Toast message: " + driver.findElement(By.xpath("//android.widget.Toast")).getText());
        System.out.println("Toast message using getAttribute: " + driver.findElement(By.xpath("//android.widget.Toast")).getAttribute("name"));
        String toastmsg = driver.findElement(By.xpath("//android.widget.Toast")).getAttribute("name");
        Assert.assertEquals(toastmsg, "Please enter your name");
    }


    public void fillloginForm() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Samantha");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//android.widget.RadioButton[@text = 'Female']")).click();
        driver.findElement(By.id("android:id/text1")).click();
        scrollTo("Argentina");
        driver.findElement(By.xpath("//android.widget.TextView[@text = 'Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(30000);
    }

}
