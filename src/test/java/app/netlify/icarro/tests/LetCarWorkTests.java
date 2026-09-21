package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import app.netlify.icarro.utils.CarDataProvider;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LetCarWorkTests extends TestBase {
    CarChecks car;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        new HomePage(driver).getLetCarWorkPage();
        car = new CarChecks(driver);
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest(){
        Assert.assertEquals(car.getPageTitle(), "Let the car work", "Page title");
    }

    @Test(dataProvider = "requiredFields", dataProviderClass = CarDataProvider.class, groups = {"regr"})
    public void requiredFieldBlocksSubmitAndRecovers(String field, String value, String label) {
        car.fillValidForm()
                .verifySubmitEnabled(true)
                .fillField(field, "")
                .verifyRequired(field, true)
                .verifySubmitEnabled(false)
                .fillField(field, value)
                .verifyRequired(field, false)
                .verifySubmitEnabled(true);
    }

    private static class CarChecks extends LetCarWorkPage {
        CarChecks(WebDriver driver) {
            super(driver);
        }

        @Override
        public CarChecks fillValidForm() {
            super.fillValidForm();
            return this;
        }

        @Override
        public CarChecks fillField(String name, String value) {
            super.fillField(name, value);
            return this;
        }

        public CarChecks verifyRequired(String name, boolean expected) {
            Assert.assertEquals(getFieldError(name), expected ? "Required" : "",
                    name + " validation message");
            return this;
        }

        public CarChecks verifySubmitEnabled(boolean expected) {
            Assert.assertEquals(isSubmitEnabled(), expected, "Submit enabled state");
            return this;
        }
    }
}
