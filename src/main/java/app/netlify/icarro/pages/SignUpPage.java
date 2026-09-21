package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPage extends BasePage {
    public SignUpPage(WebDriver driver) {
        super(driver);
    }
    public SignUpPage getSignUpPage() {
        WebElement link = driver.findElement(By.cssSelector("h1[class='title']"));
        clickWithJS(link);
        return new SignUpPage(driver);
    }
}
