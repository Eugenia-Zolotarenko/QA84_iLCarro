package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.SignUpTestData;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SignUpPage;
import app.netlify.icarro.utils.CsvReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


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
        List<Map<String, String>> csvData = CsvReader.readCsv("c:\\JavaQAProjects\\QA84_iLCarro\\src\\test\\resources\\signup_negative_testdata_big.csv");
        Object[][] data = new Object[csvData.size()][1];

        for (int i = 0; i < csvData.size(); i++) {
            Map<String, String> row = csvData.get(i);
            SignUpTestData testData = new SignUpTestData(
                    row.get("firstName"),
                    row.get("lastName"),
                    row.get("email"),
                    row.get("password"),
                    row.get("expectedError"),
                    row.get("testDescription"));
            data[i][0] = testData;
        }
        return data;
    }

    @Test(dataProvider = "signUpNegativeTestData", groups = {"negative"}, priority = 10)
    public void testSignUpNegativeScenarios(SignUpTestData testData) {
        SoftAssert softly = new SoftAssert();
        logger.info("Running test: " + testData.getTestDescription());

        signUp.fillFirstName(testData.getFirstName());
        signUp.fillLastName(testData.getLastName());
        signUp.fillEmail(testData.getEmail());
        signUp.fillPassword(testData.getPassword());
        signUp.acceptTerms();

        boolean submitEnabled = signUp.isSubmitButtonEnabled();

        if (!submitEnabled) {
            softly.assertTrue(
                    signUp.isErrorMessageDisplayed(testData.getExpectedError()),
                    "Expected error not displayed: " + testData.getExpectedError() +
                            " | Test: " + testData.getTestDescription()
            );
        } else {
            logger.warn("BUG: Submit button 'Yalla' is enabled despite invalid data | Test: " +
                    testData.getTestDescription());
            signUp.clickSubmitButton();
            softly.assertTrue(
                    signUp.isRegistrationFailedModalDisplayed(),
                    "Expected 'Registration failed' modal after submit with invalid data | Test: " +
                            testData.getTestDescription()
            );
        }
        softly.assertAll();
    }
}
