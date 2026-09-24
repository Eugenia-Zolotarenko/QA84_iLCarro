package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.util.List;

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver)
    {
        super(driver);
    }
    @FindBy(id = "city")
    WebElement cityInput;

    public SearchPage enterCity(String city) {
        type(cityInput, city);
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

    public String getFirstCarName() {
        return searchResults.get(0).getAttribute("aria-label");
    }

    public boolean isSearchResultPresent() {
        return !searchResults.isEmpty();
    }
    public SearchPage scrollToSearchResults() {
        scrollWithJS(searchResults.get(0));
        return this;
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
    public SearchPage selectCityFromDropdown(String city) {
        WebElement cityOption = driver.findElement(
                By.xpath("//*[normalize-space()='" + city + "']")
        );
        click(cityOption);
        return this;
    }
    public boolean isCityPresentInDropdown(String city) {
        return !driver.findElements(
                By.xpath("//*[@data-testid='city-autocomplete']//*[normalize-space()='" + city + "']")
        ).isEmpty();
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

}
