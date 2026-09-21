package app.netlify.icarro.core;

import app.netlify.icarro.utils.Screenshots;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

public class TestBase {
    protected static ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName()));
    Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected SoftAssert softAssert;
    protected WebDriver driver;
    protected Screenshots screen;

    @BeforeSuite
    public void  setUp(){
        driver = app.init();
    }

    @AfterSuite(enabled = true)
    public void tearDown(){
        app.stop();
    }

    @BeforeMethod
    public void startTest(Method method, Object[] p){
        softAssert = new SoftAssert();
        screen = new Screenshots();
        logger.info("start test {} with data:\n\t {}", method.getName(), Arrays.asList(p));
    }

    @AfterMethod
    public void stopTest(ITestResult result){
        if (result.getStatus()==ITestResult.SUCCESS){
            logger.info("Test PASSED");
        } else if (result.getStatus()==ITestResult.FAILURE){
            logger.info("test FAILED: {}. \n\t\t Screenshot -> {} ",
                    result.getMethod().getMethodName(),
                    screen.takeScreenshot(driver)
                    );
        } else if(result.getStatus()==ITestResult.SKIP){
            logger.info("Test SKIPPED");
        }
        logger.info("Stop test");
        logger.info("*****************************************************");
        softAssert.assertAll();
    }
    public String newEmail(){
        int i = (int) ((System.currentTimeMillis()/1000)%3600);
        String email = "sara" + i + "@gmail.com";
        return email;
    }


    protected void clickButton(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        button.click();
    }

}
