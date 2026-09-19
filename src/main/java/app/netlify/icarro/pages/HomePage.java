package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void isHomeComponentPresent(){
        isElementPresent(By.cssSelector(".search-card"));
    }

    public LetCarWorkPage getLetCarWorkPage() {
        WebElement link = driver.findElement(By.cssSelector("a[href$='/let-car-work']"));
        clickWithJS(link);
        return new LetCarWorkPage(driver);
    }

    public boolean isYallaButtonPresent(){
        return  isElementPresent(By.cssSelector("button[type='submit']"));
    }
}
