package app.netlify.icarro.model;

public class SignUpTestData {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String expectedField;
    private String testDescription;


    public SignUpTestData(String firstName, String lastName, String email,
                          String password, String expectedField, String testDescription) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.expectedField = expectedField;
        this.testDescription = testDescription;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedField() {
        return expectedField;
    }

    public String getTestDescription() {
        return testDescription;
    }


    @Override
    public String toString() {
        return testDescription + " | " + expectedField;
    }

}

