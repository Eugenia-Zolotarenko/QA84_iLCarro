package app.netlify.icarro.utils;

import java.util.concurrent.atomic.AtomicLong;

public class TestDataGenerator {

    private static final AtomicLong COUNTER = new AtomicLong();

    public static String uniqueSuffix() {
        return System.currentTimeMillis() + "" + COUNTER.incrementAndGet();
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
