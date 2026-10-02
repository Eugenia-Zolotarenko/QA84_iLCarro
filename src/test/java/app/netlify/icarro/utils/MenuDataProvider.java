package app.netlify.icarro.utils;

import app.netlify.icarro.model.MenuItemData;
import org.testng.annotations.DataProvider;

public class MenuDataProvider {

    @DataProvider(name = "headerMenuData")
    public static Object[][] headerMenuData() {

        return new Object[][]{
                {
                        new MenuItemData(
                                "Search",
                                "/search"
                        )
                },
                {
                        new MenuItemData(
                                "Let car work",
                                "/let-car-work"
                        )
                },
                {
                        new MenuItemData(
                                "Terms of use",
                                "/terms-of-use"
                        )
                },
                {
                        new MenuItemData(
                                "Sign up",
                                "/register"
                        )
                },
                {
                        new MenuItemData(
                                "Log in",
                                "/login"
                        )
                }
        };
    }


    @DataProvider(name = "footerMenuData")
    public static Object[][] footerMenuData() {

        return new Object[][]{
                {
                        new MenuItemData(
                                "Search",
                                "/search"
                        )
                },
                {
                        new MenuItemData(
                                "Let the car work",
                                "/let-car-work"
                        )
                },
                {
                        new MenuItemData(
                                "Terms of use",
                                "/terms-of-use"
                        )
                },
                {
                        new MenuItemData(
                                "Sign up",
                                "/register"
                        )
                },
                {
                        new MenuItemData(
                                "Log in",
                                "/login"
                        )
                }
        };
    }
}
