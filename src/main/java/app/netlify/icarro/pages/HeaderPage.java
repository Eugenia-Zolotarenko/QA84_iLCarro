package app.netlify.icarro.pages;

import app.netlify.icarro.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderPage extends BasePage {

    private final By header =
            By.cssSelector("div.header");

    private final By headerLogo =
            By.cssSelector("div.header a.logo");

    private final By headerLogoImage =
            By.cssSelector(
                    "div.header a.logo img[alt='IlCarro logo']"
            );

    private final By homeLink =
            By.cssSelector("div.header a.logo");

    private final By searchLink =
            By.cssSelector("div.header a[href='/search']");

    private final By letCarWorkLink =
            By.cssSelector("div.header a[href='/let-car-work']");

    private final By termsOfUseLink =
            By.cssSelector("div.header a[href='/terms-of-use']");

    private final By signUpLink =
            By.cssSelector("div.header a[href='/register']");

    private final By loginLink =
            By.cssSelector("div.header a[href='/login']");


    // MOBILE HEADER

    private final By mobileHeader =
            By.cssSelector("div.mobile-header");

    private final By mobileHeaderLogo =
            By.cssSelector(
                    "div.mobile-header a.logo img[alt='IlCarro logo']"
            );

    private final By hamburgerButton =
            By.cssSelector("div.mobile-header button");

    private final By logOutButton =
            By.cssSelector("div.header button.navigation-link.linklike");

    public HeaderPage(WebDriver driver) {
        super(driver);
    }


    public boolean isHeaderDisplayed() {
        return isElementPresent(header);
    }


    public boolean isHeaderLogoDisplayed() {
        return isElementPresent(headerLogoImage);
    }


    public boolean isHeaderLogoImageDisplayed() {
        return isElementPresent(headerLogoImage);
    }


    public boolean isHeaderLogoImagePresent() {

        WebElement image =
                driver.findElement(headerLogoImage);

        String src = image.getAttribute("src");

        return src != null && !src.isEmpty();
    }


    public boolean isHeaderLogoLinkPresent() {

        WebElement logo =
                driver.findElement(headerLogo);

        String href = logo.getAttribute("href");

        return href != null && !href.isEmpty();
    }


    public boolean isHomeLinkDisplayed() {
        return isElementPresent(homeLink);
    }


    public boolean isSearchLinkDisplayed() {
        return isElementPresent(searchLink);
    }


    public boolean isLetCarWorkLinkDisplayed() {
        return isElementPresent(letCarWorkLink);
    }


    public boolean isTermsOfUseLinkDisplayed() {
        return isElementPresent(termsOfUseLink);
    }


    public boolean isSignUpLinkDisplayed() {
        return isElementPresent(signUpLink);
    }


    public boolean isLoginLinkDisplayed() {
        return isElementPresent(loginLink);
    }


    public boolean isHeaderMenuDisplayed() {
        return isElementPresent(header);
    }


    public boolean isMobileHeaderDisplayed() {
        return driver.findElement(mobileHeader).isDisplayed();
    }


    public boolean isMobileHeaderLogoDisplayed() {
        return driver.findElement(mobileHeaderLogo).isDisplayed();
    }


    public boolean isHamburgerButtonDisplayed() {
        return driver.findElement(hamburgerButton).isDisplayed();
    }

    public boolean isLogOutDisplayed() {
        return isElementPresent(logOutButton);
    }


    public HeaderPage clickHamburgerButton() {

        WebElement menuButton =
                driver.findElement(hamburgerButton);

        clickWithJS(menuButton);

        return this;
    }
}