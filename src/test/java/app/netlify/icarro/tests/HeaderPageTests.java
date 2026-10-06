package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.model.MainMenu;
import app.netlify.icarro.model.MenuItemData;
import app.netlify.icarro.pages.HeaderPage;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LoginPage;
import app.netlify.icarro.utils.LoginDataProvider;
import app.netlify.icarro.utils.MenuDataProvider;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class HeaderPageTests extends TestBase {

    HeaderPage header;

    private final MainMenu expectedMenu =
            new MainMenu(
                    "Log in",
                    "Sign up",
                    "Terms of use",
                    "Let car work",
                    "Search"
            );


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {

        super.setUp(method, p);

        header = new HomePage(driver).getHeaderPage();
    }

    @Test(groups = {"regr", "positive"})
    public void verifyHeaderLogoPositiveTest() {

        getSoftAssert().assertTrue(
                header.isHeaderLogoDisplayed(),
                "Header logo is not displayed"
        );

        getSoftAssert().assertNotNull(
                header.getHeaderLogoHref(),
                "Header logo href is null"
        );

        getSoftAssert().assertFalse(
                header.getHeaderLogoHref().isEmpty(),
                "Header logo href is empty"
        );

        getSoftAssert().assertNotNull(
                header.getHeaderLogoSrc(),
                "Header logo src is null"
        );

        getSoftAssert().assertFalse(
                header.getHeaderLogoSrc().isEmpty(),
                "Header logo src is empty"
        );
    }

    @Test(groups = {"regr", "positive"})
    public void verifyHeaderMenuPositiveTest() {

        List<String> actualMenu =
                header.getHeaderMenuTexts();

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getSearch()),
                "Search is absent in Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getAddCar()),
                "Let car work is absent in Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getTerms()),
                "Terms of use is absent in Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getSignUp()),
                "Sign up is absent in Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getLogin()),
                "Log in is absent in Header menu"
        );
    }

    @Test(
            groups = {"regr", "positive"},
            dataProvider = "headerMenuData",
            dataProviderClass = MenuDataProvider.class
    )
    public void verifyHeaderMenuNavigationPositiveTest(
            MenuItemData data) {

        getSoftAssert().assertTrue(
                header.isMenuItemDisplayed(data.getText()),
                data.getText() + " is not displayed"
        );

        getSoftAssert().assertTrue(
                header.isMenuItemEnabled(data.getText()),
                data.getText() + " is not enabled"
        );

        header.clickMenuItem(data.getText());

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(data.getExpectedUrlPart()),
                "Incorrect transition after click on "
                        + data.getText()
        );
    }

    @Test(
            groups = {"regr", "positive"},
            dataProvider = "headerMenuData",
            dataProviderClass = MenuDataProvider.class
    )
    public void verifyHeaderMenuHoverPositiveTest(
            MenuItemData data) {

        String colorBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "color"
                );

        String backgroundBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "background-color"
                );

        String decorationBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "text-decoration"
                );

        header.hoverMenuItem(data.getText());
        header.pause(500);

        String colorAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "color"
                );

        String backgroundAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "background-color"
                );

        String decorationAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "text-decoration"
                );

        System.out.println(
                "Menu item: " + data.getText()
        );

        System.out.println(
                "Color before: " + colorBefore
                        + " | Color after: " + colorAfter
        );

        System.out.println(
                "Background before: " + backgroundBefore
                        + " | Background after: " + backgroundAfter
        );

        System.out.println(
                "Decoration before: " + decorationBefore
                        + " | Decoration after: " + decorationAfter
        );

        boolean hoverChanged =
                !colorBefore.equals(colorAfter)
                        || !backgroundBefore.equals(backgroundAfter)
                        || !decorationBefore.equals(decorationAfter);

        Assert.assertTrue(
                hoverChanged,
                "Hover style did not change for "
                        + data.getText()
        );
    }

    @Test(groups = {"regr", "positive"})
    public void verifyHeaderDisplayWithBodyWidthLessThan785PositiveTest() {

        header.setWindowWidthTo(780);
        header.pause(1000);

        getSoftAssert().assertTrue(
                header.isMobileHeaderDisplayed(),
                "Mobile Header is not displayed"
        );

        getSoftAssert().assertTrue(
                header.isMobileHeaderLogoDisplayed(),
                "Mobile Header logo is not displayed"
        );

        getSoftAssert().assertTrue(
                header.isHamburgerButtonDisplayed(),
                "Hamburger button is not displayed"
        );
    }

    @Test(groups = {"regr", "positive"})
    public void verifyHeaderMenuWithBodyWidthLessThan785PositiveTest() {

        header.setWindowWidthTo(780);
        header.pause(1000);

        header.clickHamburgerButton();
        header.pause(1000);

        List<String> actualMenu =
                header.getMobileMenuTexts();

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getSearch()),
                "Search is absent in mobile Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getAddCar()),
                "Let car work is absent in mobile Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getTerms()),
                "Terms of use is absent in mobile Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getSignUp()),
                "Sign up is absent in mobile Header menu"
        );

        getSoftAssert().assertTrue(
                actualMenu.contains(expectedMenu.getLogin()),
                "Log in is absent in mobile Header menu"
        );
    }

    @Test(
            groups = {"regr", "positive"},
            dataProvider = "headerMenuData",
            dataProviderClass = MenuDataProvider.class
    )
    public void verifyHeaderMenuNavigationWithBodyWidthLessThan785PositiveTest(
            MenuItemData data) {

        header.setWindowWidthTo(780);
        header.pause(1000);

        header.clickHamburgerButton();
        header.pause(1000);

        getSoftAssert().assertTrue(
                header.isMenuItemDisplayed(data.getText()),
                data.getText()
                        + " is not displayed in mobile Header"
        );

        getSoftAssert().assertTrue(
                header.isMenuItemEnabled(data.getText()),
                data.getText()
                        + " is not enabled in mobile Header"
        );

        header.clickMenuItem(data.getText());

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains(data.getExpectedUrlPart()),
                "Incorrect mobile transition after click on "
                        + data.getText()
        );
    }

    @Test(
            groups = {"regr", "positive"},
            dataProvider = "headerMenuData",
            dataProviderClass = MenuDataProvider.class
    )
    public void verifyHeaderMenuHoverWithBodyWidthLessThan785PositiveTest(
            MenuItemData data) {

        header.setWindowWidthTo(780);
        header.pause(1000);

        getSoftAssert().assertTrue(
                header.isHamburgerButtonDisplayed(),
                "Hamburger button is not displayed"
        );

        getSoftAssert().assertTrue(
                header.isHamburgerButtonEnabled(),
                "Hamburger button is not enabled"
        );

        header.clickHamburgerButton();
        header.pause(1000);

        getSoftAssert().assertTrue(
                header.isMenuItemDisplayed(data.getText()),
                data.getText()
                        + " is not displayed in mobile Header"
        );

        getSoftAssert().assertTrue(
                header.isMenuItemEnabled(data.getText()),
                data.getText()
                        + " is not enabled in mobile Header"
        );

        String colorBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "color"
                );

        String backgroundBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "background-color"
                );

        String decorationBefore =
                header.getMenuItemCssValue(
                        data.getText(),
                        "text-decoration"
                );

        header.hoverMenuItem(data.getText());
        header.pause(500);

        String colorAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "color"
                );

        String backgroundAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "background-color"
                );

        String decorationAfter =
                header.getMenuItemCssValue(
                        data.getText(),
                        "text-decoration"
                );

        System.out.println(
                "Mobile menu item: " + data.getText()
        );

        System.out.println(
                "Color before: " + colorBefore
                        + " | Color after: " + colorAfter
        );

        System.out.println(
                "Background before: " + backgroundBefore
                        + " | Background after: " + backgroundAfter
        );

        System.out.println(
                "Decoration before: " + decorationBefore
                        + " | Decoration after: " + decorationAfter
        );

        boolean hoverChanged =
                !colorBefore.equals(colorAfter)
                        || !backgroundBefore.equals(backgroundAfter)
                        || !decorationBefore.equals(decorationAfter);

        Assert.assertTrue(
                hoverChanged,
                "Hover style did not change for mobile Header item: "
                        + data.getText()
        );
    }

    @Test(groups = {"regr", "positive"})
    public void verifyHeaderMenuAfterLoginWithBodyWidthLessThan785PositiveTest() {

        LoginPage login =
                new HomePage(driver).getLoginPage();

        login.login(
                LoginDataProvider.validUser()
        );

        login.clickOkButton();

        header.setWindowWidthTo(780);
        header.pause(1000);

        header.clickHamburgerButton();
        header.pause(1000);

        getSoftAssert().assertTrue(
                header.isMobileHeaderLogoDisplayed(),
                "Mobile Header logo is not displayed"
        );

        getSoftAssert().assertTrue(
                header.isLogOutDisplayed(),
                "Log out is not displayed after login"
        );
    }

    @Test(groups = {"regr", "negative"})
    public void verifyBrokenLinksInHeaderNegativeTest() {

        for (WebElement link : header.getHeaderLinks()) {

            String url =
                    link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                header.verifyLinks(
                        url,
                        getSoftAssert()
                );
            }
        }
    }

    @Test(groups = {"regr", "negative"})
    public void verifyBrokenImagesInHeaderNegativeTest() {

        header.pause(1000);

        Assert.assertTrue(
                header.isHeaderImageLoaded(),
                "Broken image found in Header"
        );
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenLinksInHeaderWithBodyWidthLessThan785NegativeTest() {

        header.setWindowWidthTo(780);
        header.pause(1000);

        header.clickHamburgerButton();
        header.pause(1000);

        for (WebElement link : header.getHeaderLinks()) {

            String url =
                    link.getAttribute("href");

            if (url != null
                    && !url.isEmpty()
                    && (url.startsWith("http://")
                    || url.startsWith("https://"))) {

                header.verifyLinks(
                        url,
                        getSoftAssert()
                );
            }
        }
    }


    @Test(groups = {"regr", "negative"})
    public void verifyBrokenImagesInHeaderWithBodyWidthLessThan785NegativeTest() {

        header.setWindowWidthTo(780);
        header.pause(1000);

        Assert.assertTrue(
                header.isMobileHeaderImageLoaded(),
                "Broken image found in mobile Header"
        );
    }
}