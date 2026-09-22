package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SignUpPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class SignUpPageTests extends TestBase {

    SignUpPage signUp;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        signUp = new HomePage(driver).getSignUpPage();
    }


    @Test(groups = {"smoke"})
    public void isPageTitleSignUpCorrectPositiveSmokeTest() {
        signUp.isPageTitleCorrect("Registration");
    }

    @Test(groups = {"regr"})
    public void createAccountPositiveTest() {
        signUp.fillRegisterForm(
                "Sara",
                "Barabu",
                signUp.newEmail(),
                "Ss1a2r3a!",
                "checked").clickSubmitButton();

        getSoftAssert().assertTrue(
                signUp.isMessageRegisteredPresent(),
                "User should be Registered");

        signUp.clickModalOkButton(By.xpath("//button[.='OK']"));

        getSoftAssert().assertTrue(
                signUp.isLogOutButtonPresent(),
                "User should be logged in");
    }

    @Test(groups = {"regr"})
    public void goRegistrationFormFromLoginPagePositiveTest() {
    signUp.goRegistrationFormFromLoginPage();

    getSoftAssert().assertTrue(signUp.isElementPresent(
            By.xpath("//h1[text()='Registration']")));
    }
}

