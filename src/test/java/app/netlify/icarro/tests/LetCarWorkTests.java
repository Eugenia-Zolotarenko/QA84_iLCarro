package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LetCarWorkTests extends TestBase {
    LetCarWorkPage car;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        new HomePage(driver).getLetCarWorkPage();
        car = new LetCarWorkPage(driver);
    }

    @Test(groups = {"smoke", "regr"})
    public void isPageTitleCorrectPositiveTest(){
        Assert.assertEquals(car.getPageTitle(), "Let the car work", "Page title");
    }

    @Test(groups = {"regr"})
    public void fillValidFormSubmitEnabledPositiveTest() {
        Assert.assertTrue(car.fillValidForm().isSubmitEnabled(), "Submit enabled");
    }

    @Test(groups = {"regr"})
    public void fillRequiredFieldsSubmitEnabledPositiveTest() {
        car.fillRequiredFields("Toyota", "Corolla", "2020",
                "petrol", "5", "Economy",
                "QA12345", "50");
        Assert.assertTrue(car.isSubmitEnabled(), "Submit enabled");
    }

    @Test(groups = {"regr"})
    public void emptyManufactureShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("manufacture");
    }

    @Test(groups = {"regr"})
    public void emptyModelShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("model");
    }

    @Test(groups = {"regr"})
    public void emptyYearShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("year");
    }

    @Test(groups = {"regr"})
    public void emptyFuelShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("fuel");
    }

    @Test(groups = {"regr"})
    public void emptySeatsShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("seats");
    }

    @Test(groups = {"regr"})
    public void emptyCarClassShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("carClass");
    }

    @Test(groups = {"regr"})
    public void emptySerialNumberShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("serialNumber");
    }

    @Test(groups = {"regr"})
    public void emptyPricePerDayShowsErrorAndBlocksSubmitNegativeTest() {
        assertRequiredFieldBlocksSubmit("pricePerDay");
    }

    private void assertRequiredFieldBlocksSubmit(String field) {
        car.fillValidForm().fillField(field, "");
        Assert.assertEquals(car.getFieldError(field), "Required", field + " error");
        Assert.assertFalse(car.isSubmitEnabled(), "Submit enabled");
    }
}
