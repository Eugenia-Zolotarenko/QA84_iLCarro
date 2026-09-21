package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class LetCarWorkPage extends BasePage {
    private final By submit = By.cssSelector("button[type='submit']");

    public LetCarWorkPage(WebDriver driver) {
        super(driver);
    }

    public LetCarWorkPage fillValidForm() {
        WebElement city = getWait(10).until(ExpectedConditions.elementToBeClickable(By.id("city")));
        type(city, "Berlin");
        city.sendKeys(Keys.TAB);
        return fillField("manufacture", "Toyota")
                .fillField("model", "Corolla")
                .fillField("year", "2020")
                .fillField("fuel", "petrol")
                .fillField("seats", "5")
                .fillField("carClass", "Economy")
                .fillField("serialNumber", "QA12345")
                .fillField("pricePerDay", "50");
    }

    public LetCarWorkPage fillField(String name, String value) {
        WebElement field = getWait(10).until(ExpectedConditions.elementToBeClickable(By.name(name)));
        scrollWithJS(field);
        if (field.getTagName().equals("select")) {
            new Select(field).selectByValue(value);
        } else {
            field.click();
            field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            if (!value.isEmpty()) {
                field.sendKeys(value);
            }
        }
        field.sendKeys(Keys.TAB);
        return this;
    }

    public String getFieldError(String name) {
        return driver.findElement(By.name(name)).findElement(By.xpath(".."))
                .findElements(By.cssSelector(".error")).stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .findFirst().orElse("");
    }

    public boolean isSubmitEnabled() {
        return driver.findElement(submit).isEnabled();
    }

    public String getPageTitle() {
        return getWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
    }
}
