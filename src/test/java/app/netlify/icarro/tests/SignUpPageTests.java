package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class SignUpPageTests extends TestBase {
    SignUpPage signUp;

    @BeforeMethod
    public void preconditions() {
        driver.navigate().to("https://icarro-v1.netlify.app/search?page=0&size=10");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signUpLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a.navigation-link[href='/register']")));
        signUpLink.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        signUp = new SignUpPage(driver).getSignUpPage();
    }
    @Test(priority = 1)
    public void isPageTitleSignUpCorrectPositiveSmokeTest() {
        signUp.isPageTitleCorrect("Registration");
    }

    @Test(priority = 2)
    public void createAccountPositiveTest() {
        SoftAssert softly = new SoftAssert();
        signUp.fillFirstName("Sara");
        signUp.fillLastName("Barabu");
        signUp.fillUsername(newEmail());
        signUp.fillPassword("Ss1a2r3a!");
        signUp.acceptTerms();
        signUp.clickSubmit();
        signUp.ModalWinOk(By.xpath("//button[.='OK']"));

        softly.assertTrue(
                signUp.isLogOutButtonPresent(),
                "User should be logged in");
    }
}
