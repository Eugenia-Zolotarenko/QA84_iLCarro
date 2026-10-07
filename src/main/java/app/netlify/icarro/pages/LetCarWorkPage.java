package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import app.netlify.icarro.model.Car;

import java.util.List;

public class LetCarWorkPage extends BasePage {
    public static final String REQUIRED_ERROR = "Required";
    public static final String SEATS_MIN_ERROR = "seats must be greater than or equal to 1";
    public static final String PRICE_MIN_ERROR = "pricePerDay must be greater than or equal to 1";
    private final By submit = By.cssSelector("button[type='submit']");

    public LetCarWorkPage(WebDriver driver) {
        super(driver);
    }

    public LetCarWorkPage fillValidForm() {
        WebElement city = getWait(10).until(ExpectedConditions.elementToBeClickable(By.id("city")));
        type(city, "Berlin");
        return fillField("manufacture", "Toyota")
                .fillField("model", "Corolla")
                .fillField("year", "2020")
                .fillField("fuel", "petrol")
                .fillField("seats", "5")
                .fillField("carClass", "Economy")
                .fillField("serialNumber", "QA12345")
                .fillField("pricePerDay", "50");
    }

    public LetCarWorkPage fillForm(Car car) {
        WebElement city = getWait(10).until(ExpectedConditions.elementToBeClickable(By.id("city")));
        type(city, car.city);
        return fillField("manufacture", car.manufacture)
                .fillField("model", car.model)
                .fillField("year", car.year)
                .fillField("fuel", car.fuel)
                .fillField("seats", car.seats)
                .fillField("carClass", car.carClass)
                .fillField("serialNumber", car.serialNumber)
                .fillField("pricePerDay", car.pricePerDay);
    }

    public LetCarWorkPage fillField(String name, String value) {
        WebElement field = getWait(10).until(ExpectedConditions.elementToBeClickable(By.name(name)));
        typeWithJS(field, value);
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

    public boolean hasField(String name) {
        return isElementPresent(By.name(name));
    }

    public boolean hasCityField() {
        return isElementPresent(By.id("city"));
    }

    public boolean hasPhotoFileInput() {
        return isElementPresent(By.cssSelector("input[type='file']"));
    }

    public String getFieldType(String name) {
        return driver.findElement(By.name(name)).getAttribute("type");
    }

    public List<String> getOptions(String name) {
        return new Select(driver.findElement(By.name(name))).getOptions().stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getSelectedOption(String name) {
        return new Select(driver.findElement(By.name(name))).getFirstSelectedOption().getText();
    }

    public LetCarWorkPage selectByText(String name, String text) {
        new Select(driver.findElement(By.name(name))).selectByVisibleText(text);
        return this;
    }

    public boolean isSubmitEnabled() {
        return driver.findElement(submit).isEnabled();
    }

    public String getPageTitle() {
        return getWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
    }
}
