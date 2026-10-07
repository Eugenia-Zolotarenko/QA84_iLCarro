package app.netlify.icarro.model;

public enum SignUpField {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("username"),
    PASSWORD("password");

    private final String csvName;

    SignUpField(String inputName) {
        this.csvName = inputName;
    }

    public String getInputName() {
        return csvName;
    }

    public static SignUpField fromCsv(String value) {
        if (value == null) {
            throw new IllegalArgumentException("expectedField is empty in CSV");
        }
        for (SignUpField field : values()) {
            if (field.csvName.equalsIgnoreCase(value.trim())) {
                return field;
            }
        }
        throw new IllegalArgumentException("Unknown expectedField in CSV: '" + value + "'");
    }
}

