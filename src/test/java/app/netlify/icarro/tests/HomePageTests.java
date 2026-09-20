package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class HomePageTests extends TestBase {
    HomePage home;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        home = new HomePage(driver);
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest(){
        home.isPageTitleCorrect("Find your car now!");
    }

    @Test (groups = {"smoke", "regr"})
    public void isHomePageDisplayedPositiveTest(){
        home.isHomeComponentPresent();
    }

    @Test (groups = {"smoke", "regr", "header"})
    public void loginLinkIsVisiblePositiveTest() {
        getSoftAssert().assertTrue(home.isYallaButtonPresent(),
                "Button Sign Up is not displayed");
    }

    @Test
    public void testPageLinks() {
        home.verifyLinks("https://icarro-v1.netlify.app/let-car-work", getSoftAssert());
    }
}

