package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import app.netlify.icarro.utils.CarDataProvider;
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

    @Test(dataProvider = "requiredFields", dataProviderClass = CarDataProvider.class, groups = {"regr"})
    public void emptyRequiredFieldShowsErrorAndBlocksSubmitNegativeTest(String field) {
        car.fillValidForm().fillField(field, "");
        Assert.assertEquals(car.getFieldError(field), "Required", field + " error");
        Assert.assertFalse(car.isSubmitEnabled(), "Submit enabled");
    }
}
