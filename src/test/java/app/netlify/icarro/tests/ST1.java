package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.SP1;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ST1 extends TestBase {

    SP1  search;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        search = new SP1(driver);
    }


    // ==================== SEARCH ====================

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


    @Test(groups = {"regr"})
    public void searchWithCitySelectedFromListPositiveTest() {
        search.enterCity("ri")
                .selectCityFromSuggestions("Rishon LeZion")
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

        getSoftAssert().assertTrue(
                search.isFirstCarName("Chevrolet Comaro")
        );

        Assert.assertTrue(search.isCarPricePresent());
    }


    @Test(dataProvider = "searchData", groups = {"regr"})
    public void searchWithCsvDataPositiveTest(String city, int fromDays, int toDays) {
        search.enterCity(city)
                .clickDatesField()
                .selectFutureDay(fromDays)
                .selectFutureDay(toDays)
                .clickSearchButton();

        Assert.assertTrue(search.isSearchResultPresent());
    }


    // ==================== CITY ====================

    @Test(groups = {"smoke", "regr"})
    public void citySuggestionsPositiveTest() {
        search.enterCity("ri");
        search.pause(2000);

        Assert.assertTrue(search.areCitySuggestionsPresent());

        search.selectCityFromSuggestions("Rishon LeZion");

        Assert.assertEquals(
                search.getCityValue(),
                "Rishon LeZion"
        );

        search.pause(2000);
    }


    @Test(groups = {"regr"})
    public void citySuggestionsOnEmptyFieldPositiveTest() {
        search.clickCityField();

        Assert.assertTrue(search.areCitySuggestionsPresent());

        search.pause(2000);
        System.out.println(search.getCitySuggestions());

        search.scrollCitySuggestionsToBottom()
                .pause(2000);

        Assert.assertTrue(search.isCitySuggestionsScrolledToBottom());
    }


    // ==================== CALENDAR ====================

    @Test(groups = {"regr"})
    public void disablePastDatesPositiveTest() {
        search.clickDatesField();

        Assert.assertTrue(
                search.isPastDateDisabled(),
                "Past date is not disabled"
        );
    }


    @Test(groups = {"regr"})
    public void calendarUpperBoundaryPositiveTest() {
        search.clickDatesField()
                .selectMaxFutureDate();

        Assert.assertTrue(search.isMaxFutureDateSelected());
        Assert.assertTrue(search.isDateAfterMaxDisabled());
    }


    @Test(groups = {"regr"})
    public void nextMonthButtonAtUpperBoundaryPositiveTest() {
        search.clickDatesField()
                .selectMaxFutureDate();

        Assert.assertTrue(search.isNextMonthButtonDisabled());
    }


    @Test(groups = {"regr"})
    public void disableSearchWithoutDatesPositiveTest() {
        search.enterCity("Tel Aviv");

        Assert.assertEquals(
                search.getDatesValue(),
                "",
                "Dates field is not empty"
        );

        Assert.assertTrue(
                search.isSearchButtonDisabled(),
                "Yalla! button is enabled without dates"
        );
    }


    // ==================== SEARCH RESULTS ====================

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


    // ==================== PAGINATION ====================

    @Test(groups = {"regr"})
    public void paginationOfSearchResultsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(100)
                .selectFutureDay(102)
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
    public void scrollToSearchResultsAfterPaginationPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(100)
                .selectFutureDay(102)
                .clickSearchButton()
                .scrollToSearchResults();

        search.clickNextPageButton();

        Assert.assertTrue(search.isSearchResultsVisibleInViewport());
    }


    @Test(groups = {"regr"})
    public void paginationBoundaryButtonsPositiveTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults();

        Assert.assertTrue(search.isPreviousPageButtonDisabled());

        while (!search.isNextPageButtonDisabled()) {
            search.clickNextPageButton();
        }

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


    // ==================== CAR DETAILS ====================

    @Test(groups = {"regr"})
    public void selectCarFromSearchResultsPositiveTest() {
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
    public void carDetailsLoadingFailureNegativeTest() {
        search.enterCity("Tel Aviv")
                .clickDatesField()
                .selectFutureDay(2)
                .selectFutureDay(4)
                .clickSearchButton()
                .scrollToSearchResults()
                .selectFirstCar();

        Assert.assertTrue(search.isCarLoadingErrorPresent());
    }


    // ==================== NEGATIVE ====================

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


    @Test(dataProvider = "beershebaNoCarsData", groups = {"regr"})
    public void searchWithNoAvailableCarsPositiveTest(int fromDays, int toDays) {
        search.enterCity("Beersheba")
                .clickDatesField()
                .selectFutureDay(fromDays)
                .selectFutureDay(toDays)
                .clickSearchButton();

        Assert.assertTrue(search.isNoCarsMessagePresent());
    }


    // ==================== DATA PROVIDERS ====================

    @DataProvider(name = "searchData")
    public Object[][] searchData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/data/searchData.csv")
        );

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",");

            data.add(new Object[]{
                    values[0],
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2])
            });
        }

        reader.close();

        return data.toArray(new Object[0][]);
    }


    @DataProvider(name = "beershebaNoCarsData")
    public Object[][] beershebaNoCarsData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/data/beershebaNoCarsData.csv")
        );

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",");

            data.add(new Object[]{
                    Integer.parseInt(values[0]),
                    Integer.parseInt(values[1])
            });
        }

        reader.close();

        return data.toArray(new Object[0][]);
    }
}