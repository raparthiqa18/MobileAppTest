import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class mobileBrowserTests extends browserBaseTest{
    public void browserTest(){
        driver.get("http://google.com");
        System.out.println("Title of the page: " + driver.getTitle());
        driver.findElement(By.name("q")).sendKeys("Apple iphone15");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Title of the page: " + driver.getTitle());
    }

    @Test
    public void findProduct() throws InterruptedException {
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector("body > app-root > nav > button > span")).click();
        driver.findElement(By.cssSelector("a[routerlink*='products']")).click();
        Thread.sleep(2000);
        System.out.println("Selenium found : " + driver.findElement(By.cssSelector("a[href*='products/1']")).getText());

        //scroll
        driver.executeScript("window.scrollBy(0,500)");
        Thread.sleep(2000);
        System.out.println("Appium found : " + driver.findElement(By.cssSelector("a[href*='products/2']")).getText());
        System.out.println("Devops found : " + driver.findElement(By.cssSelector("a[href*='products/3']")).getText());



    }

}
