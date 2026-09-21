package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver)
    {
        super(driver);
    }
    public void enterCity(String city) {
        driver.findElement(By.id("city")).sendKeys(city);
    }
}
