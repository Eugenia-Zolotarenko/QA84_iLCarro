package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;

public class LetCarWorkTests extends TestBase {
    LetCarWorkPage car;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        car = new HomePage(driver).getLetCarWorkPage();
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest(){
        car.isPageTitleCorrect("Let the car work");
    }
}
