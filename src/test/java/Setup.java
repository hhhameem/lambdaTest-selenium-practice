import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Setup {
    Dotenv dotenv = Dotenv.load();
    public String lambdatestUsername = dotenv.get("lambdatestUsername");
    public String lambdatestAccesskey= dotenv.get("lambdatestAccesskey");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    public String status = "";


    @BeforeTest
    @Parameters(value={"browser","version","platform"})
    public void InitDriver(ITestContext testContext, String browser, String version, String platform) {

        MutableCapabilities browserOptions;

        if (browser.equalsIgnoreCase("chrome")) {
            browserOptions = new ChromeOptions();
        } else if (browser.equalsIgnoreCase("firefox")) {
            browserOptions = new FirefoxOptions();
        } else if (browser.equalsIgnoreCase("edge")) {
            browserOptions = new EdgeOptions();
        } else {
            System.out.println("Invalid browser specified");
            return;
        }

        browserOptions.setCapability("browserVersion", version);
        browserOptions.setCapability("platformName", platform);

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", lambdatestUsername);
        ltOptions.put("accessKey", lambdatestAccesskey);
        ltOptions.put("geoLocation", "BD");
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("timezone", "Dhaka");
        ltOptions.put("build", "LambdaTestBuild-1.1.0");
        ltOptions.put("project", "phptravels-login-lambdaTest");
        ltOptions.put("name", testContext.getCurrentXmlTest().getName());
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

    @AfterMethod
    public void setStatus() {
        driver.executeScript("lambda-status=" + status);
    }

    @AfterTest
    public void CloseDriver() {
        if (driver != null) {
            System.out.println("quiting driver........");
            driver.quit();
        }
    }

}
