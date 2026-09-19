package app.netlify.icarro.core;

import app.netlify.icarro.utils.Screenshots;
import app.netlify.icarro.utils.SoftAssertListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Arrays;

@Listeners(SoftAssertListener.class)
public class TestBase {
    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName()));

    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    private static final ThreadLocal<SoftAssert> softly = new ThreadLocal<>();
    protected Screenshots screen = new Screenshots();
    protected WebDriver driver;

    public static SoftAssert getSoftAssert() {
        return softly.get();
    }

    @BeforeMethod(alwaysRun = true)

    public void setUp(Method method, Object[] p) {
        driver = app.init();
        softly.set(new SoftAssert());
        logger.info("start test {} with data:\n\t {}", method.getName(), Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void stopTest(ITestResult result){
        if (result.getStatus()==ITestResult.SUCCESS){
            logger.info("Test PASSED");
        } else if (result.getStatus()==ITestResult.FAILURE){
            String screenshotPath = (screen != null && driver != null)
                    ? screen.takeScreenshot(driver)
                    : "Screenshot failed (driver or screen is null)";
            logger.info("test FAILED: {}. \n\t\t Screenshot -> {} ",
                    result.getMethod().getMethodName(),  screenshotPath);

        } else if(result.getStatus()==ITestResult.SKIP){
            logger.info("Test SKIPPED");
        }
        logger.info("Stop test");
        logger.info("*****************************************************");
        softly.remove();
        app.stop();
    }
}
