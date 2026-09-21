package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LetCarWorkTests extends TestBase {
    LetCarWorkPage car;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        car = new HomePage(driver).getLetCarWorkPage();
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest(){
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

    @Test(dataProvider = "requiredFields", groups = {"regr"})
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
