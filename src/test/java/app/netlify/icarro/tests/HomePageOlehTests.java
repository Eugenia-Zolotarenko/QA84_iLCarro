package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageOlehTests extends TestBase {

    HomePage home;

    @BeforeMethod(alwaysRun = true)
    public void initHomePage() {
        home = new HomePage(driver);
    }

// HOME PAGE TESTS //

     @Test(groups = {"smoke", "regr"})
     public void isPageTitleCorrectPositiveTest() {

     Assert.assertTrue(home.isPageTitleCorrect("Find your car now!"));
}
     @Test(groups = {"smoke", "regr"})
     public void isHomePageDisplayedPositiveTest() {

    Assert.assertTrue(home.isHomeComponentPresent());
}
    @Test(groups = {"smoke", "regr", "header"})
    public void loginLinkIsVisiblePositiveTest() {
    getSoftAssert().assertTrue( home.isYallaButtonPresent(),
            "Button Sign Up is not displayed" );
}
    @Test(groups = {"smoke", "regr"})
    public void testPageLinks() {

         home.verifyLinks( "https://icarro-v1.netlify.app/let-car-work", getSoftAssert() );
}

// HEADER TESTS

    @Test(groups = {"smoke", "regr", "header", "min"})
    public void mobileHeaderIsVisiblePositiveTest() {

        home.setWindowWidthTo(500);

        Assert.assertTrue(
                home.isMobileHeaderPresent(),
                "Mobile header is not displayed"
        );
    }

    @Test(groups = {"smoke", "regr"})
    public void verifyHomePage() {

        Assert.assertTrue(
                home.isHomeComponentPresent(),
                "Home component is not present"
        );
    }

    @Test(groups = {"smoke", "regr", "footer"})
    public void verifyFooter() {

        home.scrollToFooter();

        Assert.assertTrue(
                home.isFooterPresent(),
                "Footer is not present"
        );
    }

    @Test(groups = {"smoke", "regr", "header"})
    public void verifyLogo() {

        Assert.assertTrue(
                home.isLogoPresent(),
                "Logo is not present"
        );
    }

    @Test(groups = {"smoke", "regr"})
    public void verifyMainHeading() {

        Assert.assertTrue(
                home.isMainHeadingPresent(),
                "Main heading is not present"
        );
    }

    @Test(groups = {"smoke", "regr"})
    public void verifySearch() {

        Assert.assertTrue(
                home.isSearchInputPresent(),
                "Search input is not present"
        );
    }

    @Test(groups = {"smoke", "regr", "reviews"})
    public void verifyReviews() {

        Assert.assertTrue(
                home.isReviewsPresent(),
                "Reviews block is not present"
        );
    }

 // TERMS OF USE TESTS //

     @Test(groups = {"smoke", "regr", "terms"})
     public void verifyTermOfUsePage() {

                home.scrollToFooter(); home.clickTermOfUse();
         Assert.assertTrue( home.isTermsOfUsePageOpened(), "Terms of Use page is not opened" );
}
     @Test(groups = {"regr", "terms"})
     public void verifyTermOfUseHeading() { home.scrollToFooter(); home.clickTermOfUse();

         Assert.assertFalse( home.getTermsOfUseHeading().isEmpty(), "Terms of Use heading is empty" );
}

     @Test(groups = {"regr", "terms"})
     public void verifyTermOfUseContent() { home.scrollToFooter(); home.clickTermOfUse();

         Assert.assertTrue( home.isTermsOfUseContentPresent(), "Terms of Use content is not present" );
         Assert.assertTrue( home.isTermsOfUseParagraphPresent(), "Terms of Use paragraphs are not present" );
}
 // REVIEWS TESTS //

     @Test(groups = {"smoke", "regr", "reviews"})
     public void verifyReviewsBlock() {

                 home.scrollToReviews(); Assert.assertTrue( home.isReviewsPresent(),
            "Reviews block is not present" );
         Assert.assertTrue( home.isReviewsHeadingPresent(),
            "Reviews heading is not present" );
         Assert.assertTrue( home.areReviewCardsPresent(), "Review cards are not present" );
         Assert.assertTrue( home.areAllReviewCardsPresent(), "Not all 6 review cards are present" );
}
     @Test(groups = {"regr", "reviews"})
     public void verifyReviewsHeading() {

                 home.scrollToReviews(); Assert.assertTrue( home.isReviewsHeadingPresent(),
            "Reviews heading is not present" );
}

     @Test(groups = {"regr", "reviews"})
     public void verifyReviewElements() { home.scrollToReviews();

         Assert.assertTrue( home.areReviewElementsPresent(), "Review elements are not present" );
}

     @Test(groups = {"regr", "reviews"})
     public void verifyReviewText() {
               home.scrollToReviews();

         Assert.assertTrue( home.areReviewTextsPresent(), "Review texts are not present" );
         Assert.assertTrue( home.isReviewTextReadable(), "Review text is not readable" );
}

     @Test(groups = {"regr", "reviews"})
     public void verifyReviewerInformation() {

               home.scrollToReviews();

         Assert.assertTrue( home.areReviewerNamesPresent(), "Reviewer information is not present" );
         Assert.assertTrue( home.isReviewerInformationReadable(), "Reviewer information is not readable" );
}

     @Test(groups = {"regr", "reviews"})
     public void verifyReviewImages() { home.scrollToReviews();

         Assert.assertTrue( home.areReviewImagesLoaded(), "Review images are not loaded correctly" );

}

     @Test(groups = {"regr", "reviews"}) public void verifyReviewsPosition() {

               home.scrollToReviews();
               Assert.assertTrue( home.areReviewsInsidePage(), "Reviews block is outside the page boundaries" );
}

     @Test(groups = {"regr", "reviews"})
     public void verifyReviewsAfterScroll() {

               home.scrollToReviews();
         Assert.assertTrue( home.isReviewsBlockVisible(), "Reviews block is not visible" );
}

        // NEVER MISTAKEN TESTS //

    @Test(groups = {"smoke", "regr", "neverMistaken"})
    public void verifyNeverMistakenSection() {

               home.scrollToNeverMistakenSection();

    Assert.assertTrue( home.isNeverMistakenSectionPresent(), "NEVER MISTAKEN section is not present" );
    }

}