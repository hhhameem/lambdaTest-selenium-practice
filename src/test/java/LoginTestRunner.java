import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestRunner extends Setup {
    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        String actualTitle = loginPage.doSuccessfulLogin(dotenv.get("email"), dotenv.get("password"));
        String expectedTitle = "Dashboard";
        Assert.assertEquals(actualTitle, expectedTitle);
    }
}
