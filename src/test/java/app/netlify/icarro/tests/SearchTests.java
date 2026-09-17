package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTests extends TestBase {
    SearchPage search;

    @BeforeMethod
    public void precondition(){
        search = new SearchPage(driver);
    }

    @Test
    public void SearchByCityAndDates() {
        search.enterCity("Tel Aviv");

    }
}
