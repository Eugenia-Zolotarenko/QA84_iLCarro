package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import app.netlify.icarro.model.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
            By.cssSelector(
                    "div[role='dialog'] div.modal-content a.btn.btn--primary"
            );

    private final By registrationLink =
            By.cssSelector("a.navigator");


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


    public LoginPage fillLoginFormWithData(LoginData data) {

        return fillLoginForm(
                data.getLogin(),
                data.getPassword()
        );
    }


    public LoginPage clickSubmitButton() {

        driver.findElement(submitButton).click();

        return this;
    }


    public LoginPage login(LoginData data) {

        fillLoginFormWithData(data);
        clickSubmitButton();

        return this;
    }


    public String getModalTitleText() {

        pause(1000);

        return driver
                .findElement(modalWindow)
                .getText();
    }


    public LoginPage clickOkButton() {

        pause(1000);

        WebElement ok =
                driver.findElement(okButton);

        clickWithJS(ok);

        return this;
    }


    public LoginPage goRegistrationFormFromLoginPage() {

        WebElement link =
                driver.findElement(registrationLink);

        clickWithJS(link);

        return this;
    }


    public boolean isSubmitEnable() {

        return driver
                .findElement(submitButton)
                .isEnabled();
    }
}