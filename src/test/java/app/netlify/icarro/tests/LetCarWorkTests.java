package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LetCarWorkTests extends TestBase {
    LetCarWorkPage car;

    @BeforeMethod
    public void preconditions() {
        driver = app.getDriver();
        car = new HomePage(driver).getLetCarWorkPage();
        driver.navigate().refresh();
    }

    @Test
    public void isPageTitleCorrectPositiveSmokeTest() {
        car.isPageTitleCorrect("Let the car work");
    }

    @DataProvider(name = "requiredFields")
    public Object[][] requiredFields() {
        return new Object[][] {
                {"manufacture", "Toyota", "Make"},
                {"model", "Corolla", "Model"},
                {"year", "2020", "Year"},
                {"fuel", "petrol", "Fuel"},
                {"seats", "5", "Seats"},
                {"carClass", "Economy", "Car class"},
                {"serialNumber", "QA12345", "Car registration number"},
                {"pricePerDay", "50", "Price"}
        };
    }

    @Test(dataProvider = "requiredFields")
    public void requiredFieldBlocksSubmitAndRecovers(String field, String value, String label) {
        car.fillValidForm().verifySubmitEnabled(true);
        car.fillField(field, "")
                .verifyRequired(field, true)
                .verifySubmitEnabled(false);
        car.fillField(field, value)
                .verifyRequired(field, false)
                .verifySubmitEnabled(true);
    }
}
