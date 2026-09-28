package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.FooterPage;
import app.netlify.icarro.pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class FooterPageTests extends TestBase {

    FooterPage footer;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        footer = new HomePage(driver).getFooterPage();
    }


    @Test
    public void verifySocialMediaLinksPositiveTest() {

        Assert.assertTrue(
                footer.areSocialMediaLinksPresent()
        );
    }


    @Test
    public void verifyFooterDisplayPositiveTest() {

        Assert.assertTrue(
                footer.isFooterDisplayed()
        );
    }


    @Test
    public void verifySocialMediaIconsDisplayPositiveTest() {

        Assert.assertTrue(
                footer.areSocialMediaIconsDisplayed()
        );
    }


    @Test
    public void verifyFacebookIconPositiveTest() {

        Assert.assertTrue(
                footer.isFacebookIconDisplayed()
        );
    }


    @Test
    public void verifyFooterLogoLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterLogoLinkPresent()
        );
    }


    @Test
    public void verifyFooterLogoImagePositiveTest() {

        Assert.assertTrue(
                footer.isFooterLogoImagePresent()
        );
    }


    @Test
    public void verifyFooterContactInformationPositiveTest() {

        Assert.assertTrue(
                footer.isContactInformationDisplayed()
        );

        Assert.assertTrue(
                footer.isTelephoneDisplayed()
        );

        Assert.assertTrue(
                footer.isFooterAddressDisplayed()
        );
    }


    @Test
    public void verifyTopCitiesLinksPositiveTest() {

        Assert.assertTrue(
                footer.areTopCitiesLinksPresent()
        );
    }


    @Test
    public void verifyTopCitiesDisplayPositiveTest() {

        Assert.assertTrue(
                footer.isTopCitiesDisplayed()
        );

        Assert.assertTrue(
                footer.isTopCitiesTitleDisplayed()
        );
    }


    @Test
    public void verifyFooterLoginLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterLoginLinkDisplayed()
        );
    }


    @Test
    public void verifyFooterSignUpLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterSignUpLinkDisplayed()
        );
    }


    @Test
    public void verifyFooterTermsOfUseLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterTermsOfUseLinkDisplayed()
        );
    }


    @Test
    public void verifyFooterLetCarWorkLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterLetCarWorkLinkDisplayed()
        );
    }


    @Test
    public void verifyFooterSearchLinkPositiveTest() {

        Assert.assertTrue(
                footer.isFooterSearchLinkDisplayed()
        );
    }


    @Test
    public void verifyFooterMenuPositiveTest() {

        Assert.assertTrue(
                footer.isFooterMenuDisplayed()
        );
    }


    @Test
    public void verifyFooterDisplayWithBodyWidthLessThan785PositiveTest() {

        footer.setWindowWidthTo(780);

        footer.pause(1000);

        Assert.assertTrue(
                footer.isFooterDisplayed()
        );
    }


    @Test
    public void verifyFooterElementsWithBodyWidthLessThan785PositiveTest() {

        footer.setWindowWidthTo(780);

        footer.pause(1000);

        Assert.assertTrue(
                footer.isFooterLogoDisplayed()
        );

        Assert.assertTrue(
                footer.areSocialMediaIconsDisplayed()
        );

        Assert.assertTrue(
                footer.isTelephoneDisplayed()
        );
    }

    @Test
    public void verifyBrokenLinksInFooterNegativeTest() {

        SoftAssert softly = new SoftAssert();

        for (WebElement link : footer.getFooterLinks()) {

            String url = link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                footer.verifyLinks(
                        url,
                        softly
                );
            }
        }

        softly.assertAll();
    }

    @Test
    public void verifyBrokenImagesInFooterNegativeTest() {

        Assert.assertTrue(
                footer.areFooterImagesLoaded(),
                "Broken image found in Footer"
        );
    }

    @Test
    public void verifyBrokenLinksInFooterWithBodyWidthLessThan785NegativeTest() {

        footer.setWindowWidthTo(780);

        footer.pause(1000);

        SoftAssert softly = new SoftAssert();

        for (WebElement link : footer.getFooterLinks()) {

            String url = link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                footer.verifyLinks(
                        url,
                        softly
                );
            }
        }

        softly.assertAll();
    }

    @Test
    public void verifyBrokenImagesInFooterWithBodyWidthLessThan785NegativeTest() {

        footer.setWindowWidthTo(780);

        footer.pause(1000);

        Assert.assertTrue(
                footer.areFooterImagesLoaded(),
                "Broken image found in mobile Footer"
        );
    }
}