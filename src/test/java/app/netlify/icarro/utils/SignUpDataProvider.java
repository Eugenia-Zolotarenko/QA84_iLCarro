package app.netlify.icarro.utils;

import app.netlify.icarro.model.NewUserSignUp;
import app.netlify.icarro.model.SignUpField;
import org.testng.annotations.DataProvider;
import static app.netlify.icarro.utils.TestDataGenerator.uniqueSuffix;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.netlify.icarro.utils.TestDataGenerator.generateEmail;

public class SignUpDataProvider {

    private static final String CSV_PATH = "src/test/resources/dataSearch/signup_negative_testdata_all.csv";
    private static final Pattern MACRO = Pattern.compile(
            "\\{(UNIQUE|SP|LF|CR|TAB|NUL|BEL|RAND:(\\d+)|PWD:(\\d+))\\}");
    private static final Pattern ANY_MACRO = Pattern.compile("\\{[A-Z]+(:\\d+)?\\}");

    private static final String CONTROL_CHARS_CSV_PATH =
            "src/test/resources/dataSearch/signup_negative_control_chars_api.csv";

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

//    private static NewUserSignUp toModel(Map<String, String> row) {
//        SignUpField expected = SignUpField.fromCsv(value(row, "expectedField"));
//        String email = (expected == SignUpField.EMAIL)
//                ? value(row, "email").replace("{UNIQUE}", uniqueSuffix())
//                : generateEmail();
//
//        return new NewUserSignUp(
//                value(row, "firstName"),
//                value(row, "lastName"),
//                email,
//                value(row, "password"),
//                expected,
//                value(row, "testDescription"));
//    }

    @DataProvider(name = "signUpControlCharsApiData")
    public Object[][] provideSignUpControlCharsApiData() {
        List<Map<String, String>> rows = CsvReader.readCsv(CONTROL_CHARS_CSV_PATH);

        if (rows.isEmpty()) {
            throw new IllegalStateException("No test data found in " + CONTROL_CHARS_CSV_PATH);
        }

        NewUserSignUp[] users = rows.stream()
                .map(SignUpDataProvider::toModel)
                .toArray(NewUserSignUp[]::new);

        checkUniqueDescriptions(users);

        return java.util.Arrays.stream(users)
                .map(u -> new Object[]{u})
                .toArray(Object[][]::new);
    }


    private static NewUserSignUp toModel(Map<String, String> row) {
        SignUpField expected = SignUpField.fromCsv(value(row, "expectedField"));
        // Для строк, где проверяется не email, берём валидный уникальный email.
        // Для email-строк берём значение из CSV и раскрываем макросы.
        String email = (expected == SignUpField.EMAIL)
                ? expand(value(row, "email"))
                : generateEmail();
        return new NewUserSignUp(
                expand(value(row, "firstName")),
                expand(value(row, "lastName")),
                email,
                expand(value(row, "password")),
                expected,
                value(row, "testDescription"));   // описание не раскрываем
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

    static String expand(String raw) {
        Matcher m = MACRO.matcher(raw);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            String token = m.group(1);
            String replacement;
            if (token.startsWith("RAND:")) {
                replacement = randomLetters(Integer.parseInt(m.group(2)));
            } else if (token.startsWith("PWD:")) {
                replacement = password(Integer.parseInt(m.group(3)));
            } else {
                replacement = switch (token) {
                    case "UNIQUE" -> uniqueSuffix();
                    case "SP"     -> " ";
                    case "LF"     -> "\n";
                    case "CR"     -> "\r";
                    case "TAB"    -> "\t";
                    case "NUL"    -> "\0";
                    case "BEL"    -> "\7";
                    default -> throw new IllegalArgumentException("Unknown macro {" + token + "}");
                };
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(sb);
        String result = sb.toString();

        // опечатка вроде {TABB} не должна тихо уйти в форму как текст
        Matcher unknown = ANY_MACRO.matcher(result);
        if (unknown.find()) {
            throw new IllegalArgumentException(
                    "Unknown macro " + unknown.group() + " in '" + raw + "'");
        }
        return result;
    }

    private static String randomLetters(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + ThreadLocalRandom.current().nextInt(26)));
        }
        return sb.toString();
    }

    private static String password(int n) {
        if (n < 4) throw new IllegalArgumentException("PWD length must be >= 4, got " + n);
        StringBuilder sb = new StringBuilder(n);
        while (sb.length() < n) sb.append("Aa1!");
        return sb.substring(0, n);
    }

    private static String value(Map<String, String> row, String column) {
        if (!row.containsKey(column)) {
            throw new IllegalStateException("Missing CSV column: " + column + ", row: " + row);
        }
        String v = row.get(column);
        return v == null ? "" : v;
    }


}