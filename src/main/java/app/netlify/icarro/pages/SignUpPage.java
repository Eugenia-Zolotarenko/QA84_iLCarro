package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import app.netlify.icarro.model.NewUserSignUp;
import app.netlify.icarro.model.SignUpField;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[name='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastNameInput;

    @FindBy(css = "input[name='username']")
    private WebElement userEmailInput;

    @FindBy(css = "input[name='password']")
    private WebElement passwordInput;

    @FindBy(id = "terms-of-use")
    private WebElement termsCheckbox;

    @FindBy(css = "button.btn.btn--primary")
    private WebElement submitButton;

    @FindBy(xpath = "(//button[normalize-space()='Log out'])[1]")
    private WebElement logOutButton;

    // ---------- Filling Out the Form ----------

    public SignUpPage fillRegisterForm(String firstName, String lastName,
                                       String userEmail, String userPass) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(userEmailInput, userEmail);
        type(passwordInput, userPass);
        return this;
    }

    public SignUpPage fillRegisterForm(NewUserSignUp user) {
        return fillRegisterForm(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());
    }

    public SignUpPage acceptTerms() {
        if (!termsCheckbox.isSelected()) {
            click(termsCheckbox);
        }
        return this;
    }

    public SignUpPage clickSubmitButton() {
        click(submitButton);
        return this;
    }

    // ---------- Inspections ----------

    public boolean isSubmitButtonDisabled() {
        try {
            getWait(3).until(d -> !submitButton.isEnabled());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed(SignUpField field) {
        try {
            getWait(5).until(ExpectedConditions.visibilityOfElementLocated(errorLocator(field)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

//    public boolean isErrorMessageAbsent(SignUpField field) {
//        return getWait(1).until(ExpectedConditions.invisibilityOfElementLocated(errorLocator(field)));
//    }

    public boolean isMessageRegisteredPresent(String modalTitle, String modalMessage) {
        return isElementPresent(By.xpath("//h3[text()='" + modalTitle + "']"))
                && isElementPresent(By.xpath("//p[text()='" + modalMessage + "']"));
    }

//    public boolean isRegistrationFailedModalDisplayed() {
//        return isElementPresent(By.xpath("//h3[text()='Registration failed']"));
//    }

    public boolean isLogOutButtonPresent() {
        return isElementPresent(By.cssSelector("button.navigation-link.linklike"));
    }

    // ---------- Modal window ----------

    public void clickModalWindowOkButton(By okButtonLocator) {
        WebElement okButton = getWait(3).until(
                ExpectedConditions.elementToBeClickable(okButtonLocator));
        okButton.click();
    }

    public void clickModalWindowOkButton() {
        clickModalWindowOkButton(By.xpath("//button[.='OK']"));
    }

    // ---------- Supporting ----------

    private By errorLocator(SignUpField field) {
        return By.cssSelector(
                "div.input-container:has(input[name='" + field.getInputName() + "']) > div.error");
    }


    public void clickLogOutButton() {
        click(logOutButton);
    }

    public boolean isErrorMessagePresent(String modalTitle) {
        return isElementPresent(By.xpath("(//p[normalize-space()='\"User already exists\"'])[1]"));
    }
}