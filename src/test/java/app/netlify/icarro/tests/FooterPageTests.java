package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.MenuItemData;
import app.netlify.icarro.pages.FooterPage;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.utils.MenuDataProvider;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class FooterPageTests extends TestBase {

    FooterPage footer;


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        footer = new HomePage(driver)
                .getFooterPage();
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterDisplayPositiveTest() {

        Assert.assertTrue(
                footer.isFooterDisplayed(),
                "Footer is not displayed"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterLogoPositiveTest() {

        getSoftAssert().assertTrue(
                footer.isFooterLogoDisplayed(),
                "Footer logo is not displayed"
        );

        getSoftAssert().assertNotNull(
                footer.getFooterLogoHref(),
                "Footer logo href is null"
        );

        getSoftAssert().assertFalse(
                footer.getFooterLogoHref().isEmpty(),
                "Footer logo href is empty"
        );

        getSoftAssert().assertNotNull(
                footer.getFooterLogoSrc(),
                "Footer logo src is null"
        );

        getSoftAssert().assertFalse(
                footer.getFooterLogoSrc().isEmpty(),
                "Footer logo src is empty"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifySocialMediaPositiveTest() {

        getSoftAssert().assertTrue(
                footer.isSocialNetworksDisplayed(),
                "Social networks block is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.areSocialMediaLinksPresent(),
                "Social media links are absent"
        );

        getSoftAssert().assertTrue(
                footer.areSocialMediaIconsDisplayed(),
                "Social media icons are not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isFacebookIconDisplayed(),
                "Facebook icon is not displayed"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterContactInformationPositiveTest() {

        getSoftAssert().assertTrue(
                footer.isContactInformationDisplayed(),
                "Contact information is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isTelephoneDisplayed(),
                "Telephone is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isFooterAddressDisplayed(),
                "Footer address is not displayed"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyTopCitiesPositiveTest() {

        getSoftAssert().assertTrue(
                footer.isTopCitiesDisplayed(),
                "Top Cities block is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isTopCitiesTitleDisplayed(),
                "Top Cities title is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.areTopCitiesLinksPresent(),
                "Top Cities links are absent"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterMenuDisplayPositiveTest() {

        Assert.assertTrue(
                footer.isFooterMenuDisplayed(),
                "Footer menu is not displayed"
        );
    }


    @Test(
            groups = {"regr", "positive"},
            dataProvider = "footerMenuData",
            dataProviderClass = MenuDataProvider.class
    )
    public void verifyFooterMenuNavigationPositiveTest(
            MenuItemData data) {

        getSoftAssert().assertTrue(
                footer.isMenuItemDisplayed(data.getText()),
                data.getText()
                        + " is not displayed in Footer menu"
        );

        getSoftAssert().assertTrue(
                footer.isMenuItemEnabled(data.getText()),
                data.getText()
                        + " is not enabled in Footer menu"
        );

        footer.clickMenuItem(data.getText());

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(data.getExpectedUrlPart()),
                "Incorrect transition after click on "
                        + data.getText()
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterDisplayWithBodyWidthLessThan785PositiveTest() {

        footer.setWindowWidthTo(780);
        footer.pause(1000);

        Assert.assertTrue(
                footer.isFooterDisplayed(),
                "Footer is not displayed with width less than 785px"
        );
    }


    @Test(groups = {"regr", "positive"})
    public void verifyFooterElementsWithBodyWidthLessThan785PositiveTest() {

        footer.setWindowWidthTo(780);
        footer.pause(1000);

        getSoftAssert().assertTrue(
                footer.isFooterLogoDisplayed(),
                "Footer logo is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isSocialNetworksDisplayed(),
                "Social networks block is not displayed"
        );

        getSoftAssert().assertTrue(
                footer.areSocialMediaIconsDisplayed(),
                "Social media icons are not displayed"
        );

        getSoftAssert().assertTrue(
                footer.isTelephoneDisplayed(),
                "Telephone is not displayed"
        );
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenLinksInFooterNegativeTest() {

        for (WebElement link : footer.getFooterLinks()) {

            String url =
                    link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                footer.verifyLinks(
                        url,
                        getSoftAssert()
                );
            }
        }
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenImagesInFooterNegativeTest() {

        footer.pause(1000);

        Assert.assertTrue(
                footer.areFooterImagesLoaded(),
                "Broken image found in Footer"
        );
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenLinksInFooterWithBodyWidthLessThan785NegativeTest() {

        footer.setWindowWidthTo(780);
        footer.pause(1000);

        for (WebElement link : footer.getFooterLinks()) {

            String url =
                    link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                footer.verifyLinks(
                        url,
                        getSoftAssert()
                );
            }
        }
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenImagesInFooterWithBodyWidthLessThan785NegativeTest() {

        footer.setWindowWidthTo(780);
        footer.pause(1000);

        Assert.assertTrue(
                footer.areFooterImagesLoaded(),
                "Broken image found in mobile Footer"
        );
    }
}