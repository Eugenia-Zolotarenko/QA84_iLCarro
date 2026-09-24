package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage extends BasePage {

    private final By emailInput =
            By.cssSelector("input[type='email']");

    private final By passwordInput =
            By.cssSelector("input[type='password']");

    private final By submitButton =
            By.cssSelector("button[type='submit']");

    private final By modalWindow =
            By.cssSelector("h3");

    private final By okButton =
            By.xpath("//*[normalize-space(.)='OK']");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public LoginPage fillLoginForm(String email, String password) {

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);

        return this;
    }


    public LoginPage clickSubmitButton() {

        driver.findElement(submitButton).click();

        return this;
    }


    public LoginPage login(String email, String password) {

        fillLoginForm(email, password);
        clickSubmitButton();

        return this;
    }


    public String getModalTitleText() {

        pause(1000);

        return driver.findElement(modalWindow).getText();
    }


    public LoginPage clickOkButton() {

        pause(1000);

        WebElement element =
                driver.findElement(
                        By.xpath("//*[normalize-space(.)='OK']")
                );

        element.click();

        return this;
    }
    public LoginPage goRegistrationFormFromLoginPage() {
        WebElement registrationLink =
                driver.findElement(By.cssSelector("a.navigator"));

        clickWithJS(registrationLink);

        return this;
    }

    public void assertGoToRegForm() {
        Assert.assertTrue(
                isElementPresent(
                        By.xpath("//h1[text()='Registration']")
                )
        );
    }
}