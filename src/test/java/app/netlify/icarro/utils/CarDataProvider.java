package app.netlify.icarro.utils;

import org.testng.annotations.DataProvider;

public class CarDataProvider {
    @DataProvider(name = "requiredFields")
    public static Object[][] requiredFields() {
        return new Object[][] {
                {"manufacture"}, {"model"}, {"year"}, {"fuel"},
                {"seats"}, {"carClass"}, {"serialNumber"}, {"pricePerDay"}
        };
    }
}
