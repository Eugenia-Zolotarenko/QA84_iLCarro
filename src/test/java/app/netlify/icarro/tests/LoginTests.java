package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LoginPage;
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
    public void loginWithInvalidCredentialsNegativeTest() {
        login.login("wronguser@test.com", "WrongPassword123!");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void loginWithEmptyEmailNegativeTest() {
        login.login("", "Password123!");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void loginWithEmptyPasswordNegativeTest() {
        login.login("test@test.com", "");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void loginWithEmptyFieldsNegativeTest() {
        login.login("", "");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void loginWithValidCredentialsPositiveTest() {
        login.login("oksana.icarro.test@gmail.com", "Aa123456!");

        Assert.assertTrue(login.isLoginSuccessful());
        }
    }
