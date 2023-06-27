import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class Setup {
    Dotenv dotenv = Dotenv.load();
    public String lambdatestUsername = dotenv.get("lambdatestUsername");
    public String lambdatestAccesskey= dotenv.get("lambdatestAccesskey");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";


    @BeforeClass
    public void InitDriver() {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("114.0");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", lambdatestUsername);
        ltOptions.put("accessKey", lambdatestAccesskey);
        ltOptions.put("geoLocation", "BD");
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("timezone", "Dhaka");
        ltOptions.put("build", "LambdaTestBuild-1.1.0");
        ltOptions.put("project", "phptravels-login-lambdaTest");
        ltOptions.put("name", "phptravels-successful-login");
        ltOptions.put("console", "true");
        ltOptions.put("w3c", true);
        browserOptions.setCapability("LT:Options", ltOptions);
        try {
            driver = new RemoteWebDriver(new URL("https://" + lambdatestUsername + ":" + lambdatestAccesskey + gridURL), browserOptions);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public void CloseDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

}
