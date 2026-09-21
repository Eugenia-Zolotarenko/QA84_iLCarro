package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends TestBase {

    LoginPage login;

    @BeforeMethod
    public void precondition() {
        driver.get("https://icarro-v1.netlify.app/login");
        login = new LoginPage(driver);
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
