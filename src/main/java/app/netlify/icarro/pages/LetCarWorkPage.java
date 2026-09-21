package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

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

    public LetCarWorkPage verifyRequired(String name, boolean expected) {
        By error = By.xpath("//*[@name='" + name + "']/../div[@class='error']");
        if (expected) {
            WebElement message = getWait(10).until(ExpectedConditions.visibilityOfElementLocated(error));
            Assert.assertEquals(message.getText(), "Required", name + " validation message");
        } else {
            getWait(10).withMessage(name + " still shows Required")
                    .until(d -> !d.findElement(By.name(name)).findElement(By.xpath("..")).getText().contains("Required"));
        }
        return this;
    }

    public LetCarWorkPage verifySubmitEnabled(boolean expected) {
        getWait(10).withMessage("Submit enabled should be " + expected)
                .until(d -> d.findElement(submit).isEnabled() == expected);
        return this;
    }
}
