package app.netlify.icarro.utils;

import app.netlify.icarro.model.LoginData;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "invalidLoginData")
    public static Object[][] twoLolins() {
        return new Object[][]{
                {new LoginData("test2@gmail.com", "Aa123456!")},
                {new LoginData("test222@gmail.com", "123456!")}
        };
    }

    @DataProvider(name = "emptyLoginData")
    public static Object[][] emptyFields() {
        return new Object[][]{
                {new LoginData("", "Aa123456!")},
                {new LoginData("test222@gmail.com", "")},
                {new LoginData("     ", "    ")}
        };
    }
}
