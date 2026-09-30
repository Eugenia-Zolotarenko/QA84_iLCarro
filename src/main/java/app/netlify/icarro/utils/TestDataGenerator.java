package app.netlify.icarro.utils;

public class TestDataGenerator {
    public static String uniqueSuffix() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String generateEmail() {
        return "sara" + uniqueSuffix() + "@gmail.com";
    }

//    public static String generateFirstName() {
//        return "Sara" + uniqueSuffix();
//    }
//
//    public static String generateLastName() {
//        return "Barabu" + uniqueSuffix();
//    }

}
