package app.netlify.icarro.tests;

import app.netlify.icarro.core.TestBase;
import app.netlify.icarro.pages.HeaderPage;
import app.netlify.icarro.pages.HomePage;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class HeaderPageTests extends TestBase {
    HeaderPage header;
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, Object[] p) {
        super.setUp(method, p);
        header = new HomePage(driver).getHeaderPage();
    }
}
