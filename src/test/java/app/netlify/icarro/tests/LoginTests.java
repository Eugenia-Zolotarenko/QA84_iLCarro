package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.LoginData;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LoginPage;
import app.netlify.icarro.utils.LoginDataProvider;
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

        Assert.assertTrue(
                login.isPageTitleCorrect("Registration")
        );
    }

    @Test(
            dataProvider = "invalidLoginData",
            dataProviderClass = LoginDataProvider.class
    )
    public void loginWithInvalidEmailNegativeTest(LoginData data) {

        login.fillLoginFormWithData(data)
                .clickSubmitButton();

        getSoftAssert().assertEquals(
                login.getModalTitleText(),
                "Login failed"
        );

        login.clickOkButton();
    }


    @Test
    public void loginWithEmptyEmailNegativeTest() {

        login.login(
                "",
                "Aa123456!"
        );

        Assert.assertFalse(
                login.isSubmitEnable()
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