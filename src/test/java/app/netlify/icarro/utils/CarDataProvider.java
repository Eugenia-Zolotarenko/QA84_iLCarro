package app.netlify.icarro.utils;

import org.testng.annotations.DataProvider;

public class CarDataProvider {
    @DataProvider(name = "requiredFields")
    public static Object[][] requiredFields() {
        return new Object[][] {
                {"manufacture", "Toyota", "Make"},
                {"model", "Corolla", "Model"},
                {"year", "2020", "Year"},
                {"fuel", "petrol", "Fuel"},
                {"seats", "5", "Seats"},
                {"carClass", "Economy", "Car class"},
                {"serialNumber", "QA12345", "Car registration number"},
                {"pricePerDay", "50", "Price"}
        };
    }
}
