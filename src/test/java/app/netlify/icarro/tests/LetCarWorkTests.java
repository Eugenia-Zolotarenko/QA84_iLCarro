package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HomePage;
import app.netlify.icarro.pages.LetCarWorkPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LetCarWorkTests extends TestBase {
    LetCarWorkPage car;
    @BeforeMethod
    public void preconditions(){
        car = new HomePage(driver).getLetCarWorkPage();
    }

    @Test
    public void isPageTitleCorrectPositiveTest(){
        car.isPageTitleCorrect("Let the car work");
    }
}
