package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.NewUserSignUp;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SignUpPage;
import app.netlify.icarro.utils.SignUpDataProvider;
import app.netlify.icarro.utils.TestDataGenerator;
import org.testng.Assert;
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
                        TestDataGenerator.generateEmail(),
                        "Ss1a2r3a!")
                .acceptTerms()
                .clickSubmitButton();

        getSoftAssert().assertTrue(
                signUp.isMessageRegisteredPresent(
                        "Registered",
                        "You are logged in success"),
                "User should be Registered");
        signUp.clickModalWindowOkButton();

        Assert.assertTrue(
                signUp.isLogOutButtonPresent(),
                "User should be logged in");
    }



    @Test(
            dataProvider = "signUpNegativeTestData",
            dataProviderClass = SignUpDataProvider.class,
            groups = {"regr", "frontend"})
    public void signUpFrontendNegativeTests(NewUserSignUp testData) {

        logger.info("-------------------------------------------------");
        logger.info("Case: '{}' | expected error field: {}",
                testData.getTestDescription(), testData.getExpectedField());

        signUp.fillRegisterForm(testData)
                .acceptTerms();


        boolean submitDisabled = signUp.isSubmitButtonDisabled();

        boolean errorDisplayed =
                signUp.isErrorMessageDisplayed(
                        testData.getExpectedField());

        logger.info("EXPECTED: Submit should be disabled for invalid data");

        logger.info("ACTUAL: Submit disabled: {} | Expected field error displayed: {} "
                , submitDisabled, errorDisplayed);

        boolean frontendPassed =
                submitDisabled && errorDisplayed;

        if (frontendPassed) {
            logger.info("CLIENT VALIDATION: PASS");
        } else {
            logger.error("CLIENT VALIDATION: FAIL");
        }

        getSoftAssert().assertTrue(
                frontendPassed,
                "Frontend validation failed. " +
                        "Test: " + testData.getTestDescription());

        logger.info("-------------------------------------------------");
    }




}
