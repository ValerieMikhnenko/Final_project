package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.codeborne.selenide.Selenide.webdriver;

public class ListingPage extends BasePage {

    // на узком экране у заголовка класс h1Mobile
    private final SelenideElement title = $("h1.h1, h1.h1Mobile");
    private final SelenideElement deleteButton = $(byText("Удалить"));

    public ListingPage shouldBeOpened(String name) {
        webdriver().shouldHave(urlContaining("/listing/"));
        title.shouldHave(exactText(name));
        return this;
    }

    @Step("Нажать «Удалить»")
    public void delete() {
        deleteButton.click();
        title.should(disappear);
    }
}
