import org.testng.annotations.Test;

public class LoginTestRunner extends Setup {
    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        String actualTitle = loginPage.doLogin(dotenv.get("email"), dotenv.get("password"));
        String expectedTitle = "Dashboard";
        status = expectedTitle.equalsIgnoreCase(actualTitle) ? "passed": "failed";
    }
}
