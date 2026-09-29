package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SP1 extends BasePage {

    public SP1(WebDriver driver) {
        super(driver);
    }


    // ==================== CITY ====================

    @FindBy(id = "city")
    WebElement cityInput;

    public SP1 enterCity(String city) {
        type(cityInput, city);
        return this;
    }

    public SP1 clickCityField() {
        click(cityInput);
        return this;
    }

    public String getCityValue() {
        return cityInput.getAttribute("value");
    }


    @FindBy(css = "[data-testid='city-dropdown']")
    WebElement citySuggestions;

    @FindBy(css = "[data-testid='city-option']")
    List<WebElement> citySuggestionsList;

    public boolean areCitySuggestionsPresent() {
        return !citySuggestionsList.isEmpty();
    }

    public List<String> getCitySuggestions() {
        return citySuggestionsList.stream()
                .map(element -> element.getAttribute("data-value"))
                .toList();
    }

    public SP1 selectCityFromSuggestions(String city) {
        WebElement cityOption = driver.findElement(
                By.cssSelector(
                        "[data-testid='city-option'][data-value='" + city + "']"
                )
        );

        click(cityOption);
        return this;
    }

    public SP1 scrollCitySuggestionsToBottom() {
        js.executeScript(
                "arguments[0].scrollTop = arguments[0].scrollHeight;",
                citySuggestions
        );

        return this;
    }

    public boolean isCitySuggestionsScrolledToBottom() {
        return (Boolean) js.executeScript(
                "return arguments[0].scrollTop + arguments[0].clientHeight >= " +
                        "arguments[0].scrollHeight - 2;",
                citySuggestions
        );
    }


    // ==================== CALENDAR ====================

    @FindBy(id = "dates")
    WebElement datesInput;

    @FindBy(css = "button.rdrNextButton")
    WebElement nextMonthButton;

    @FindBy(css = ".rdrMonthPicker select")
    WebElement monthPicker;

    @FindBy(css = ".rdrYearPicker select")
    WebElement yearPicker;


    public SP1 clickDatesField() {
        click(datesInput);
        return this;
    }

    public String getDatesValue() {
        return datesInput.getAttribute("value");
    }

    public SP1 selectDay(String day) {
        WebElement date = driver.findElement(
                By.xpath(
                        "//button[contains(@class,'rdrDay') and " +
                                "not(contains(@class,'rdrDayDisabled'))]" +
                                "//span[@class='rdrDayNumber']/span[text()='" +
                                day + "']"
                )
        );

        click(date);
        return this;
    }

    public SP1 selectFutureDay(int daysFromToday) {
        LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);

        selectYear(String.valueOf(futureDate.getYear()));
        selectMonth(futureDate.getMonthValue());

        String day = String.valueOf(futureDate.getDayOfMonth());

        return selectDay(day);
    }

    public boolean isPastDateDisabled() {
        WebElement pastDate = driver.findElement(
                By.cssSelector("button.rdrDay.rdrDayDisabled")
        );

        return pastDate.getAttribute("class")
                .contains("rdrDayDisabled");
    }

    public SP1 selectMonth(int month) {
        type(
                monthPicker,
                String.valueOf(month - 1)
        );

        return this;
    }

    public SP1 selectYear(String year) {
        type(yearPicker, year);
        return this;
    }

    public SP1 clickNextMonthButton() {
        click(nextMonthButton);
        return this;
    }

    public boolean isNextMonthButtonDisabled() {
        return !nextMonthButton.isEnabled();
    }


    // ---------- Calendar upper boundary ----------

    public SP1 selectMaxFutureDate() {
        LocalDate maxDate = LocalDate.now().plusYears(1);

        selectYear(String.valueOf(maxDate.getYear()));
        selectMonth(maxDate.getMonthValue());

        return selectDay(
                String.valueOf(maxDate.getDayOfMonth())
        );
    }

    public boolean isMaxFutureDateSelected() {
        LocalDate maxDate = LocalDate.now().plusYears(1);

        String expectedDate = maxDate.format(
                DateTimeFormatter.ofPattern("M/d/yyyy")
        );

        return datesInput
                .getAttribute("value")
                .contains(expectedDate);
    }

    public boolean isDateAfterMaxDisabled() {
        LocalDate dateAfterMax = LocalDate.now()
                .plusYears(1)
                .plusDays(1);

        selectYear(
                String.valueOf(dateAfterMax.getYear())
        );

        selectMonth(
                dateAfterMax.getMonthValue()
        );

        String day =
                String.valueOf(dateAfterMax.getDayOfMonth());

        return isElementPresent(
                By.xpath(
                        "//button[contains(@class,'rdrDayDisabled')]" +
                                "[.//span[@class='rdrDayNumber']" +
                                "/span[text()='" + day + "']]"
                )
        );
    }


    // ==================== SEARCH BUTTON ====================

    @FindBy(css = "button[type='submit']")
    WebElement searchButton;

    public SP1 clickSearchButton() {
        click(searchButton);
        return this;
    }

    public boolean isSearchButtonDisabled() {
        return !searchButton.isEnabled();
    }


    // ==================== SEARCH RESULTS ====================

    @FindBy(css = ".car-card")
    List<WebElement> searchResults;

    @FindBy(css = ".cars-container .car-card")
    List<WebElement> carsInContainer;

    @FindBy(css = ".car-price-value")
    List<WebElement> carPrices;


    public boolean isSearchResultPresent() {
        return !searchResults.isEmpty();
    }

    public boolean isFirstCarName(String carName) {
        String str =
                searchResults.get(0)
                        .getAttribute("textContent");

        return str.contains(carName);
    }

    public boolean isCarPricePresent() {
        return !carPrices.isEmpty();
    }

    public SP1 scrollToSearchResults() {
        scrollWithJS(searchResults.get(0));
        return this;
    }

    public boolean isSearchResultsVisibleInViewport() {
        return (Boolean) js.executeScript(
                "const rect = arguments[0].getBoundingClientRect();" +
                        "return rect.top >= 0 && " +
                        "rect.top < window.innerHeight;",
                searchResults.get(0)
        );
    }

    public boolean areCarsPresentInContainer() {
        return !carsInContainer.isEmpty();
    }

    public int getCarsCountInContainer() {
        return carsInContainer.size();
    }

    public int getSearchResultsCount() {
        return searchResults.size();
    }

    public boolean isNoCarsMessagePresent() {
        return isElementPresent(
                By.xpath(
                        "//main//*[contains(text()," +
                                "'No cars found for the selected search criteria')]"
                )
        );
    }


    // ==================== CAR DETAILS ====================

    public SP1 selectFirstCar() {
        click(searchResults.get(0));
        return this;
    }

    public boolean isCarPageOpened() {
        return driver.getCurrentUrl()
                .contains("/cars/");
    }

    public boolean isCarLoadingErrorPresent() {
        try {
            getWait(5).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//div[contains(text()," +
                                            "'Не удалось загрузить машину')]"
                            )
                    )
            );

            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean areCarDetailsPresent(String... details) {
        for (String detail : details) {

            if (!isElementPresent(
                    By.xpath(
                            "//*[normalize-space(.)='" +
                                    detail + "']"
                    )
            )) {
                return false;
            }
        }

        return true;
    }


    // ==================== PAGINATION ====================

    @FindBy(css = "button[title='Next']")
    WebElement nextPageButton;

    @FindBy(css = "button[title='Prev']")
    WebElement previousPageButton;

    @FindBy(
            xpath = "//button[@title='Prev']/following-sibling::span"
    )
    WebElement pageNumber;


    public SP1 clickNextPageButton() {
        click(nextPageButton);
        return this;
    }

    public SP1 clickPreviousPageButton() {
        click(previousPageButton);
        return this;
    }

    public String getPageNumber() {
        return pageNumber.getText();
    }

    public boolean isPreviousPageButtonDisabled() {
        return !previousPageButton.isEnabled();
    }

    public boolean isNextPageButtonDisabled() {
        return !nextPageButton.isEnabled();
    }

    public SP1 hoverOverNextButton() {
        actions.moveToElement(nextPageButton)
                .perform();

        pause(2000);

        return this;
    }

    public String getNextButtonColor() {
        return nextPageButton
                .getCssValue("color");
    }


    // ==================== ROWS PER PAGE ====================

    @FindBy(css = "select")
    WebElement rowsPerPage;

    public SP1 selectRowsPerPage(String value) {
        type(rowsPerPage, value);
        return this;
    }

    public String getRowsPerPageValue() {
        return rowsPerPage
                .getAttribute("value");
    }
}
