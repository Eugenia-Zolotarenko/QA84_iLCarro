package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.IAttributes;
import org.openqa.selenium.WebElement;


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
        WebElement okButton = getWait(3).until(
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
        return "sara" + System.currentTimeMillis() + "@gmail.com";
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
        try {
            return getWait(10).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(selector)
                    )
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void acceptTerms() {
        if (!termsCheckbox.isSelected()) {
            click(termsCheckbox);
        }
    }


    public SignUpPage fillFirstName(String firstName) {
        getWait(2).until(ExpectedConditions.visibilityOf(firstNameInput));
        if (firstName != null && !firstName.isEmpty()) {
            type(firstNameInput, firstName);
            js.executeScript("arguments[0].value = arguments[1];", firstNameInput, firstName);
            pause(100);}
        firstNameInput.sendKeys(Keys.TAB);
        return this;

    }

    public SignUpPage fillLastName(String lastName) {
        lastNameInput.click();
        if (lastName != null && !lastName.isEmpty()) {
            type(lastNameInput, lastName);
        }
        lastNameInput.sendKeys(Keys.TAB);
        return this;
    }

    public SignUpPage fillEmail(String email) {
        userEmailInput.click();
        if (email != null && !email.isEmpty()) {
            type(userEmailInput, email);
        }
        userEmailInput.sendKeys(Keys.TAB);
        return this;
    }

    public SignUpPage fillPassword(String password) {
        passwordInput.click();
        if (password != null && !password.isEmpty()) {
            type(passwordInput, password);
        }
        passwordInput.sendKeys(Keys.TAB);
        return this;
    }

    public boolean isSubmitButtonEnabled() {
        getWait(3).until(ExpectedConditions.visibilityOf(submitButton));
        // 1. Get the value of the "disabled" attribute
        String disabledAttr = submitButton.getAttribute("disabled");
        // In HTML, disabled="" or disabled="true" means that the button is DISABLED.
        // If the attribute is null, the button is ACTIVE.
        boolean hasDisabledAttribute = (disabledAttr != null);
        // 2. We return true only if isEnabled() = true and the disabled attribute is not present
        return submitButton.isEnabled() && !hasDisabledAttribute;
    }

    public boolean isRegistrationFailedModalDisplayed() {
        return isElementPresent(By.xpath("//h3[text()='Registration failed']"));
    }


    public WebElement getSubmitButton() {
        return submitButton;
    }
}
