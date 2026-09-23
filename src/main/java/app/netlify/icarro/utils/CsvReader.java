package app.netlify.icarro.utils;

import java.io.*;
import java.util.*;

public class CsvReader {

    public static List<Map<String, String>> readCsv(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return data;
            }

            String[] headers = headerLine.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    String value = (i < values.length) ? values[i].trim() : "";
                    row.put(headers[i], value);
                }
                data.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + filePath);
            e.printStackTrace();
        }
        return data;
    }
}