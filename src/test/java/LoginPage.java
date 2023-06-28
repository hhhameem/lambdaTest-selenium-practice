import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    RemoteWebDriver driver;
    public LoginPage (RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "email")
    WebElement inputEmail;
    @FindBy(id = "password")
    WebElement inputPassword;
    @FindBy(id = "submit")
    WebElement btnlogin;

    public String doLogin(String email, String password) throws InterruptedException {
        driver.get("https://phptravels.net/admin/login.php");
        inputEmail.sendKeys(email);
        Thread.sleep(1000);
        inputPassword.sendKeys(password);
        Thread.sleep(1000);
        btnlogin.click();
        return driver.getTitle();
    }
}
