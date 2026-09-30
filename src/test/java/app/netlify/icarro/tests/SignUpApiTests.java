package app.netlify.icarro.tests;

import app.netlify.icarro.api.SignUpApiClient;
import app.netlify.icarro.model.NewUserSignUp;
import app.netlify.icarro.utils.SignUpDataProvider;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SignUpApiTests {

    private static final Logger logger = LoggerFactory.getLogger(SignUpApiTests.class);

    private static final int EXPECTED_STATUS = 400;
    private static final int MAX_BODY_LENGTH = 500;

    private final SignUpApiClient signUpApi = new SignUpApiClient();

    @Test(
            dataProvider = "signUpNegativeTestData",
            dataProviderClass = SignUpDataProvider.class,
            groups = {"regr", "backend"},
            description = "Sign up API: server rejects invalid data")
    public void signUpBackendNegativeTest(NewUserSignUp testData) {
        logger.info("");
        logger.info("-----------------------------^-----------------------------");

        String caseName = "[" + testData.getTestDescription() + "]";
        String expectedField = testData.getExpectedField().getInputName();

        Response response = signUpApi.register(testData);

        int status = response.statusCode();
        String body = response.asString();
        Set<String> errorFields = extractErrorFields(response);

        boolean statusOk = status == EXPECTED_STATUS;
        boolean fieldOk = errorFields.contains(expectedField);
        boolean noToken = !body.contains("accessToken");

        List<String> failed = new ArrayList<>();
        if (!statusOk) failed.add("status");
        if (!fieldOk) failed.add("field");
        if (!noToken) failed.add("token");

        if (failed.isEmpty()) {
            logger.info("PASS \n| {} \n| HTTP {} \n| error fields: {}",
                    testData.getTestDescription(), status, errorFields);
        } else {
            logger.error("FAIL \n({}) \n| {}\n{}",
                    String.join(", ", failed),
                    testData.getTestDescription(),
                    details(testData, expectedField, response, errorFields));
        }

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(statusOk,
                caseName + " Expected HTTP " + EXPECTED_STATUS + ", got " + status + ". Body: " + body);
        soft.assertTrue(fieldOk,
                caseName + " Expected validation error for field '" + expectedField
                        + "', but got fields: " + errorFields);
        soft.assertTrue(noToken,
                caseName + " Server returned a token for invalid registration");
        soft.assertAll();
    }

    private static String details(NewUserSignUp data, String expectedField,
                                  Response response, Set<String> errorFields) {
        String body = response.asString();
        return String.join("\n",
                "Request : POST " + SignUpApiClient.REGISTRATION_URL,
                "  firstName: " + show(data.getFirstName()),
                "  lastName : " + show(data.getLastName()),
                "  username : " + show(data.getEmail()),
                "  password : " + show(data.getPassword()),
                "Expected: HTTP " + EXPECTED_STATUS + " + error on field '" + expectedField + "'",
                "Actual  : HTTP " + response.statusCode() + " in " + response.time()
                        + " ms | error fields: " + errorFields,
                "Body    : " + truncate(body),
                "Hint    : " + hint(body));
    }

    private static String show(String value) {
        return value == null ? "<null>" : "\"" + value + "\" (len " + value.length() + ")";
    }

    private static String mask(String text) {
        return text.replaceAll("\"accessToken\"\\s*:\\s*\"[^\"]+\"", "\"accessToken\":\"***\"");
    }

    private static String truncate(String text) {
        String masked = mask(text);
        return masked.length() <= MAX_BODY_LENGTH
                ? masked
                : masked.substring(0, MAX_BODY_LENGTH) + "... [truncated, total " + masked.length() + " chars]";
    }

    private static String hint(String body) {
        return body.contains("User already exists")
                ? "DATA PROBLEM: email already registered, field validation was not reached"
                : "-";
    }

    private static Set<String> extractErrorFields(Response response) {
        try {
            Object message = response.jsonPath().get("message");
            if (message instanceof Map<?, ?> map) {
                return map.keySet().stream()
                        .map(String::valueOf)
                        .collect(Collectors.toSet());
            }
        } catch (RuntimeException ignored) {
            // тело не JSON (например, HTML-страница с 404)
        }
        return Collections.emptySet();
    }

}