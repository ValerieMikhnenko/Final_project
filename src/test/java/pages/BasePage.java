package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

public abstract class BasePage {

    protected final Header header = new Header();

    public Header header() {
        return header;
    }

    // clear() на этом сайте не всегда срабатывает (React), поэтому стираем через Backspace
    protected void clearAndType(SelenideElement field, String text) {
        field.click();
        field.sendKeys(Keys.END);
        int length = field.getValue() == null ? 0 : field.getValue().length();
        for (int i = 0; i < length; i++) {
            field.sendKeys(Keys.BACK_SPACE);
        }
        field.sendKeys(text);
    }
}
