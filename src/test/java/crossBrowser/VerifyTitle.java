package crossBrowser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VerifyTitle {

    WebDriver driver;


    @Test
    @Parameters("browser")
    public void verifyPageTitle(String browserName){

        if(browserName.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
            driver = new FirefoxDriver();

        }else if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();

        }


        driver.manage().window().maximize();



        driver.get("https://google.com");
        System.out.println("driver.getTitle() = " + driver.getTitle());
        driver.quit();
    }


}
