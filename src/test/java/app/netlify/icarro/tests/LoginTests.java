package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.LoginData;
import app.netlify.icarro.pages.HeaderPage;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LoginPage;
import app.netlify.icarro.utils.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LoginTests extends TestBase {

    LoginPage login;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        login = new HomePage(driver)
                .getLoginPage();
    }


    @Test(
            groups = {"regr", "positive"},
            dataProvider = "validLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void loginWithValidCredentialsPositiveTest(
            LoginData data) {

        login.login(data);

        Assert.assertEquals(
                login.getModalTitleText(),
                "You are logged in success",
                "Successful login message is incorrect"
        );

        login.clickOkButton();

        HeaderPage header =
                new HomePage(driver)
                        .getHeaderPage();

        Assert.assertTrue(
                header.isLogOutDisplayed(),
                "Log out is not displayed after successful login"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void goRegistrationFormFromLoginPagePositiveTest() {

        login.goRegistrationFormFromLoginPage();

        Assert.assertTrue(
                login.isPageTitleCorrect("Registration"),
                "Registration page is not opened"
        );
    }


    @Test(
            groups = {"regr", "negative"},
            dataProvider = "invalidLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void loginWithInvalidCredentialsNegativeTest(
            LoginData data) {

        login.login(data);

        Assert.assertEquals(
                login.getModalTitleText(),
                "Login failed",
                "Login failed message is incorrect"
        );
    }


    @Test(
            groups = {"regr", "negative"},
            dataProvider = "emptyLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void loginWithEmptyFieldsNegativeTest(
            LoginData data) {

        login.fillLoginFormWithData(data);

        Assert.assertFalse(
                login.isSubmitEnable(),
                "Submit button is enabled for empty required field"
        );
    }
}