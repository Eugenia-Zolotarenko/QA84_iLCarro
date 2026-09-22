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


    // POSITIVE
    // Login with Valid Credentials

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


    // NEGATIVE 1
    // Login with Invalid Email

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


    // NEGATIVE 2
    // Login with Invalid Password

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


    // NEGATIVE 3
    // Login with Empty Email

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


    // NEGATIVE 4
    // Login with Empty Password

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


    // NEGATIVE 5
    // Login with Empty Email and Password

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