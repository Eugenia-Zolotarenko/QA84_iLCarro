package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class HomePageTests extends TestBase {

    HomePage home;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        home = new HomePage(driver);
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest() {

        Assert.assertTrue(
                home.isPageTitleCorrect("Find your car now!"),
                "Home page title is incorrect"
        );
    }

    @Test(groups = {"smoke", "regr"})
    public void isHomePageDisplayedPositiveTest() {

        Assert.assertTrue(
                home.isHomeComponentPresent(),
                "Home page component is not displayed"
        );
    }

    @Test(groups = {"smoke", "regr"})
    public void yallaButtonIsVisiblePositiveTest() {

        Assert.assertTrue(
                home.isYallaButtonPresent(),
                "Yalla button is not displayed"
        );
    }

    @Test(groups = {"header", "min", "regr"})
    public void mobileHeaderIsVisiblePositiveTest() {

        home.setWindowWidthTo(500);

        Assert.assertTrue(
                home.isMobileHeaderPresent(),
                "Mobile header is not displayed"
        );
    }

    @Test(groups = {"regr", "smoke"})
    public void brokenLinksOnHomePageTest() {

        List<String> urls =
                home.getLinkUrls();

        home.verifyBrokenLinks(urls);
    }

    @Test(groups = {"regr", "smoke"})
    public void brokenImagesOnHomePageTest() {

        List<String> broken =
                home.getBrokenImageUrls();

        home.verifyBrokenImg(broken);
    }
}