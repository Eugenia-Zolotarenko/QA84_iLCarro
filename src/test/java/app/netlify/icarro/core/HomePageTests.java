package app.netlify.icarro.core;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests extends TestBase {

    HomePage homePage;

    @BeforeMethod
    public void init() {
        homePage = new HomePage(driver);
    }

    @Test
    public void verifyHomePage() {

        Assert.assertTrue(
                homePage.isHomeComponentPresent(),
                "Home component is not present"
        );
    }

    @Test
    public void verifyHeader() {

        Assert.assertTrue(
                homePage.isHeaderPresent(),
                "Header is not present"
        );
    }

    @Test
    public void verifyLogo() {

        Assert.assertTrue(
                homePage.isLogoPresent(),
                "Logo is not present"
        );
    }

    @Test
    public void verifyMainHeading() {

        Assert.assertTrue(
                homePage.isMainHeadingPresent(),
                "Main heading is not present"
        );
    }

    @Test
    public void verifySearch() {

        Assert.assertTrue(
                homePage.isSearchInputPresent(),
                "Search input is not present"
        );
    }

    @Test
    public void verifyReviews() {

        Assert.assertTrue(
                homePage.isReviewsPresent(),
                "Reviews block is not present"
        );
    }

    @Test
    public void verifyFooter() {

        homePage.scrollToFooter();

        Assert.assertTrue(
                homePage.isFooterPresent(),
                "Footer is not present"
        );
    }
    @Test
    public void verifyTermOfUsePage() {

        homePage.scrollToFooter();

        homePage.clickTermOfUse();

        Assert.assertTrue(
                homePage.isTermsOfUsePageOpened(),
                "Terms of Use page is not opened"
        );
    }
    @Test
    public void verifyTermOfUseHeading() {

        homePage.scrollToFooter();

        homePage.clickTermOfUse();

        Assert.assertFalse(
                homePage.getTermsOfUseHeading().isEmpty(),
                "Terms of Use heading is empty"
        );
    }
    @Test
    public void verifyTermOfUseContent() {

        homePage.scrollToFooter();

        homePage.clickTermOfUse();

        Assert.assertTrue(
                homePage.isTermsOfUseContentPresent(),
                "Terms of Use content is not present"
        );

        Assert.assertTrue(
                homePage.isTermsOfUseParagraphPresent(),
                "Terms of Use paragraphs are not present"
        );
    }
    // ==========================================
    // REVIEWS TESTS
    // ==========================================

    @Test
    public void verifyReviewsBlock() {
        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.isReviewsPresent(),
                "Reviews block is not present"
        );

        Assert.assertTrue(
                homePage.isReviewsHeadingPresent(),
                "Reviews heading is not present"
        );

        Assert.assertTrue(
                homePage.areReviewCardsPresent(),
                "Review cards are not present"
        );

        Assert.assertTrue(
                homePage.areAllReviewCardsPresent(),
                "Not all 6 review cards are present"
        );
    }
    @Test
    public void verifyReviewsHeading() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.isReviewsHeadingPresent(),
                "Reviews heading is not present"
        );
    }
    @Test
    public void verifyReviewElements() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.areReviewElementsPresent(),
                "Review elements are not present"
        );
    }
    @Test
    public void verifyReviewText() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.areReviewTextsPresent(),
                "Review texts are not present"
        );

        Assert.assertTrue(
                homePage.isReviewTextReadable(),
                "Review text is not readable"
        );
    }
    @Test
    public void verifyReviewerInformation() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.areReviewerNamesPresent(),
                "Reviewer information is not present"
        );

        Assert.assertTrue(
                homePage.isReviewerInformationReadable(),
                "Reviewer information is not readable"
        );
    }
    @Test
    public void verifyReviewImages() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.areReviewImagesLoaded(),
                "Review images are not loaded correctly"
        );
    }
    @Test
    public void verifyReviewsPosition() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.areReviewsInsidePage(),
                "Reviews block is outside the page boundaries"
        );
    }
    @Test
    public void verifyReviewsAfterScroll() {

        homePage.scrollToReviews();

        Assert.assertTrue(
                homePage.isReviewsBlockVisible(),
                "Reviews block is not visible"
        );

        homePage.scrollSlightlyAroundReviews();

        Assert.assertTrue(
                homePage.isReviewsBlockVisible(),
                "Reviews block disappeared after scrolling"
        );
    }
    // ==========================================
// NEVER MISTAKEN TESTS
// ==========================================

    @Test
    public void verifyNeverMistakenSection() {

        homePage.scrollToNeverMistakenSection();

        Assert.assertTrue(
                homePage.isNeverMistakenSectionPresent(),
                "NEVER MISTAKEN section is not present"
        );

        Assert.assertTrue(
                homePage.isNeverMistakenHeadingPresent(),
                "NEVER MISTAKEN heading is not present"
        );
    }

}

