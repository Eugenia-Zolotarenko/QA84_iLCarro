package app.netlify.icarro.utils;

import app.netlify.icarro.model.LoginData;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    public static LoginData validUser() {
        return new LoginData(
                "oksana.icarro.test@gmail.com",
                "Aa123456!"
        );
    }


    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() {

        return new Object[][]{
                {validUser()}
        };
    }


    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {

        return new Object[][]{
                {
                        new LoginData(
                                "test2@gmail.com",
                                "Aa123456!"
                        )
                },
                {
                        new LoginData(
                                "test222@gmail.com",
                                "123456!"
                        )
                }
        };
    }


    @DataProvider(name = "emptyLoginData")
    public static Object[][] emptyLoginData() {

        return new Object[][]{
                {
                        new LoginData(
                                "",
                                "Aa123456!"
                        )
                },
                {
                        new LoginData(
                                "oksana.icarro.test@gmail.com",
                                ""
                        )
                },
                {
                        new LoginData(
                                "",
                                ""
                        )
                }
        };
    }
}
