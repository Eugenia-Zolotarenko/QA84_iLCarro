package app.netlify.icarro.core;

import app.netlify.icarro.utils.LinkChecker;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    public JavascriptExecutor js;
    public Actions actions;
    @FindBy(tagName = "a")
    List<WebElement> links;
    @FindBy(tagName = "img")
    List<WebElement> images;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }


    public boolean isPageTitleCorrect(String title) {
        pause(1000);
        String actualTitle = driver.findElement(By.tagName("h1")).getText();
        return title.equals(actualTitle);
        //Assert.assertEquals(title, actualTitle, "Page title doesn't match");
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public void scrollWithJS(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollWithJSTopPage() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void clickWithJS(WebElement element) {
        scrollWithJS(element);
        js.executeScript("arguments[0].click();", element);
    }

    public void typeWithJS(WebElement element, String text) {
        scrollWithJS(element);
        type(element, text);
    }

    public void type(WebElement element, String text) {
        if (text != null) {
            //click(element);
            //element.clear();
            //element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            //element.sendKeys(text);
            if (element.getTagName().equals("select")) {
                new Select(element).selectByValue(text);
            } else {
                click(element);
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
                element.sendKeys(text);
            }
        }
    }

    public void click(WebElement element) {
        element.click();
    }

    public boolean isAlertPresent(int time) {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.alertIsPresent());
        if (alert == null) {
            return false;
        } else {
            driver.switchTo().alert().accept();//click OK in alert
            return true;
        }
    }

    public boolean isContainsText(String text, WebElement element) {
        return element.getText().contains(text);
    }

    public WebDriverWait getWait(int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time));
    }

    public boolean isElementVisible(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            e.getMessage();
            return false;
        }
    }

    public void verifyLinks(String url, SoftAssert softly) {
        try {
            URL linkUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) linkUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            int statusCode = connection.getResponseCode();

            // Используем тот ассерт, который нам передали из теста
            softly.assertTrue(statusCode < 400,
                    url + " --> " + connection.getResponseMessage() +
                            " is a BROKEN link (Status: " + statusCode + ")");

        } catch (IOException e) {
            softly.fail(url + " --> ERROR occurred: " + e.getMessage());
        }
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWindowWidthTo(int px) {
        int currentHeight = driver.manage().window().getSize().getHeight();
        driver.manage().window().setSize(new Dimension(px, currentHeight));
    }

    public boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }

    public List<String> getLinkUrls() {
        getWait(10).until(d -> !links.isEmpty());

        return links.stream()
                .map(link -> link.getAttribute("href"))
                .filter(url -> url != null
                        && (url.startsWith("http://") || url.startsWith("https://")))
                .map(url -> url.split("#")[0])
                .distinct()
                .toList();
    }

    public List<String> getBrokenImageUrls() {
        getWait(10).until(d -> !images.isEmpty());

        List<String> broken = new ArrayList<>();

        for (WebElement image : images) {
            scrollWithJS(image);

            try {
                getWait(5).until(d -> (Boolean) js.executeScript(
                        "return arguments[0].complete;", image));
            } catch (TimeoutException ignored) {
            }

            boolean ok = (Boolean) js.executeScript(
                    "return arguments[0].complete && arguments[0].naturalWidth > 0;", image);

            if (!ok) {
                broken.add(image.getAttribute("src"));
            }
        }

        return broken;
    }

    public void verifyBrokenLinks(List<String> urls) {
        Assert.assertFalse(urls.isEmpty(), "No links found on the page");
        List<String> broken = LinkChecker.checkAll(urls);
        Assert.assertTrue(broken.isEmpty(), "Broken links: " + broken);
    }

    public void verifyBrokenImg(List<String> broken) {
        Assert.assertTrue(broken.isEmpty(), "Broken images on results: " + broken);
    }
}


