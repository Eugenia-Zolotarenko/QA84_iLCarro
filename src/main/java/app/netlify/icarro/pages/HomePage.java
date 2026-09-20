package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

    public boolean isYallaButtonPresent(){
        return  isElementPresent(By.cssSelector("button[type='submit']"));
    }

    public boolean isMobileHeaderPresent(){
        return  driver.findElement(By.cssSelector(".mobile-header")).isDisplayed();
    }

    public void setWindowWidthTo(int px) {
        int currentHeight = driver.manage().window().getSize().getHeight();
        driver.manage().window().setSize(new Dimension(px, currentHeight));

    }
}
