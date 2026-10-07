package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.model.Car;
import app.netlify.icarro.pages.LetCarWorkPage;
import app.netlify.icarro.utils.CarDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

    @Test(groups = {"regr"}, dataProvider = "validCarDataObj", dataProviderClass = CarDataProvider.class)
    public void fillRequiredFieldsSubmitEnabledPositiveTest(Car carData) {
        Assert.assertTrue(car.fillForm(carData).isSubmitEnabled(), "Submit enabled");
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

    // 84T1-8 (step 1): form fields are present, Year/Doors/Seats/Price are numeric inputs
    @Test(groups = {"regr"})
    public void formShowsAllFieldsPositiveTest() {
        getSoftAssert().assertTrue(car.hasCityField(), "city field");
        for (String name : new String[]{"manufacture", "model", "year", "fuel", "gear", "wheelsDrive", "doors",
                "seats", "carClass", "serialNumber", "pricePerDay", "about", "image"}) {
            getSoftAssert().assertTrue(car.hasField(name), name + " field");
        }
        getSoftAssert().assertTrue(car.hasPhotoFileInput(), "photo file input");
    }

    @Test(groups = {"regr"})
    public void numberFieldsAreNumericInputsPositiveTest() {
        for (String name : new String[]{"year", "doors", "seats", "pricePerDay"}) {
            getSoftAssert().assertEquals(car.getFieldType(name), "number", name + " input type");
        }
    }

    // 84T1-10: available selector values
    @Test(groups = {"regr"})
    public void fuelSelectorHasExpectedChoicesPositiveTest() {
        assertSelectorChoices("fuel", "Select fuel", "Petrol", "Diesel", "Hybrid", "Electric");
    }

    @Test(groups = {"regr"})
    public void gearSelectorHasExpectedChoicesPositiveTest() {
        assertSelectorChoices("gear", "Select gear", "Automatic", "Manual");
    }

    @Test(groups = {"regr"})
    public void wheelsDriveSelectorHasExpectedChoicesPositiveTest() {
        assertSelectorChoices("wheelsDrive", "Select WD", "FWD", "RWD", "AWD");
    }

    // 84T1-11: Seats and Price lower boundaries
    @Test(groups = {"regr"})
    public void seatsZeroShowsErrorAndBlocksSubmitNegativeTest() {
        car.fillValidForm().fillField("seats", "0");
        Assert.assertEquals(car.getFieldError("seats"), LetCarWorkPage.SEATS_MIN_ERROR, "Seats error");
        Assert.assertFalse(car.isSubmitEnabled(), "Submit enabled");
    }

    @Test(groups = {"regr"})
    public void priceZeroShowsErrorAndBlocksSubmitNegativeTest() {
        car.fillValidForm().fillField("pricePerDay", "0");
        Assert.assertEquals(car.getFieldError("pricePerDay"), LetCarWorkPage.PRICE_MIN_ERROR, "Price error");
        Assert.assertFalse(car.isSubmitEnabled(), "Submit enabled");
    }

    @Test(groups = {"regr"})
    public void seatsOneAndPriceOneAreAcceptedPositiveTest() {
        car.fillValidForm().fillField("seats", "1").fillField("pricePerDay", "1");
        Assert.assertEquals(car.getFieldError("seats"), "", "Seats error");
        Assert.assertEquals(car.getFieldError("pricePerDay"), "", "Price error");
        Assert.assertTrue(car.isSubmitEnabled(), "Submit enabled");
    }

    private void assertSelectorChoices(String name, String placeholder, String... choices) {
        Assert.assertEquals(car.getSelectedOption(name), placeholder, name + " placeholder");
        List<String> expected = new ArrayList<>(List.of(placeholder));
        expected.addAll(List.of(choices));
        Assert.assertEquals(car.getOptions(name), expected, name + " choices");
        for (String choice : choices) {
            Assert.assertEquals(car.selectByText(name, choice).getSelectedOption(name), choice, name + " selected value");
        }
    }

    private void assertRequiredFieldBlocksSubmit(String field) {
        car.fillValidForm().fillField(field, "");
        Assert.assertEquals(car.getFieldError(field), LetCarWorkPage.REQUIRED_ERROR, field + " error");
        Assert.assertFalse(car.isSubmitEnabled(), "Submit enabled");
    }

    @Test(groups = {"regr"}, dataProvider = "carsFromJson", dataProviderClass = CarDataProvider.class)
    public void fillValidFormJsonSubmitEnabledPositiveTest(Car testData) {
        Assert.assertTrue(car.fillForm(testData).isSubmitEnabled(), "Submit button should be enabled");
    }
}
