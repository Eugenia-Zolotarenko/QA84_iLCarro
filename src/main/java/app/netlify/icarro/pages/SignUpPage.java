package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage {
    public SignUpPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(css = "input[name='firstName']")
    WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    WebElement lastNameInput;

    @FindBy(css = "input[name='username']")
    WebElement userEmailInput;

    @FindBy(css = "input[name='password']")
    WebElement passwordInput;

    @FindBy(id = "terms-of-use")
    WebElement termsCheckbox;

    @FindBy(css = "button.btn.btn--primary")
    WebElement submitButton;

    public void clickModalOkButton(By okButtonLocator) {
        WebElement okButton = getWait(10).until(
                ExpectedConditions.elementToBeClickable(okButtonLocator)
        );
        okButton.click();
    }

    public boolean isLogOutButtonPresent() {
        return isElementPresent(By.cssSelector("button[class='navigation-link linklike']"));
    }

    public String newEmail() {
        int i = (int) ((System.currentTimeMillis() / 1000) % 3600);
        String email = "sara" + i + "@gmail.com";
        return email;
    }

    public SignUpPage fillRegisterForm(String firstName, String lastName, String userEmail, String userPass, String checkBox) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(userEmailInput, userEmail);
        type(passwordInput, userPass);
        type(termsCheckbox, checkBox);
        return this;
    }

    public SignUpPage clickSubmitButton() {
        click(submitButton);
        return this;
    }

    public boolean isMessageRegisteredPresent() {
        return isElementPresent(By.xpath("//h3[text()='Registered']"))
                && isElementPresent(By.xpath("//p[text()='You are logged in success']"));
    }


    public LoginPage goRegistrationFormFromLoginPage() {
        LoginPage loginPage = new HomePage(driver).getLoginPage();
        WebElement registrationLink = driver.findElement(By.cssSelector("a.navigator"));
        clickWithJS(registrationLink);

        return loginPage;
    }
}