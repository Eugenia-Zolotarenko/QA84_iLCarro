package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // =========================
    // HOME PAGE
    // =========================

    public boolean isHomeComponentPresent() {
        return isElementPresent(By.cssSelector(".search-card"));
    }
    public boolean isLogoPresent() {
        return isElementPresent(By.cssSelector("header img"));
    }

    public boolean isYallaButtonPresent() {
        return isElementPresent(By.cssSelector("button[type='submit']"));
    }
    public boolean isMainHeadingPresent() {
        return isElementPresent(By.cssSelector("h1"));
    }
    public boolean isSearchInputPresent() {
        return isElementPresent(By.cssSelector("input"));
    }
    public void scrollToFooter() {
        WebElement footer = driver.findElement(By.cssSelector("footer"));
        scrollWithJS(footer);
    }
    public boolean isFooterPresent() {
        return isElementPresent(By.cssSelector("footer"));
    }
    public void clickTermOfUse() {
        WebElement link = driver.findElement(
                By.xpath("//a[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms of use')]")
        );

        clickWithJS(link);
    }
    public boolean isTermsOfUsePageOpened() {
        return driver.getCurrentUrl().contains("terms");
    }
    public String getTermsOfUseHeading() {
        return driver.findElement(
                By.cssSelector("h1")
        ).getText();
    }
    public boolean isTermsOfUseContentPresent() {
        return isElementPresent(By.cssSelector("main"));
    }
    public boolean isTermsOfUseParagraphPresent() {
        return !driver.findElements(
                By.cssSelector("main p")
        ).isEmpty();
    }

    public boolean isMobileHeaderPresent() {
        return driver.findElement(
                By.cssSelector(".mobile-header")
        ).isDisplayed();
    }

    // =========================
    // NEVER MISTAKEN
    // =========================

    public boolean isNeverMistakenBlockDisplayed() {
        return !driver.findElements(
                By.xpath("//*[contains(text(),'NEVER MISTAKEN FOR ANYTHING ELSE')]")
        ).isEmpty();
    }

    public String getNeverMistakenText() {
        WebElement element = driver.findElement(
                By.xpath("//*[contains(text(),'NEVER MISTAKEN FOR ANYTHING ELSE')]")
        );

        return element.getText();
    }

    public void scrollToNeverMistakenSection() {
        WebElement element = driver.findElement(
                By.xpath("//*[contains(text(),'NEVER MISTAKEN FOR ANYTHING ELSE')]")
        );

        scrollWithJS(element);
    }

    public boolean isNeverMistakenSectionPresent() {
        return isNeverMistakenBlockDisplayed();
    }

    public boolean isNeverMistakenHeadingPresent() {
        return !getNeverMistakenText().isEmpty();
    }

    // =========================
    // REVIEWS
    // =========================

    public boolean isReviewsBlockDisplayed() {
        return !driver.findElements(
                By.xpath("//*[contains(normalize-space(),'Reviews')]")
        ).isEmpty();
    }

    public String getReviewsText() {
        WebElement element = driver.findElement(
                By.xpath("//*[contains(normalize-space(),'Reviews')]")
        );

        return element.getText();
    }

    public void scrollToReviews() {
        WebElement element = driver.findElement(
                By.xpath("//*[contains(normalize-space(),'Reviews')]")
        );

        scrollWithJS(element);
    }

    public boolean isReviewsPresent() {
        return isReviewsBlockDisplayed();
    }

    public boolean isReviewsHeadingPresent() {
        return isReviewsBlockDisplayed();
    }

    public boolean areReviewCardsPresent() {
        return !driver.findElements(
                By.cssSelector(".review-card")
        ).isEmpty();
    }

    public boolean areAllReviewCardsPresent() {
        return driver.findElements(
                By.cssSelector(".review-card")
        ).size() == 6;
    }

    public boolean areReviewElementsPresent() {
        return areReviewCardsPresent();
    }

    public boolean areReviewTextsPresent() {
        return areReviewCardsPresent();
    }

    public boolean isReviewTextReadable() {
        return areReviewCardsPresent();
    }

    public boolean areReviewerNamesPresent() {
        return areReviewCardsPresent();
    }

    public boolean isReviewerInformationReadable() {
        return areReviewCardsPresent();
    }

    public boolean areReviewImagesLoaded() {
        return !driver.findElements(
                By.cssSelector(".review-card img")
        ).isEmpty();
    }

    public boolean areReviewsInsidePage() {
        return isReviewsBlockDisplayed();
    }

    public boolean isReviewsBlockVisible() {
        return isReviewsBlockDisplayed();
    }

    public void scrollSlightlyAroundReviews() {
        js.executeScript("window.scrollBy(0, 300);");
    }

    // =========================
    // LET CAR WORK
    // =========================

    public LetCarWorkPage getLetCarWorkPage() {

        WebElement link = driver.findElement(
                By.cssSelector("a[href$='/let-car-work']")
        );

        clickWithJS(link);

        return new LetCarWorkPage(driver);
    }
}