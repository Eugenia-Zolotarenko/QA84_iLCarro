package app.netlify.icarro.utils;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    WebDriver driver;

    public String takeScreenshot(WebDriver driver) {
        if (driver == null) {
            return "WebDriver is null";
        }

        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);//temporary
        File screen = new File("screenshots/screen-" + System.currentTimeMillis() + ".png");//
        try {
            Files.copy(tmp,screen); //com.google.common.io
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return screen.getAbsolutePath();
    }
}
