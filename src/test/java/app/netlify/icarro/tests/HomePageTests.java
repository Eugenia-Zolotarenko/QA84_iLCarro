package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests extends TestBase {
    HomePage home;

    @BeforeMethod
    public void precondition(){
        home = new HomePage(driver);
    }

    @Test
    public void isPageTitleCorrectPositiveTest(){
        home.isPageTitleCorrect("Find your car now!");
    }

}
