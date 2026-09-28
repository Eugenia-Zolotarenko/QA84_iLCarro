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

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "city")
    WebElement cityInput;

    public SearchPage enterCity(String city) {
        type(cityInput, city);
        return this;
    }
        public SearchPage clickCityField() {
            click(cityInput);
            return this;
        }


    @FindBy(id = "dates")
    WebElement datesInput;

    public SearchPage clickDatesField() {
        click(datesInput);
        return this;
    }

    public SearchPage selectDay(String day) {
        WebElement date = driver.findElement(
                By.xpath("//button[contains(@class,'rdrDay') and " +
                        "not(contains(@class,'rdrDayDisabled'))]" +
                        "//span[@class='rdrDayNumber']/span[text()='" + day + "']")
        );
        click(date);
        return this;
    }

    @FindBy(css = "button[type='submit']")
    WebElement searchButton;

    public SearchPage clickSearchButton() {
        click(searchButton);
        return this;
    }

    @FindBy(css = ".car-card")
    List<WebElement> searchResults;

    public boolean isFirstCarName(String carName) {
        String str = searchResults.get(0).getAttribute("textContent");
        return str.contains(carName);
    }

    public boolean isSearchResultPresent() {
        return !searchResults.isEmpty();
    }

    public SearchPage scrollToSearchResults() {
        scrollWithJS(searchResults.get(0));
        return this;
    }
    public boolean isSearchResultsVisibleInViewport() {//находиться ли верхняя карточка в видимой части экрана
        return (Boolean) js.executeScript(
                "const rect = arguments[0].getBoundingClientRect();" +
                        "return rect.top >= 0 && rect.top < window.innerHeight;",
                searchResults.get(0)
        );
    }

    public SearchPage selectFirstCar() {
        click(searchResults.get(0));
        return this;
    }

    @FindBy(css = ".car-price-value")
    List<WebElement> carPrices;

    public boolean isCarPricePresent() {
        return !carPrices.isEmpty();
    }

    @FindBy(css = "button[title='Next']")
    WebElement nextPageButton;

    public SearchPage clickNextPageButton() {
        click(nextPageButton);
        return this;
    }
    public boolean isNextMonthButtonDisabled() {
        return !nextMonthButton.isEnabled();
    }

    @FindBy(xpath = "//button[@title='Prev']/following-sibling::span")
    WebElement pageNumber;

    public String getPageNumber() {
        return pageNumber.getText();
    }

    @FindBy(css = "button[title='Prev']")
    WebElement previousPageButton;

    public SearchPage clickPreviousPageButton() {
        click(previousPageButton);
        return this;
    }

    @FindBy(css = "select")
    WebElement rowsPerPage;

    public SearchPage selectRowsPerPage(String value) {
        type(rowsPerPage, value);
        return this;
    }

    public String getRowsPerPageValue() {
        return rowsPerPage.getAttribute("value");
    }

    public int getSearchResultsCount() {
        return searchResults.size();
    }

    public boolean isCarPageOpened() {
        return driver.getCurrentUrl().contains("/cars/");
    }

    public boolean isCarLoadingErrorPresent() {
        try {
            getWait(5).until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(),'Не удалось загрузить машину')]")
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public SearchPage selectCityFromSuggestions(String city) {
        WebElement cityOption = driver.findElement(
                By.cssSelector("[data-testid='city-option'][data-value='" + city + "']")
        );
        click(cityOption);
        return this;
    }


    public boolean isPastDateDisabled() {
        WebElement pastDate = driver.findElement(
                By.cssSelector("button.rdrDay.rdrDayDisabled"));

        return pastDate.getAttribute("class").contains("rdrDayDisabled");
    }

    public String getDatesValue() {
        return datesInput.getAttribute("value");
    }

    public boolean isSearchButtonDisabled() {
        return !searchButton.isEnabled();
    }


    public SearchPage selectFutureDay(int daysFromToday) {
        LocalDate futureDate = LocalDate.now().plusDays(daysFromToday);

        selectYear(String.valueOf(futureDate.getYear()));
        selectMonth(futureDate.getMonthValue());

        String day = String.valueOf(futureDate.getDayOfMonth());

        return selectDay(day);
    }


    public boolean areCarDetailsPresent(String... details) {
        for (String detail : details) {
            if (!isElementPresent(
                    By.xpath("//*[normalize-space(.)='" + detail + "']")
            )) {
                return false;
            }
        }
        return true;
    }

    @FindBy(css = ".cars-container .car-card")
    List<WebElement> carsInContainer;

    public boolean areCarsPresentInContainer() {
        return !carsInContainer.isEmpty();
    }

    public int getCarsCountInContainer() {
        return carsInContainer.size();
    }

    public String getCityValue() {
        return cityInput.getAttribute("value");
    }

    public boolean isNoCarsMessagePresent() {
        return isElementPresent(
                By.xpath("//main//*[contains(text()," +
                        "'No cars found for the selected search criteria')]")
        );
    }

    public boolean isPreviousPageButtonDisabled() {
        return !previousPageButton.isEnabled();
    }

    public boolean isNextPageButtonDisabled() {
        return !nextPageButton.isEnabled();
    }

    public SearchPage hoverOverNextButton() {
        actions.moveToElement(nextPageButton).perform();
        pause(2000);
        return this;
    }

    public String getNextButtonColor() {
        return nextPageButton.getCssValue("color");
    }


    @FindBy(css = "button.rdrNextButton")
    WebElement nextMonthButton;

    public SearchPage clickNextMonthButton() {
        click(nextMonthButton);
        return this;
    }

    @FindBy(css = ".rdrMonthPicker select")
    WebElement monthPicker;

    public SearchPage selectMonth(int month) {
        type(monthPicker, String.valueOf(month - 1));
        return this;
    }

    @FindBy(css = ".rdrYearPicker select")
    WebElement yearPicker;

    public SearchPage selectYear(String year) {
        type(yearPicker, year);
        return this;
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
    public SearchPage scrollCitySuggestionsToBottom() {
        js.executeScript(
                "arguments[0].scrollTop = arguments[0].scrollHeight;",
                citySuggestions
        );
        return this;
    }
    public boolean isCitySuggestionsScrolledToBottom() {
        return (Boolean) js.executeScript(
                "return arguments[0].scrollTop + arguments[0].clientHeight >= arguments[0].scrollHeight - 2;",
                citySuggestions);
    }



    public SearchPage selectMaxFutureDate() {
        LocalDate maxDate = LocalDate.now().plusYears(1);

        selectYear(String.valueOf(maxDate.getYear()));
        selectMonth(maxDate.getMonthValue());

        return selectDay(String.valueOf(maxDate.getDayOfMonth()));
    }

    public boolean isMaxFutureDateSelected() {
        LocalDate maxDate = LocalDate.now().plusYears(1);

        String expectedDate = maxDate.format(
                DateTimeFormatter.ofPattern("M/d/yyyy")
        );

        return datesInput.getAttribute("value").contains(expectedDate);
    }

    public boolean isDateAfterMaxDisabled() {
        LocalDate dateAfterMax = LocalDate.now()
                .plusYears(1)
                .plusDays(1);

        selectYear(String.valueOf(dateAfterMax.getYear()));
        selectMonth(dateAfterMax.getMonthValue());

        String day = String.valueOf(dateAfterMax.getDayOfMonth());

        return isElementPresent(
                By.xpath("//button[contains(@class,'rdrDayDisabled')]" +
                        "[.//span[@class='rdrDayNumber']/span[text()='" + day + "']]")
        );
    }
}
