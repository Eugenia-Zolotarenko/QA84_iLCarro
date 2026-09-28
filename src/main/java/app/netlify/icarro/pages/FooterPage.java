package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    private final By footerSearchLink =
            By.cssSelector(
                    "div.footer-container div.page-links a[href='/search']"
            );

    private final By footerLetCarWorkLink =
            By.cssSelector(
                    "div.footer-container div.page-links a[href='/let-car-work']"
            );

    private final By footerTermsOfUseLink =
            By.cssSelector(
                    "div.footer-container div.page-links a[href='/terms-of-use']"
            );

    private final By footerSignUpLink =
            By.cssSelector(
                    "div.footer-container div.page-links a[href='/register']"
            );

    private final By footerLoginLink =
            By.cssSelector(
                    "div.footer-container div.page-links a[href='/login']"
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


    public FooterPage(WebDriver driver) {
        super(driver);
    }


    public boolean isFooterDisplayed() {
        return isElementPresent(footer);
    }


    public boolean isFooterLogoDisplayed() {
        return isElementPresent(footerLogoImage);
    }


    public boolean isFooterLogoLinkPresent() {

        WebElement logo =
                driver.findElement(footerLogo);

        String href = logo.getAttribute("href");

        return href != null && !href.isEmpty();
    }


    public boolean isFooterLogoImagePresent() {

        WebElement image =
                driver.findElement(footerLogoImage);

        String src = image.getAttribute("src");

        return src != null && !src.isEmpty();
    }


    public boolean isSocialNetworksDisplayed() {
        return isElementPresent(socialNetworks);
    }


    public boolean areSocialMediaLinksPresent() {
        return driver.findElements(socialMediaLinks).size() == 5;
    }


    public boolean areSocialMediaIconsDisplayed() {
        return driver.findElements(socialMediaIcons).size() == 5;
    }


    public boolean isFacebookIconDisplayed() {
        return isElementPresent(facebookIcon);
    }


    public boolean isFooterMenuDisplayed() {
        return isElementPresent(footerMenu);
    }


    public boolean isFooterSearchLinkDisplayed() {
        return isElementPresent(footerSearchLink);
    }


    public boolean isFooterLetCarWorkLinkDisplayed() {
        return isElementPresent(footerLetCarWorkLink);
    }


    public boolean isFooterTermsOfUseLinkDisplayed() {
        return isElementPresent(footerTermsOfUseLink);
    }


    public boolean isFooterSignUpLinkDisplayed() {
        return isElementPresent(footerSignUpLink);
    }


    public boolean isFooterLoginLinkDisplayed() {
        return isElementPresent(footerLoginLink);
    }


    public boolean isTopCitiesDisplayed() {
        return isElementPresent(topCitiesContainer);
    }


    public boolean isTopCitiesTitleDisplayed() {
        return isElementPresent(topCitiesTitle);
    }


    public boolean areTopCitiesLinksPresent() {
        return driver.findElements(topCitiesLinks).size() == 10;
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
}
