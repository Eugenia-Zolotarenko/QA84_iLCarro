package app.netlify.icarro.utils;

import app.netlify.icarro.model.Car;
import org.testng.annotations.DataProvider;

public class CarDataProvider {
    @DataProvider(name = "validCarDataObj")
    public static Object[][] validCarDataObj() {
        return new Object[][] {
                { new Car("Berlin", "Toyota", "Corolla", "2020", "petrol", "5", "Economy", "QA12345", "50") },
                { new Car("Hamburg", "Audi", "A4", "2021", "petrol", "5", "Standard", "QA77777", "80") },
                { new Car("Frankfurt", "Mercedes", "E-Class", "2023", "hybrid", "5", "Luxury", "QA88888", "150") },
                { new Car("Munich", "BMW", "X5", "2022", "diesel", "5", "Business", "QA98765", "150") },
                { new Car("Stuttgart", "Porsche", "911", "2024", "petrol", "2", "Sport", "QA11111", "300") }
        };
    }
}
