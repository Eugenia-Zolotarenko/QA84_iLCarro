package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.SignUpTestData;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SignUpPage;
import app.netlify.icarro.utils.CsvReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


public class SignUpPageTests extends TestBase {

    SignUpPage signUp;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        driver.get("https://icarro-v1.netlify.app/");
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
                signUp.isMessageRegisteredPresent(
                        "Registered",
                        "You are logged in success"),
                "User should be Registered");
        signUp.clickModalWindowOkButton();

        Assert.assertTrue(
                signUp.isLogOutButtonPresent(),
                "User should be logged in");
    }


    @DataProvider(name = "signUpNegativeTestData")
    public Object[][] provideSignUpNegativeData() {
        List<Map<String, String>> csvData = CsvReader.readCsv("src/test/resources/signup_negative_testdata_10.csv");
        Object[][] data = new Object[csvData.size()][1];

        for (int i = 0; i < csvData.size(); i++) {
            Map<String, String> row = csvData.get(i);
            SignUpTestData testData = new SignUpTestData(
                    row.get("firstName"),
                    row.get("lastName"),
                    row.get("email"),
                    row.get("password"),
                    row.get("expectedField"),
                    row.get("testDescription"));
            data[i][0] = testData;
        }
        return data;
    }

    @Test(
            dataProvider = "signUpNegativeTestData",
            groups = {"negative"},
            priority = 10)
    public void testSignUpNegativeScenarios(SignUpTestData testData) {
        logger.info("=================================================");
        logger.info("START NEGATIVE SIGN UP TEST");
        logger.info("Test: {}", testData.getTestDescription());
        logger.info("Expected error field: {}", testData.getExpectedField());

        // =================================================
        // STEP 1: Fill registration form
        // =================================================
        signUp.getWait(100);

        signUp.fillFirstName(testData.getFirstName());
        signUp.fillLastName(testData.getLastName());

        // For all negative tests except email-related tests
        // generate a new valid email.
        String email = testData.getEmail();

        if (!"username".equals(testData.getExpectedField())) {
            email = signUp.newEmail();
            logger.info("Email source: GENERATED | Email: {}", email);
        } else {
            logger.info("Email source: CSV | Email: {}", email);
        }
        signUp.fillEmail(email);

        signUp.fillPassword(testData.getPassword());
        signUp.acceptTerms();

        logger.info("Registration form filled.");
        logger.info("Terms of use: accepted");

        boolean submitEnabled = signUp.isSubmitButtonEnabled();

        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        logger.info("SUBMIT BUTTON STATE CHECK:");
        logger.info("Is 'Y'alla!' button clickable/enabled? -> {}", submitEnabled);
        logger.info("Button HTML 'disabled' attribute present? -> {}",
                signUp.getSubmitButton().getAttribute("disabled") != null);
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // =================================================
        // SCENARIO A
        // Submit button is disabled
        // =================================================

        if (!submitEnabled) {

            logger.info("SCENARIO A: Submit button is DISABLED (Expected for invalid input).");

            boolean errorDisplayed =
                    signUp.isErrorMessageDisplayed(
                            testData.getExpectedField());
            if (errorDisplayed) {
                logger.info("CLIENT VALIDATION: PASS | Error displayed for field '{}'", testData.getExpectedField());
                logger.info("SERVER VALIDATION: N/A");
                logger.info("OVERALL TEST RESULT: PASS");

            } else {
                logger.error("CLIENT VALIDATION: FAIL | Button is disabled, but no validation error message for '{}'"
                        , testData.getExpectedField());
                logger.info("SERVER VALIDATION: N/A");
                logger.error("OVERALL TEST RESULT: FAIL");
            }

            getSoftAssert().assertTrue(
                    errorDisplayed,
                    "Submit button is disabled, but expected validation " +
                            "error is not displayed. Field: " +
                            testData.getExpectedField() + " | Test: " +
                            testData.getTestDescription());

            // =================================================
            // SCENARIO B
            // Submit button is enabled
            // =================================================

        } else {
            logger.warn(
                    "SCENARIO B: Submit button is ENABLED despite invalid data.");
            logger.warn("CLIENT VALIDATION DEFECT: Button stayed active.");
            logger.info(
                    "Submitting invalid registration to verify server-side validation.");
            signUp.clickSubmitButton();

            // -------------------------------------------------
            // STEP 4B: Check server response
            // -------------------------------------------------

            boolean registrationFailed =
                    signUp.isRegistrationFailedModalDisplayed();

            boolean registrationSucceeded =
                    signUp.isMessageRegisteredPresent(
                            "Registered",
                            "You are logged in success");

            logger.info(
                    "Server result: Registration failed modal = {}",
                    registrationFailed);

            logger.info(
                    "Server result: Registration succeeded = {}",
                    registrationSucceeded);

            // -------------------------------------------------
            // Server rejected invalid data
            // -------------------------------------------------

            if (registrationFailed) {

                logger.info("SERVER VALIDATION: PASS");
                logger.info("Server rejected invalid registration.");
                logger.info("OVERALL TEST RESULT: PASS");

                logger.warn("NOTE: Client-side validation defect detected, "
                        + "but server-side validation protected the application.");
            }

            // -------------------------------------------------
            // CRITICAL BUG: server accepted invalid data
            // -------------------------------------------------

            else if (registrationSucceeded) {

                logger.error("SERVER VALIDATION: FAIL");

                logger.error(
                        "CRITICAL BUG: Server ACCEPTED invalid registration. " +
                                "User was successfully registered with: " + testData.getTestDescription());

                logger.error("OVERALL TEST RESULT: FAIL");

                getSoftAssert().fail(
                        "CRITICAL BUG: Invalid registration was accepted by server. " +
                                "Test: " +
                                testData.getTestDescription());
            }

            // -------------------------------------------------
            // Unexpected result
            // -------------------------------------------------

            else {
                logger.error("SERVER VALIDATION: UNEXPECTED");

                logger.error("Neither 'Registration failed' nor 'Registered' "
                        + "modal was displayed.");

                logger.error("OVERALL TEST RESULT: FAIL");

                getSoftAssert().fail(
                        "Unexpected result after submitting invalid registration. " +
                                "Test: " + testData.getTestDescription());
            }
        }
        logger.info("END NEGATIVE SIGN UP TEST");
        logger.info("=================================================");
    }
}
