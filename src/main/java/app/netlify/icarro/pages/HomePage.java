package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public boolean isHeaderPresent() {
        return isElementPresent(By.cssSelector(".header"));
    }
    public boolean isLogoPresent() {
        return isElementPresent(
                By.cssSelector(".header .logo img")
        );
    }
    public boolean isMainHeadingPresent() {
        return isElementPresent(By.tagName("h1"));
    }
    public boolean isSearchInputPresent() {
        return isElementPresent(By.cssSelector("input"));
    }
    public boolean isNeverMistakenSectionPresent() {
        return driver.findElements(
                By.cssSelector(".top-banner")
        ).size() > 0;
    }
    public void scrollToNeverMistakenSection() {
        WebElement section = driver.findElement(
                By.cssSelector(".top-banner")
        );

        scrollWithJS(section);
    }
    public boolean isReviewsPresent() {
        return isElementPresent(
                By.cssSelector(".feedback-section")
        );
    }
    public void scrollToFooter() {
        js.executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }
    public boolean isFooterPresent() {
        return isElementPresent(By.cssSelector(".footer-container"));
    }
    public void clickTermOfUse() {
        WebElement termOfUse = driver.findElement(
                By.cssSelector("a[href='/terms-of-use']")
        );

        scrollWithJS(termOfUse);
        clickWithJS(termOfUse);
    }
    public boolean isTermsOfUsePageOpened() {
        return driver.findElement(
                By.cssSelector(".terms-container h1.title")
        ).isDisplayed();
    }
    public String getTermsOfUseHeading() {
        return driver.findElement(
                By.cssSelector(".terms-container h1.title")
        ).getText().trim();
    }
    public boolean isTermsOfUseContentPresent() {
        return isElementPresent(By.cssSelector(".terms-container"));
    }
    public boolean isTermsOfUseParagraphPresent() {
        return !driver.findElements(
                By.cssSelector(".terms-container p")
        ).isEmpty();
    }
    public void scrollToReviews() {
        WebElement reviews = driver.findElement(
                By.cssSelector(".feedback-section")
        );

        scrollWithJS(reviews);
    }

    public boolean isReviewsHeadingPresent() {
        WebElement heading = driver.findElement(
                By.cssSelector(".feedback-section .feedback-title")
        );

        return heading.isDisplayed()
                && !heading.getText().trim().isEmpty();
    }

    public boolean areReviewCardsPresent() {
        return driver.findElements(
                By.cssSelector(".feedback-card")
        ).size() > 0;
    }

    public boolean areAllReviewCardsPresent() {
        return driver.findElements(
                By.cssSelector(".feedback-card")
        ).size() == 6;
    }




    public boolean areReviewElementsPresent() {
        return !driver.findElements(
                By.cssSelector(".feedback-card")
        ).isEmpty();
    }

    public boolean areReviewTextsPresent() {
        return !driver.findElements(
                By.cssSelector(".feedback-card .feedback-text")
        ).isEmpty();
    }

    public boolean areReviewerNamesPresent() {
        return !driver.findElements(
                By.cssSelector(".feedback-card .feedback-name")
        ).isEmpty();
    }

    public boolean areReviewImagesLoaded() {

        return driver.findElements(
                By.cssSelector(".feedback-card")
        ).size() == 6;
    }

    public boolean isReviewTextReadable() {
        for (WebElement review :
                driver.findElements(
                        By.cssSelector(".feedback-card .feedback-text")
                )) {

            if (!review.isDisplayed()) {
                return false;
            }

            if (review.getText().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean isReviewerInformationReadable() {
        for (WebElement reviewer :
                driver.findElements(
                        By.cssSelector(".feedback-card .feedback-name")
                )) {

            if (!reviewer.isDisplayed()) {
                return false;
            }

            if (reviewer.getText().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }



    public boolean areReviewsInsidePage() {
        WebElement reviews = driver.findElement(
                By.cssSelector(".feedback-section")
        );

        return (Boolean) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return rect.left >= 0 && " +
                        "rect.right <= window.innerWidth && " +
                        "rect.top < window.innerHeight && " +
                        "rect.bottom > 0;",
                reviews
        );
    }
    public boolean isReviewsBlockVisible() {
        WebElement reviews = driver.findElement(
                By.cssSelector(".feedback-section")
        );

        return reviews.isDisplayed();
    }
    public void scrollSlightlyAroundReviews() {
        js.executeScript(
                "window.scrollBy(0, 300);"
        );
    }



    public boolean isNeverMistakenHeadingPresent() {
        WebElement heading = driver.findElement(
                By.cssSelector(".top-banner .banner-left h1")
        );

        return heading.isDisplayed()
                && !heading.getText().trim().isEmpty();
    }
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomeComponentPresent(){
      return  isElementPresent(By.cssSelector(".search-card"));
    }

    public LetCarWorkPage getLetCarWorkPage() {
        WebElement link = driver.findElement(By.cssSelector("a[href$='/let-car-work']"));
        clickWithJS(link);
        return new LetCarWorkPage(driver);
    }

    public boolean isYallaButtonPresent(){
        return  isElementPresent(By.cssSelector("button[type='submit']"));
    }

    public boolean isMobileHeaderPresent(){
        return  driver.findElement(By.cssSelector(".mobile-header")).isDisplayed();
    }


}
