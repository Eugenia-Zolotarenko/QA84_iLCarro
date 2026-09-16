package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SearchPage;
import org.testng.annotations.BeforeMethod;

public class SearchTests extends TestBase {
    SearchPage search;

    @BeforeMethod
    public void precondition(){
        search = new SearchPage(driver);
    }
}
