package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FooterPage extends BasePage {

    private final By footer =
            By.cssSelector("div.footer-container");

    private final By footerLogo =
            By.cssSelector("div.footer-container a.logo");

    private final By footerLogoImage =
            By.cssSelector(
                    "div.footer-container a.logo img[alt='IlCarro logo']"
            );

    private final By socialNetworks =
            By.cssSelector(
                    "div.footer-container div.social-networks"
            );

    private final By socialMediaLinks =
            By.cssSelector(
                    "div.footer-container div.social-networks a"
            );

    private final By socialMediaIcons =
            By.cssSelector(
                    "div.footer-container div.social-networks i"
            );

    private final By facebookIcon =
            By.cssSelector(
                    "div.footer-container div.social-networks i.icon-facebook-squared"
            );

    private final By footerMenu =
            By.cssSelector(
                    "div.footer-container div.page-links"
            );

    private final By footerMenuLinks =
            By.cssSelector(
                    "div.footer-container div.page-links a"
            );

    private final By topCitiesContainer =
            By.cssSelector(
                    "div.footer-container div.top-cities-container"
            );

    private final By topCitiesTitle =
            By.cssSelector(
                    "div.footer-container div.top-cities-container span.title"
            );

    private final By topCitiesLinks =
            By.cssSelector(
                    "div.footer-container div.top-cities-container div.top-cities a"
            );

    private final By contactInformation =
            By.cssSelector(
                    "div.footer-container address.address-container"
            );

    private final By telephone =
            By.cssSelector(
                    "div.footer-container address.address-container a.telephone"
            );

    private final By footerAddress =
            By.cssSelector(
                    "div.footer-container address.address-container a.address"
            );

    private final By footerLinks =
            By.cssSelector(
                    "div.footer-container a"
            );

    private final By footerImages =
            By.cssSelector(
                    "div.footer-container img"
            );


    public FooterPage(WebDriver driver) {
        super(driver);
    }


    public boolean isFooterDisplayed() {

        return isElementPresent(footer);
    }

    public boolean isFooterLogoDisplayed() {

        return isElementPresent(footerLogoImage);
    }


    public String getFooterLogoHref() {

        return driver
                .findElement(footerLogo)
                .getAttribute("href");
    }


    public String getFooterLogoSrc() {

        return driver
                .findElement(footerLogoImage)
                .getAttribute("src");
    }


    public boolean isSocialNetworksDisplayed() {

        return isElementPresent(socialNetworks);
    }


    public boolean areSocialMediaLinksPresent() {

        return driver
                .findElements(socialMediaLinks)
                .size() == 5;
    }


    public boolean areSocialMediaIconsDisplayed() {

        List<WebElement> icons =
                driver.findElements(socialMediaIcons);

        if (icons.size() != 5) {
            return false;
        }

        for (WebElement icon : icons) {

            if (!icon.isDisplayed()) {
                return false;
            }
        }

        return true;
    }


    public boolean isFacebookIconDisplayed() {

        return isElementPresent(facebookIcon);
    }


    public boolean isFooterMenuDisplayed() {

        return isElementPresent(footerMenu);
    }


    public List<String> getFooterMenuTexts() {

        List<WebElement> elements =
                driver.findElements(footerMenuLinks);

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
                driver.findElements(footerMenuLinks);

        for (WebElement menuItem : menuItems) {

            if (menuItem.isDisplayed()
                    && menuItem.getText()
                    .trim()
                    .equals(text.trim())) {

                return menuItem;
            }
        }

        throw new RuntimeException(
                "Footer menu item was not found: " + text
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


    public FooterPage clickMenuItem(String text) {

        WebElement menuItem =
                findMenuItemByText(text);

        clickWithJS(menuItem);

        return this;
    }

    public FooterPage hoverMenuItem(String text) {

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

        return value
                .toString()
                .trim();
    }


    public boolean isTopCitiesDisplayed() {

        return isElementPresent(topCitiesContainer);
    }


    public boolean isTopCitiesTitleDisplayed() {

        return isElementPresent(topCitiesTitle);
    }


    public boolean areTopCitiesLinksPresent() {

        return driver
                .findElements(topCitiesLinks)
                .size() == 10;
    }

    public boolean isContactInformationDisplayed() {

        return isElementPresent(contactInformation);
    }


    public boolean isTelephoneDisplayed() {

        return isElementPresent(telephone);
    }


    public boolean isFooterAddressDisplayed() {

        return isElementPresent(footerAddress);
    }


    public List<WebElement> getFooterLinks() {

        return driver
                .findElements(footerLinks);
    }


    public boolean areFooterImagesLoaded() {

        List<WebElement> images =
                driver.findElements(footerImages);

        for (WebElement image : images) {

            Boolean loaded =
                    (Boolean) js.executeScript(
                            "return arguments[0].complete " +
                                    "&& arguments[0].naturalWidth > 0;",
                            image
                    );

            if (!Boolean.TRUE.equals(loaded)) {

                return false;
            }
        }

        return true;
    }
}