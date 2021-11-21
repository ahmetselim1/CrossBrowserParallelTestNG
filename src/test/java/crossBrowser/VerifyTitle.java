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
    public void verifyPageTitle(String browserName) {

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
        temperature = temperature.substring(0, 1);


        try {
            int number = Integer.parseInt(temperature);
            System.out.println(number);
            if (number > 20) {
                driver.findElement(By.xpath("//button[contains(text(),'Buy moisturizers')]")).click();
            } else if (number < 20) {
                driver.findElement(By.xpath("//button[contains(text(),'Buy sunscreens')]")).click();
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(By.xpath("//button[contains(text(),'Add')]"));
        elements.get(0).click();
        driver.findElement(By.xpath("//button[@onclick='goToCart()']")).click();


        driver.quit();
    }


}
