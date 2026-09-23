package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.openqa.selenium.devtools.v142.debugger.Debugger.pause;

public class SearchTests extends TestBase {
    SearchPage search;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        search = new SearchPage(driver);
    }



    @Test(groups = {"smoke", "regr"})
    public void searchWithManuallyEnteredCityAndDatesPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectDay("25")
                .selectDay("27")
                .clickSearchButton()
                .scrollToSearchResults();
        Assert.assertTrue(search.isSearchResultPresent());

    }
    @Test(groups = {"smoke", "regr"})
    public void displaySearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectDay("25")
                .selectDay("27")
                .clickSearchButton()
                .scrollToSearchResults();
        Assert.assertEquals(search.getFirstCarName(), "Chevrolet Comaro");
        Assert.assertTrue(search.isCarPricePresent());
    }
    @Test(groups = {"regr"})
    public void paginationOfSearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectDay("25")
                .selectDay("27")
                .clickSearchButton()
                .scrollToSearchResults();

        String pageBefore = search.getPageNumber();
        search.clickNextPageButton();
        String pageAfter = search.getPageNumber();
        Assert.assertNotEquals(pageBefore, pageAfter);
        search.clickPreviousPageButton();
        String pageAfterPrevious = search.getPageNumber();
        Assert.assertEquals(pageAfterPrevious, pageBefore);
    }
    @Test(groups = {"regr"})
    public void rowsPerPagePositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectDay("25")
                .selectDay("27")
                .clickSearchButton()
                .scrollToSearchResults();

        String rowsBefore = search.getRowsPerPageValue();
        int resultsBefore = search.getSearchResultsCount();
        search.selectRowsPerPage("20");
        String rowsAfter = search.getRowsPerPageValue();
        int resultsAfter = search.getSearchResultsCount();
        Assert.assertNotEquals(rowsBefore, rowsAfter);
        Assert.assertNotEquals(resultsBefore, resultsAfter);
    }
    @Test(groups = {"regr"})
    public void selectCarFromSearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectDay("25")
                .selectDay("27")
                .clickSearchButton()
                .scrollToSearchResults()
                .selectFirstCar();
        Assert.assertTrue(search.isCarPageOpened());
        Assert.assertFalse(search.isCarLoadingErrorPresent());
    }
//    @Test(groups = {"regr"})
//    public void displayCarDetailsPositiveTest() {
//        search.enterCity("Tel Aviv")
//                .clickDatesField()
//                .selectDay("25")
//                .selectDay("27")
//                .clickSearchButton()
//                .scrollToSearchResults()
//                .selectFirstCar();
//
//        Assert.assertTrue(search.isCarPageOpened());
//        Assert.assertFalse(search.isCarLoadingErrorPresent());
//    }
@Test(groups = {"regr"})
public void searchWithCitySelectedFromListPositiveTest() {
    search.enterCity("ri")
            .selectCityFromDropdown("Rishon LeZion")
            .clickDatesField()
            .selectDay("25")
            .selectDay("27")
            .clickSearchButton()
            .scrollToSearchResults();

    Assert.assertTrue(search.isSearchResultPresent());
}
//    @Test(groups = {"regr"})
//    public void invalidCityShouldNotBeAddedToDropdownNegativeTest() {
//        search.enterCity("ZZZTest987")
//                .clickDatesField()
//                .selectDay("25")
//                .selectDay("27")
//                .clickSearchButton();
//        driver.navigate().refresh();
//        search.enterCity("ZZZ");
//        search.pause(15000);
//        Assert.assertFalse(
//                search.isCityPresentInDropdown("ZZZTest987"),
//                "Invalid city was added to the autocomplete dropdown"
//        );
//    }

    @Test(groups = {"regr"})
    public void disablePastDatesPositiveTest() {
        search.clickDatesField();

        Assert.assertTrue(
                search.isPastDateDisabled(),
                "Past date is not disabled"
        );
    }
    @Test(groups = {"regr"})
    public void disableSearchWithoutDatesPositiveTest() {
        search.enterCity("Tel Aviv");

        Assert.assertEquals(
                search.getDatesValue(),
                "",
                "Dates field is not empty");
        Assert.assertTrue(
                search.isSearchButtonDisabled(),
                "Yalla! button is enabled without dates"
        );
    }
}
//By.xpath("//*[normalize-space(.)='Chevrolet']");