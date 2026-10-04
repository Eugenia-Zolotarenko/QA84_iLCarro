package app.netlify.icarro.utils;

import app.netlify.icarro.model.NewUserSignUp;
import app.netlify.icarro.model.SignUpField;
import org.testng.annotations.DataProvider;
import static app.netlify.icarro.utils.TestDataGenerator.uniqueSuffix;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static app.netlify.icarro.utils.TestDataGenerator.generateEmail;

public class SignUpDataProvider {

    private static final String CSV_PATH = "src/test/resources/dataSearch/signup_negative_testdata50.csv";

    @DataProvider(name = "signUpNegativeTestData")
    public Object[][] provideSignUpNegativeData() {
        List<Map<String, String>> rows = CsvReader.readCsv(CSV_PATH);

        if (rows.isEmpty()) {
            throw new IllegalStateException("No test data found in " + CSV_PATH);
        }

        NewUserSignUp[] users = new NewUserSignUp[rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            try {
                users[i] = toModel(rows.get(i));
            } catch (RuntimeException e) {
                throw new IllegalStateException(
                        "Invalid CSV data at data row " + (i + 1)
                                + " (file line " + (i + 2) + "): " + rows.get(i), e);
            }
        }

        checkUniqueDescriptions(users);

        Object[][] data = new Object[users.length][1];
        for (int i = 0; i < users.length; i++) {
            data[i][0] = users[i];
        }
        return data;
    }

    private static NewUserSignUp toModel(Map<String, String> row) {
        SignUpField expected = SignUpField.fromCsv(value(row, "expectedField"));
        String email = (expected == SignUpField.EMAIL)
                ? value(row, "email").replace("{UNIQUE}", uniqueSuffix())
                : generateEmail();

        return new NewUserSignUp(
                value(row, "firstName"),
                value(row, "lastName"),
                email,
                value(row, "password"),
                expected,
                value(row, "testDescription"));
    }

    private static void checkUniqueDescriptions(NewUserSignUp[] users) {
        Set<String> descriptions = new HashSet<>();
        for (NewUserSignUp user : users) {
            if (!descriptions.add(user.getTestDescription())) {
                throw new IllegalStateException(
                        "Duplicate testDescription in CSV: '" + user.getTestDescription() + "'");
            }
        }
    }

    private static String value(Map<String, String> row, String column) {
        if (!row.containsKey(column)) {
            throw new IllegalStateException("Missing CSV column: " + column + ", row: " + row);
        }
        String v = row.get(column);
        return v == null ? "" : v;
    }
}