package app.netlify.icarro.core;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import static java.awt.SystemColor.window;

public abstract class BasePage {
    protected WebDriver driver;
    public JavascriptExecutor js;
    public Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        js =(JavascriptExecutor) driver;
        actions = new Actions(driver);
    }


    public void isPageTitleCorrect(String title) {
        String actualTitle = driver.findElement(By.tagName("h1")).getAttribute("innerText");
        //String actualTitle = driver.findElement(By.tagName("h1")).getText();
        pause(1000);
        Assert.assertTrue(actualTitle.equals(title), "Page title doesn't match");
    }

    public boolean isElementPresent(By locator){
        return !driver.findElements(locator).isEmpty();
    }

    public void scrollWithJS(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollWithJSTopPage(){
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void clickWithJS(WebElement element){
        scrollWithJS(element);
        js.executeScript("arguments[0].click();", element);
    }

    public void typeWithJS(WebElement element, String text){
        scrollWithJS(element);
        type(element, text);
    }
    public void type(WebElement element, String text){
        if(text!=null){
            click(element);
            element.clear();
            element.sendKeys(text);
        }
    }

    public void  click(WebElement element){
        element.click();
    }
    public boolean isAlertPresent(int time){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.alertIsPresent());
        if (alert==null){
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
//    protected void verifyLinks(String url) {
//        try {
//            URL linkUrl = new URL(url);
//            HttpURLConnection connection =(HttpURLConnection) linkUrl
//                    .openConnection();
//            connection.setConnectTimeout(5000);
//            connection.connect();
//            int statusCode = connection.getResponseCode();
//
//            if(statusCode >= 400) {
//                softly.fail(url + " --> " + connection.getResponseMessage() + " is a BROKEN link");
//            } else
//                softly.assertThat(statusCode).isLessThan(400);
//        } catch (IOException e) {
//            softly.fail(url + " --> " + "ERROR occurred");
//        }
//    }

public void pause(int millis){
    try {
        Thread.sleep(millis);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
}


