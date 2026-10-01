package app.netlify.icarro.model;

public class MenuItemData {

    private final String text;
    private final String expectedUrlPart;

    public MenuItemData(String text, String expectedUrlPart) {
        this.text = text;
        this.expectedUrlPart = expectedUrlPart;
    }

    public String getText() {
        return text;
    }

    public String getExpectedUrlPart() {
        return expectedUrlPart;
    }
}
