package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class HomePageTests extends TestBase {

    HomePage home;


    // SETUP

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        home = new HomePage(driver);
    }

    // HOME PAGE - POSITIVE TESTS

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest() {

        Assert.assertTrue(
                home.isPageTitleCorrect("Find your car now!"),
                "Page title is incorrect"
        );
    }


    @Test(groups = {"smoke", "regr"})
    public void isHomePageDisplayedPositiveTest() {

        Assert.assertTrue(
                home.isHomeComponentPresent(),
                "Home component is not displayed"
        );
    }


    // HEADER - POSITIVE TESTS


    @Test(groups = {"smoke", "regr", "header"})
    public void loginLinkIsVisiblePositiveTest() {

        getSoftAssert().assertTrue(
                home.isYallaButtonPresent(),
                "Button Sign Up is not displayed"
        );
    }


    @Test(groups = {"smoke", "regr"})
    public void testPageLinksPositiveTest() {

        home.verifyLinks(
                "https://icarro-v1.netlify.app/let-car-work",
                getSoftAssert()
        );
    }


    @Test(groups = {"smoke", "regr", "header", "min"})
    public void mobileHeaderIsVisiblePositiveTest() {

        home.setWindowWidthTo(500);

        Assert.assertTrue(
                home.isMobileHeaderPresent(),
                "Mobile header is not displayed"
        );
    }

    // NEGATIVE TESTS

    @Test(groups = {"negative", "regr"})
    public void isPageTitleCorrectNegativeTest() {

        Assert.assertFalse(
                home.isPageTitleCorrect("Wrong Page Title"),
                "Page title should not match the wrong title"
        );
    }


    @Test(groups = {"negative", "regr", "header", "min"})
    public void mobileHeaderIsNotVisibleOnDesktopNegativeTest() {

        home.setWindowWidthTo(1920);

        Assert.assertFalse(
                home.isMobileHeaderPresent(),
                "Mobile header should not be displayed on desktop"
        );
    }


    @Test(groups = {"negative", "regr"})
    public void testInvalidPageLinkNegativeTest() {

        home.verifyLinks(
                "https://icarro-v1.netlify.app/wrong-page",
                getSoftAssert()
        );
    }
}