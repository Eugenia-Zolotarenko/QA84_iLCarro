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
        List<Map<String, String>> csvData = CsvReader.readCsv("src/test/resources/signup_negative_testdata_all.csv");
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
        logger.info("TEST: {}", testData.getTestDescription());
        logger.info("Expected error field: {}", testData.getExpectedField());



        // =================================================
        // STEP 1: Fill registration form
        // =================================================

        signUp.fillFirstName(testData.getFirstName());
        signUp.fillLastName(testData.getLastName());

        // Generate a new valid email for all negative tests
        // except tests where email itself is being tested.
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

        // =================================================
        // STEP 2. EXPECTED CLIENT RESULT
        // =================================================

        logger.info("-------------------------------------------------");
        logger.info("EXPECTED CLIENT RESULT:");
        logger.info("- Submit button should be DISABLED");
        logger.info("- Validation error should be displayed for field '{}'",
                testData.getExpectedField());


        // =================================================
        // STEP 3. ACTUAL CLIENT RESULT
        // =================================================

        boolean submitEnabled = signUp.isSubmitButtonEnabled();

        boolean errorDisplayed =
                signUp.isErrorMessageDisplayed(
                        testData.getExpectedField());

        boolean submitDisabled = !submitEnabled;

        logger.info("-------------------------------------------------");
        logger.info("ACTUAL CLIENT RESULT:");
        logger.info("- Submit button enabled: {}", submitEnabled);
        logger.info("- Submit button disabled: {}", submitDisabled);
        logger.info("- Validation error displayed: {}", errorDisplayed);


        // =================================================
        // STEP 4. CLIENT VALIDATION
        // =================================================

        boolean clientValidationPassed =
                submitDisabled && errorDisplayed;

        logger.info("-------------------------------------------------");
        logger.info("CLIENT VALIDATION:");

        logger.info(
                "Expected: Submit disabled = true, Error displayed = true");

        logger.info(
                "Actual:   Submit disabled = {}, Error displayed = {}",
                submitDisabled,
                errorDisplayed);

        logger.info(
                "CLIENT VALIDATION: {}",
                clientValidationPassed ? "PASS" : "FAIL");


        // =================================================
        // SCENARIO A
        // Client validation works
        // =================================================

        if (!submitEnabled) {

            logger.info("-------------------------------------------------");
            logger.info("SCENARIO A: Submit button is DISABLED.");

            logger.info("SERVER VALIDATION: N/A");
            logger.info("Expected: Backend request should not be submitted.");
            logger.info("Actual:   Registration was not submitted through UI.");

            logger.info("-------------------------------------------------");

            if (clientValidationPassed) {

                logger.info("OVERALL TEST RESULT: PASS");

            } else {

                logger.error("OVERALL TEST RESULT: FAIL");
            }

            getSoftAssert().assertTrue(
                    clientValidationPassed,
                    "Client validation failed. " +
                            "Expected Submit disabled and validation error displayed. " +
                            "Test: " +
                            testData.getTestDescription());

            logger.info("END NEGATIVE SIGN UP TEST");
            logger.info("=================================================");

            return;
        }

            // =================================================
           // SCENARIO B
          //Client validation failed
         // =================================================

        logger.info("-------------------------------------------------");
        logger.warn("SCENARIO B: Submit button is ENABLED.");
        logger.warn("Client-side validation did not block invalid data.");

        logger.info("CLIENT VALIDATION: FAIL");

        logger.info("Expected: Submit disabled = true");
        logger.info("Actual:   Submit disabled = false");


        // =================================================
        // STEP 5. Submit invalid data
        // =================================================

        logger.info("Submitting invalid registration to test backend validation.");

        signUp.clickSubmitButton();


        // =================================================
        // STEP 6. Backend actual result
        // =================================================

        boolean registrationFailed =
                signUp.isRegistrationFailedModalDisplayed();

        boolean registrationSucceeded =
                signUp.isMessageRegisteredPresent(
                        "Registered",
                        "You are logged in success");


        // =================================================
        // STEP 7. Backend validation
        // =================================================

        logger.info("-------------------------------------------------");
        logger.info("SERVER VALIDATION:");

        logger.info("Expected: Backend must REJECT invalid registration.");

        logger.info(
                "Actual: Registration failed modal = {}",
                registrationFailed);

        logger.info(
                "Actual: Registration succeeded = {}",
                registrationSucceeded);


        boolean serverValidationPassed =
                registrationFailed && !registrationSucceeded;

        logger.info(
                "SERVER VALIDATION: {}",
                serverValidationPassed ? "PASS" : "FAIL");


        // =================================================
        // STEP 8. Overall result
        // =================================================

        boolean overallPassed =
                clientValidationPassed && serverValidationPassed;

        logger.info("-------------------------------------------------");

        logger.info(
                "OVERALL TEST RESULT: {}",
                overallPassed ? "PASS" : "FAIL");


        // =================================================
        // STEP 9. Assertions
        // =================================================

        getSoftAssert().assertTrue(
                clientValidationPassed,
                "Client validation failed. " +
                        "Invalid data was not blocked on client side. " +
                        "Test: " +
                        testData.getTestDescription());

        getSoftAssert().assertTrue(
                serverValidationPassed,
                "Server validation failed. " +
                        "Invalid registration was accepted or unexpected result occurred. " +
                        "Test: " +
                        testData.getTestDescription());

        logger.info("=================================================");
    }
}
