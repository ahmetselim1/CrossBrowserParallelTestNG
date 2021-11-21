package crossBrowser;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        wait = new WebDriverWait(driver, 10);



        driver.get("https://weathershopper.pythonanywhere.com/");
        System.out.println("driver.getTitle() = " + driver.getTitle());
        String temperature = driver.findElement(By.id("temperature")).getText();
        System.out.println("temperature = " + temperature);
        temperature = temperature.substring(0, 2);


        try {
            int number = Integer.parseInt(temperature);
            System.out.println(number);
            if (number > 34) {
                driver.findElement(By.xpath("//button[contains(text(),'Buy moisturizers')]")).click();
            } else if (number < 19) {
                driver.findElement(By.xpath("//button[contains(text(),'Buy sunscreens')]")).click();
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(By.xpath("//button[contains(text(),'Add')]"));
        elements.get(0).click();
        driver.findElement(By.xpath("//button[@onclick='goToCart()']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Pay with Card')]")).click();

        WebElement iframeElement = driver.findElement(By.xpath("//iframe[@name='stripe_checkout_app']"));
        driver.switchTo().frame(iframeElement);

        driver.findElement(By.id("email")).sendKeys("omertalha@gmail.com");


        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[1].value = arguments[0]; ", "4242424242424242", driver.findElement(By.id("card_number")));
        js.executeScript("arguments[1].value = arguments[0]; ", "10/25", driver.findElement(By.id("cc-exp")));

        driver.findElement(By.id("cc-csc")).sendKeys("125");
        driver.findElement(By.id("billing-zip")).sendKeys("11111");
        driver.findElement(By.xpath("//span[@class='iconTick']")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2"))));



        Assert.assertEquals(driver.findElement(By.xpath("//h2")).getText(), "PAYMENT SUCCESS");




        driver.quit();

    }


}
