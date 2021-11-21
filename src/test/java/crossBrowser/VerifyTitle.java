package crossBrowser;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class VerifyTitle {

    WebDriver driver;


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
        Thread.sleep(3000);

        WebElement iframeElement = driver.findElement(By.xpath("//iframe[@name='stripe_checkout_app']"));
        driver.switchTo().frame(iframeElement);

        driver.findElement(By.id("email")).sendKeys("omertalha@gmail.com");
        Thread.sleep(2000);


        String cardNum= "4242424242424242";
        WebElement inputField= driver.findElement(By.id("card_number"));

        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[1].value = arguments[0]; ", cardNum, inputField);

        Thread.sleep(2000);

        driver.findElement(By.id("cc-exp")).sendKeys("1125");
        Thread.sleep(2000);
        driver.findElement(By.id("cc-csc")).sendKeys("125");
        Thread.sleep(20000);

        driver.findElement(By.xpath("//span[@class='iconTick']")).click();
        driver.quit();
    }


}
