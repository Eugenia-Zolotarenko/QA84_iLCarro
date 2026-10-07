package app.netlify.icarro.utils;

import app.netlify.icarro.model.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.InputStream;
import java.util.List;

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

    @DataProvider(name = "invalidCarDataObj")
    public static Object[][] invalidCarDataObj() {
        return new Object[][] {
                { new Car("Berlin", "", "Corolla", "2020", "petrol", "5", "Economy", "QA12345", "50") },
                { new Car("Hamburg", "Audi", "", "2021", "petrol", "5", "Standard", "QA77777", "80") },
                { new Car("Frankfurt", "Mercedes", "E-Class", "", "hybrid", "5", "Luxury", "QA88888", "150") },
                { new Car("Munich", "BMW", "X5", "2022", "diesel", "", "Business", "QA98765", "150") },
                { new Car("Stuttgart", "Porsche", "911", "2024", "", "2", "Sport", "QA11111", "300") },
                { new Car("Frankfurt", "Mercedes", "E-Class", "2023", "hybrid", "5", "", "QA88888", "150") },
                { new Car("Munich", "BMW", "X5", "2022", "diesel", "5", "Business", "", "150") },
                { new Car("Stuttgart", "Porsche", "911", "2024", "petrol", "2", "Sport", "QA11111", "") }
        };
    }

    @DataProvider(name = "carsFromJson")
    public static Object[][] carsFromJson() throws Exception {
        // 1. Создаем ObjectMapper от Jackson
        ObjectMapper mapper = new ObjectMapper();

        // 2. Читаем файл из папки ресурсов (src/test/resources/cars.json)
        InputStream inputStream = CarDataProvider.class.getClassLoader().getResourceAsStream("cars.json");

        if (inputStream == null) {
            throw new RuntimeException("Файл cars.json не найден в папке resources!");
        }

        // 3. Десериализуем JSON-массив в список Java-объектов List<CarData>
        List<Car> carList = mapper.readValue(inputStream, new TypeReference<List<Car>>() {});

        // 4. Превращаем List<CarData> в формат Object[][] для TestNG
        Object[][] data = new Object[carList.size()][1];
        for (int i = 0; i < carList.size(); i++) {
            data[i][0] = carList.get(i);
        }

        return data;
    }



}
