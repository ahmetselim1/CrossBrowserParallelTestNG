package crossBrowser;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyTitle {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;


    @Test
    @Parameters("browser")
    public void verifyPageTitle(String browserName) throws InterruptedException {

        if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);


        driver.get("https://weathershopper.pythonanywhere.com/");
        String temperature = driver.findElement(By.id("temperature")).getText();
        System.out.println("temperature = " + temperature);
        temperature = temperature.substring(0, 2);


        try {
            int number = Integer.parseInt(temperature);
            if (number > 34) driver.findElement(By.xpath("//button[contains(text(),'Buy moisturizers')]")).click();
            else if (number < 19) driver.findElement(By.xpath("//button[contains(text(),'Buy sunscreens')]")).click();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(By.xpath("//button[contains(text(),'Add')]"));
        elements.get(2).click();

        driver.findElement(By.xpath("//button[@onclick='goToCart()']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Pay with Card')]")).click();

        WebElement iframeElement = driver.findElement(By.xpath("//iframe[@name='stripe_checkout_app']"));
        driver.switchTo().frame(iframeElement);

        driver.findElement(By.id("email")).sendKeys("omertalha@gmail.com");

        js = (JavascriptExecutor) driver;
        String cardNumber = "4242424242424242";
        String expiryDate = "10/25";
        js.executeScript("arguments[1].value = arguments[0]; ", cardNumber, driver.findElement(By.id("card_number")));
        js.executeScript("arguments[1].value = arguments[0]; ", expiryDate, driver.findElement(By.id("cc-exp")));

        driver.findElement(By.id("cc-csc")).sendKeys("125");
        driver.findElement(By.id("billing-zip")).sendKeys("11111");
        driver.findElement(By.xpath("//span[@class='iconTick']")).click();

//        Thread.sleep(1000);
//        String actualMessage = driver.findElement(By.xpath("//h2")).getText();
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2"))));
//        System.out.println("actualMessage = " + actualMessage);
//        Assert.assertEquals(actualMessage, "PAYMENT SUCCESS");
//        System.out.println("actualMessage = " + actualMessage);
        Thread.sleep(5000); //to see that test successfully landed on the final page.

        Assert.assertEquals(driver.getCurrentUrl(), "https://weathershopper.pythonanywhere.com/confirmation");
        Assert.assertEquals(driver.getTitle(), "Confirmation");

        driver.quit();


    }


}
