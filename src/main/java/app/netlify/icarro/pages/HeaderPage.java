package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HeaderPage extends BasePage {

    private final By header =
            By.cssSelector("div.header");

    private final By headerLogo =
            By.cssSelector("div.header a.logo");

    private final By headerLogoImage =
            By.cssSelector(
                    "div.header a.logo img[alt='IlCarro logo']"
            );

    private final By headerMenuLinks =
            By.cssSelector(
                    "div.header a.navigation-link"
            );

    private final By headerLinks =
            By.cssSelector(
                    "div.header a"
            );

    private final By mobileHeader =
            By.cssSelector(
                    "div.mobile-header"
            );

    private final By mobileHeaderLogo =
            By.cssSelector(
                    "div.mobile-header a.logo img[alt='IlCarro logo']"
            );

    private final By hamburgerButton =
            By.cssSelector(
                    "div.mobile-header button"
            );

    private final By logOutButton =
            By.cssSelector(
                    "div.header button.navigation-link.linklike"
            );


    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeaderDisplayed() {

        return isElementPresent(header);
    }


    public boolean isHeaderLogoDisplayed() {

        return isElementPresent(headerLogoImage);
    }


    public String getHeaderLogoHref() {

        return driver
                .findElement(headerLogo)
                .getAttribute("href");
    }


    public String getHeaderLogoSrc() {

        return driver
                .findElement(headerLogoImage)
                .getAttribute("src");
    }

    public List<String> getHeaderMenuTexts() {

        List<WebElement> elements =
                driver.findElements(headerMenuLinks);

        List<String> menuTexts =
                new ArrayList<>();

        for (WebElement element : elements) {

            if (element.isDisplayed()) {

                menuTexts.add(
                        element.getText().trim()
                );
            }
        }

        return menuTexts;
    }

    private WebElement findMenuItemByText(String text) {

        List<WebElement> menuItems =
                driver.findElements(headerMenuLinks);

        for (WebElement menuItem : menuItems) {

            if (menuItem.isDisplayed()
                    && menuItem.getText()
                    .trim()
                    .equals(text.trim())) {

                return menuItem;
            }
        }

        throw new RuntimeException(
                "Header menu item was not found: " + text
        );
    }

    public boolean isMenuItemDisplayed(String text) {

        return findMenuItemByText(text)
                .isDisplayed();
    }

    public boolean isMenuItemEnabled(String text) {

        return findMenuItemByText(text)
                .isEnabled();
    }

    public HeaderPage clickMenuItem(String text) {

        WebElement menuItem =
                findMenuItemByText(text);

        clickWithJS(menuItem);

        return this;
    }

    public HeaderPage hoverMenuItem(String text) {

        WebElement menuItem =
                findMenuItemByText(text);

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                menuItem
        );

        actions
                .moveToElement(menuItem)
                .pause(Duration.ofMillis(700))
                .perform();

        return this;
    }

    public String getMenuItemCssValue(
            String text,
            String cssProperty
    ) {

        WebElement menuItem =
                findMenuItemByText(text);

        Object value =
                js.executeScript(
                        "return window.getComputedStyle(arguments[0])" +
                                ".getPropertyValue(arguments[1]);",
                        menuItem,
                        cssProperty
                );

        if (value == null) {
            return "";
        }

        return value.toString().trim();
    }

    public boolean isMobileHeaderDisplayed() {

        return driver
                .findElement(mobileHeader)
                .isDisplayed();
    }


    public boolean isMobileHeaderLogoDisplayed() {

        return driver
                .findElement(mobileHeaderLogo)
                .isDisplayed();
    }


    public boolean isHamburgerButtonDisplayed() {

        return driver
                .findElement(hamburgerButton)
                .isDisplayed();
    }


    public boolean isHamburgerButtonEnabled() {

        return driver
                .findElement(hamburgerButton)
                .isEnabled();
    }


    public HeaderPage clickHamburgerButton() {

        WebElement menuButton =
                driver.findElement(hamburgerButton);

        clickWithJS(menuButton);

        return this;
    }


    public List<String> getMobileMenuTexts() {

        return getHeaderMenuTexts();
    }


    public boolean isLogOutDisplayed() {

        return isElementPresent(logOutButton);
    }



    public List<WebElement> getHeaderLinks() {

        return driver.findElements(headerLinks);
    }


    public boolean isHeaderImageLoaded() {

        WebElement image =
                driver.findElement(headerLogoImage);

        Boolean loaded =
                (Boolean) js.executeScript(
                        "return arguments[0].complete " +
                                "&& arguments[0].naturalWidth > 0;",
                        image
                );

        return Boolean.TRUE.equals(loaded);
    }


    public boolean isMobileHeaderImageLoaded() {

        WebElement image =
                driver.findElement(mobileHeaderLogo);

        Boolean loaded =
                (Boolean) js.executeScript(
                        "return arguments[0].complete " +
                                "&& arguments[0].naturalWidth > 0;",
                        image
                );

        return Boolean.TRUE.equals(loaded);
    }
}