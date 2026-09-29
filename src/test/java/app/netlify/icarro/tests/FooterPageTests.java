package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.FooterPage;
import app.netlify.icarro.pages.HomePage;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class FooterPageTests extends TestBase {
    FooterPage footer;
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        footer = new HomePage(driver).getFooterPage();
    }
}
