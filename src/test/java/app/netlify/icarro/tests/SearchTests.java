package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SearchTests extends TestBase {
    HomePage search;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        search = new HomePage(driver);
    }

    @Test
    public void SearchByCityAndDates(){
    }
}
