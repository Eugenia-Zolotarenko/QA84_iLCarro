package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.openqa.selenium.Keys;

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

    public void clickModalWindowOkButton(By okButtonLocator) {
        WebElement okButton = getWait(10).until(
                ExpectedConditions.elementToBeClickable(okButtonLocator)
        );
        okButton.click();
    }
    public void clickModalWindowOkButton() {
        clickModalWindowOkButton(By.xpath("//button[.='OK']"));
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

    public boolean isMessageRegisteredPresent(String modalTitle, String modalMessage) {
        return isElementPresent(By.xpath("//h3[text()='" + modalTitle + "']"))
                && isElementPresent(By.xpath("//p[text()='" + modalMessage + "']"));
    }





    public boolean isErrorMessageDisplayed(String fieldName) {
        String selector =
                "div.input-container:has(input[name='" + fieldName + "']) > div.error";
        return getWait(5).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(selector))
        ).isDisplayed();
    }


    public void acceptTerms() {
        click(termsCheckbox);
    }



    public SignUpPage fillFirstName(String firstName) {
        firstNameInput.click();
        if (firstName != null && !firstName.isEmpty()) {
            firstNameInput.sendKeys(firstName);
        }
        firstNameInput.sendKeys(Keys.TAB);
        return this;
    }

    public SignUpPage fillLastName(String lastName) {
        lastNameInput.click();
        if (lastName != null && !lastName.isEmpty()) {
            lastNameInput.sendKeys(lastName);
        }
        lastNameInput.sendKeys(Keys.TAB);
        return this;
    }

    public SignUpPage fillEmail(String email) {
        userEmailInput.click();
        if (email != null && !email.isEmpty()) {
            userEmailInput.sendKeys(email);
        }
        userEmailInput.sendKeys(Keys.TAB);
        return this;
    }

    public SignUpPage fillPassword(String password) {
        passwordInput.click();
        if (password != null && !password.isEmpty()) {
            passwordInput.sendKeys(password);
        }
        passwordInput.sendKeys(Keys.TAB);
        return this;
    }

    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled();
    }

    public boolean isRegistrationFailedModalDisplayed() {
        return isElementPresent(By.xpath("//h3[text()='Registration failed']"));
    }


}
