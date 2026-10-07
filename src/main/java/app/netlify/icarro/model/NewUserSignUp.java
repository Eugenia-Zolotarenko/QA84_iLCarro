package app.netlify.icarro.model;

import static app.netlify.icarro.utils.TestDataGenerator.generateEmail;

public class NewUserSignUp {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final SignUpField expectedField;
    private final String testDescription;

    public NewUserSignUp(String firstName, String lastName, String email,
                         String password, SignUpField expectedField, String testDescription) {
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

    public SignUpField getExpectedField() {
        return expectedField;}

    public String getTestDescription() {
        return testDescription;
    }


    @Override
    public String toString() {
        return testDescription + " | " + expectedField;
    }

}

