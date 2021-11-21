package crossBrowser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        Thread.sleep(2000);
        driver.findElement(By.id("email")).sendKeys("omertalha@gmail.com");
        driver.findElement(By.id("card_number")).sendKeys("4242424242424242");
        driver.findElement(By.id("cc-exp")).sendKeys("10/25");
        driver.findElement(By.id("cc-csc")).sendKeys("125");
        driver.findElement(By.xpath("//span[@class='iconTick']")).click();
        driver.quit();
    }


}
