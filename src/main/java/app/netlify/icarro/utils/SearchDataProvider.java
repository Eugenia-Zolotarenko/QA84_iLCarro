package app.netlify.icarro.utils;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchDataProvider {

    @DataProvider(name = "searchData")
    public Object[][] searchData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/dataSearch/searchData.csv")
        );

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",");

            data.add(new Object[]{
                    values[0],
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2])
            });
        }

        reader.close();

        return data.toArray(new Object[0][]);
    }


    @DataProvider(name = "beershebaNoCarsData")
    public Object[][] beershebaNoCarsData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/dataSearch/beershebaNoCarsData.csv")
        );

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.isBlank()) {
                continue;
            }

            String[] values = line.split(",");

            data.add(new Object[]{
                    Integer.parseInt(values[0]),
                    Integer.parseInt(values[1])
            });
        }

        reader.close();

        return data.toArray(new Object[0][]);
    }
}
