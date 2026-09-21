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
    public SignUpPage getSignUpPage() {
        WebElement link = driver.findElement(By.cssSelector("h1[class='title']"));
        clickWithJS(link);
        return new SignUpPage(driver);
    }
    @FindBy(css = "input[name='firstName']")
    WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    WebElement lastNameInput;

    @FindBy(css = "input[name='username']")
    WebElement usernameInput;

    @FindBy(css = "input[name='password']")
    WebElement passwordInput;

    @FindBy(id = "terms-of-use")
    WebElement termsCheckbox;

    @FindBy(css = "button.btn.btn--primary")
    WebElement submitButton;

    public void fillFirstName(String firstName) {
        fillField(firstNameInput, firstName);
    }

    public void fillLastName(String lastName) {
        fillField(lastNameInput, lastName);
    }

    public void fillUsername(String username) {
        fillField(usernameInput, username);
    }

    public void fillPassword(String password) {
        fillField(passwordInput, password);
    }

    public void acceptTerms() {
        click(termsCheckbox);
    }

    public void clickSubmit() {
        click(submitButton);
    }

    private void fillField(WebElement element, String text) {
        getWait(10).until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public void ModalWinOk(By okButtonLocator) {
        WebElement okButton = getWait(10).until(
                ExpectedConditions.elementToBeClickable(okButtonLocator)
        );
        okButton.click();
    }


    public boolean isLogOutButtonPresent() {
        return isElementPresent(By.cssSelector("button[class='navigation-link linklike']"));
    }

}
