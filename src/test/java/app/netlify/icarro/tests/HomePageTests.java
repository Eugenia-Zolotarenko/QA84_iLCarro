package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests extends TestBase {

    private HomePage homePage;


    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        driver.get("https://icarro-v1.netlify.app/search?page=0&size=10");
        homePage = new HomePage(driver);
    }


    // ==========================================
    // TEST 1
    // NEVER MISTAKEN FOR ANYTHING ELSE
    // ==========================================

    @Test(groups = {"smoke", "regr"})
    public void neverMistakenForAnythingElseTest() {

        getSoftAssert().assertTrue(
                homePage.isNeverMistakenBlockDisplayed(),
                "Block 'NEVER MISTAKEN FOR ANYTHING ELSE' is not displayed"
        );

        getSoftAssert().assertTrue(
                homePage.getNeverMistakenText()
                        .contains("NEVER MISTAKEN FOR ANYTHING ELSE"),
                "Incorrect text in 'NEVER MISTAKEN FOR ANYTHING ELSE' block"
        );

        getSoftAssert().assertAll();
    }


    // ==========================================
    // TEST 2
    // REVIEWS
    // ==========================================

    @Test(groups = {"reviews", "regr"})
    public void reviewsBlockTest() {

        getSoftAssert().assertTrue(
                homePage.isReviewsBlockDisplayed(),
                "Reviews block is not displayed"
        );

        getSoftAssert().assertTrue(
                homePage.getReviewsText().contains("Reviews"),
                "Reviews block does not contain expected text"
        );

        getSoftAssert().assertAll();
    }
}