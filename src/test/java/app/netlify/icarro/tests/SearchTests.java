package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

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
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        Assert.assertTrue(search.isSearchResultPresent());

    }
    @Test(groups = {"smoke", "regr"})
    public void displaySearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        getSoftAssert().assertEquals(search.getFirstCarName(), "Chevrolet Comaro");
        Assert.assertTrue(search.isCarPricePresent());
    }
    @Test(groups = {"regr"})
    public void paginationOfSearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
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
                .selectFutureDay(2)
                .selectFutureDay(4)
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
    public void selectCarFromSearchResultsPositiveTest() { //должна открыться без ошибки
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults()
                .selectFirstCar();

        getSoftAssert().assertTrue(search.isCarPageOpened());
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
            .selectFutureDay(2)
            .selectFutureDay(4)
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
    @Test(groups = {"regr"})
    public void displayCarDetailsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults()
                .selectFirstCar();

        Assert.assertTrue(
                search.areCarDetailsPresent(
                        "Chevrolet",
                        "Comaro",
                        "2020",
                        "30.0"
                )
        );
    }
    @Test(groups = {"regr"})
    public void changeRowsPerPagePositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        Assert.assertTrue(search.areCarsPresentInContainer());
        int carsBefore = search.getCarsCountInContainer();
        search.selectRowsPerPage("20");
        int carsAfter = search.getCarsCountInContainer();
        Assert.assertNotEquals(carsBefore, carsAfter);
    }
    @Test(groups = {"regr"})
    public void searchWithNoAvailableCarsPositiveTest() {
        search.enterCity("Beersheba")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton();

        Assert.assertTrue(search.isNoCarsMessagePresent());
    }
    @Test(groups = {"regr"})
    public void rowsPerPageOptionsPositiveTest() {
        search.enterCity("Petah Tikva")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        Assert.assertTrue(search.areCarsPresentInContainer());

        search.selectRowsPerPage("10");
        int carsFor10 = search.getCarsCountInContainer();
        System.out.println("Rows per page: 10, actual cars: " + carsFor10);

        search.selectRowsPerPage("20");
        int carsFor20 = search.getCarsCountInContainer();
        System.out.println("Rows per page: 20, actual cars: " + carsFor20);

        search.selectRowsPerPage("50");
        int carsFor50 = search.getCarsCountInContainer();
        System.out.println("Rows per page: 50, actual cars: " + carsFor50);

        getSoftAssert().assertTrue(carsFor10 <= 10);
        getSoftAssert().assertTrue(carsFor20 <= 20);
        getSoftAssert().assertTrue(carsFor50 <= 50);
    }
    @Test(groups = {"regr"})
    public void paginationBoundaryButtonsPositiveTest() {// на этом тесте ноут пошел на взлет)))
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        // На первой странице назад перейти нельзя
        Assert.assertTrue(search.isPreviousPageButtonDisabled());

        // Идём вперёд, пока кнопка Next доступна
        while (!search.isNextPageButtonDisabled()) {
            search.clickNextPageButton();
        }

        // На последней странице вперёд перейти нельзя
        Assert.assertTrue(search.isNextPageButtonDisabled());
    }
    @Test(groups = {"regr"})
    public void paginationButtonHoverUIPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        String colorBefore = search.getNextButtonColor();
        search.hoverOverNextButton();
        String colorAfter = search.getNextButtonColor();
        Assert.assertNotEquals(colorBefore, colorAfter);
    }

// NEGATIVE

    @Test(groups = {"regr"})
    public void searchWithEmptyCityNegativeTest() {
        search.clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4);

        Assert.assertTrue(search.isSearchButtonDisabled());
    }

    @Test(groups = {"regr"})
    public void searchWithInvalidCityNegativeTest() {
        search.enterCity("Berlin")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton();

        Assert.assertFalse(search.isSearchResultPresent());
    }
    @Test(groups = {"regr"})
    public void searchDataAfterPageRefreshNegativeTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4);

        driver.navigate().refresh();

        getSoftAssert().assertEquals(search.getCityValue(), "");
        Assert.assertEquals(search.getDatesValue(), "");
    }
    @Test(groups = {"regr"})
    public void carDetailsLoadingFailureNegativeTest() {//сообщение есть, машины нет
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults()
                .selectFirstCar();

        Assert.assertTrue(search.isCarLoadingErrorPresent());
    }

}
//By.xpath("//*[normalize-space(.)='Chevrolet']");