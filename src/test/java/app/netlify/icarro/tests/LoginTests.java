package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LoginTests extends TestBase {

    LoginPage login;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        login = new HomePage(driver).getLoginPage();
    }


    @Test
    public void loginWithValidCredentialsPositiveTest() {

        login.fillLoginForm(
                        "oksana.icarro.test@gmail.com",
                        "Aa123456!")
                .clickSubmitButton();

        getSoftAssert().assertEquals(
                login.getModalTitleText(),
                "You are logged in success"
        );

        login.clickOkButton();

        login.pause(1000);

        Assert.assertTrue(
                login.isElementPresent(
                        By.xpath("//*[normalize-space(.)='Log out']")
                )
        );
    }


    @Test(groups = {"regr"})
    public void goRegistrationFormFromLoginPagePositiveTest() {

        login.goRegistrationFormFromLoginPage();

        login.assertGoToRegForm();
    }


    @Test
    public void loginWithInvalidEmailNegativeTest() {

        login.fillLoginForm(
                        "wronguser@test.com",
                        "Aa123456!")
                .clickSubmitButton();

        getSoftAssert().assertEquals(
                login.getModalTitleText(),
                "Login failed"
        );

        login.clickOkButton();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }


    @Test
    public void loginWithInvalidPasswordNegativeTest() {

        login.fillLoginForm(
                        "oksana.icarro.test@gmail.com",
                        "WrongPassword123!")
                .clickSubmitButton();

        getSoftAssert().assertEquals(
                login.getModalTitleText(),
                "Login failed"
        );

        login.clickOkButton();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }


    @Test
    public void loginWithEmptyEmailNegativeTest() {

        login.login(
                "",
                "Aa123456!"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }


    @Test
    public void loginWithEmptyPasswordNegativeTest() {

        login.login(
                "oksana.icarro.test@gmail.com",
                ""
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }

    @Test
    public void loginWithEmptyFieldsNegativeTest() {

        login.login(
                "",
                ""
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
    }
}