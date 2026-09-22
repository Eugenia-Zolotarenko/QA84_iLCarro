package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomeComponentPresent(){
      return  isElementPresent(By.cssSelector(".search-card"));
    }

    public LetCarWorkPage getLetCarWorkPage() {
        WebElement link = driver.findElement(By.cssSelector("a[href$='/let-car-work']"));
        clickWithJS(link);
        return new LetCarWorkPage(driver);
    }

    public LoginPage getLoginPage() {
        WebElement link = driver.findElement(By.cssSelector("a[href$='/login']"));
        clickWithJS(link);
        return new LoginPage(driver);
    }

    public HeaderPage getHeaderPage() {
        WebElement logo = driver.findElement(By.cssSelector("a.logo"));
        clickWithJS(logo);
        return new HeaderPage(driver);
    }

    public FooterPage getFooterPage() {
        WebElement logo = driver.findElement(By.cssSelector("a.logo"));
        clickWithJS(logo);
        return new FooterPage(driver);
    }

    public boolean isYallaButtonPresent(){
        return  isElementPresent(By.cssSelector("button[type='submit']"));
    }

    public boolean isMobileHeaderPresent(){
        return  driver.findElement(By.cssSelector(".mobile-header")).isDisplayed();
    }


}
