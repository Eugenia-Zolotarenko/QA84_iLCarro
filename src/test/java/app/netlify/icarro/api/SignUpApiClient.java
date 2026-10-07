package app.netlify.icarro.api;

import app.netlify.icarro.model.NewUserSignUp;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SignUpApiClient {

    private static final String BASE_URL = "https://ilcarro-backend.herokuapp.com";
    private static final String REGISTRATION_ENDPOINT = "/v1/user/registration/usernamepassword";
    public static final String REGISTRATION_URL = BASE_URL + REGISTRATION_ENDPOINT;

    // Heroku может "просыпаться" долго; без таймаутов тест способен зависнуть.
    private static final RestAssuredConfig CONFIG = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", 10_000)
                    .setParam("http.socket.timeout", 20_000));

    public Response register(String firstName, String lastName, String username, String password) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("username", username);
        body.put("password", password);

        return given()
                .config(CONFIG)
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(REGISTRATION_ENDPOINT);
    }

    public Response register(NewUserSignUp user) {
        return register(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());
    }
}
