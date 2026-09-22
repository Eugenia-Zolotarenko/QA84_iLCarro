package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage extends BasePage {

    private final By emailInput =
            By.cssSelector("input[type='email']");

    private final By passwordInput =
            By.cssSelector("input[type='password']");

    private final By submitButton =
            By.cssSelector("button[type='submit']");

    private final By modalTitle =
            By.xpath(
                    "//*[normalize-space(.)='Login failed' " +
                            "or normalize-space(.)='You are logged in success']"
            );

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

        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> {

                    List<WebElement> elements =
                            driver.findElements(modalTitle);

                    for (WebElement element : elements) {
                        if (element.isDisplayed()) {
                            return element;
                        }
                    }

                    return null;
                })
                .getText();
    }


    public LoginPage clickOkButton() {

        WebElement ok = new WebDriverWait(
                driver,
                Duration.ofSeconds(5)
        ).until(driver -> {

            List<WebElement> elements =
                    driver.findElements(okButton);

            for (WebElement element : elements) {

                if (element.isDisplayed()) {
                    return element;
                }
            }

            return null;
        });

        ok.click();

        return this;
    }
}