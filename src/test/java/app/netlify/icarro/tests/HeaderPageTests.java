package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HeaderPage;
import app.netlify.icarro.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import app.netlify.icarro.pages.LoginPage;

import java.lang.reflect.Method;

public class HeaderPageTests extends TestBase {

    HeaderPage header;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        header = new HomePage(driver).getHeaderPage();
    }

    @Test
    public void verifyHeaderLogoImagePositiveTest() {

        Assert.assertTrue(
                header.isHeaderLogoImagePresent()
        );
    }

    @Test
    public void verifyHeaderLogoLinkPositiveTest() {

        Assert.assertTrue(
                header.isHeaderLogoLinkPresent()
        );
    }

    @Test
    public void verifyHeaderLogoDisplayPositiveTest() {

        Assert.assertTrue(
                header.isHeaderLogoDisplayed()
        );
    }

    @Test
    public void verifyLoginLinkPositiveTest() {

        Assert.assertTrue(
                header.isLoginLinkDisplayed()
        );
    }

    @Test
    public void verifySignUpLinkPositiveTest() {

        Assert.assertTrue(
                header.isSignUpLinkDisplayed()
        );
    }

    @Test
    public void verifyTermsOfUseLinkPositiveTest() {

        Assert.assertTrue(
                header.isTermsOfUseLinkDisplayed()
        );
    }

    @Test
    public void verifyLetCarWorkLinkPositiveTest() {

        Assert.assertTrue(
                header.isLetCarWorkLinkDisplayed()
        );
    }

    @Test
    public void verifySearchLinkPositiveTest() {

        Assert.assertTrue(
                header.isSearchLinkDisplayed()
        );
    }

    @Test
    public void verifyHomeMenuLinkPositiveTest() {

        Assert.assertTrue(
                header.isHomeLinkDisplayed()
        );
    }


    @Test
    public void verifyHeaderMenuPositiveTest() {

        Assert.assertTrue(
                header.isHeaderMenuDisplayed()
        );
    }


    @Test
    public void verifyHeaderDisplayWithBodyWidthLessThan785PositiveTest() {

        header.setWindowWidthTo(780);

        header.pause(1000);

        Assert.assertTrue(
                header.isMobileHeaderDisplayed()
        );

        Assert.assertTrue(
                header.isMobileHeaderLogoDisplayed()
        );

        Assert.assertTrue(
                header.isHamburgerButtonDisplayed()
        );
    }

    @Test
    public void verifyHeaderMenuWithBodyWidthLessThan785PositiveTest() {

        LoginPage login =
                new HomePage(driver).getLoginPage();

        login.fillLoginForm(
                        "oksana.icarro.test@gmail.com",
                        "Aa123456!")
                .clickSubmitButton();

        login.clickOkButton();

        header.setWindowWidthTo(780);

        header.pause(1000);

        header.clickHamburgerButton();

        header.pause(1000);

        Assert.assertTrue(
                header.isMobileHeaderLogoDisplayed()
        );

        Assert.assertTrue(
                header.isSearchLinkDisplayed()
        );

        Assert.assertTrue(
                header.isLetCarWorkLinkDisplayed()
        );

        Assert.assertTrue(
                header.isTermsOfUseLinkDisplayed()
        );

        Assert.assertTrue(
                header.isLogOutDisplayed()
        );
    }
}
