package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage extends BasePage {

    private final SelenideElement filterForm = $("form[class*=filter_shell]");
    private final ElementsCollection cards = $$(".card");
    private final SelenideElement pagination = $("[class*=pagination_shell]");
    private final SelenideElement pageCounter = pagination.$("p");
    private final SelenideElement nextPageButton = pagination.$(".arrowButton--right");

    @Step("Открыть главную страницу")
    public MainPage open() {
        com.codeborne.selenide.Selenide.open("/");
        return shouldBeOpened();
    }

    public MainPage shouldBeOpened() {
        filterForm.shouldBe(visible);
        return this;
    }

    // из профиля страница объявления открывается пустой, поэтому открываем из ленты
    @Step("Открыть объявление «{name}» из ленты")
    public ListingPage openListing(String name) {
        findCardOnAnyPage(name).$("h2").click();
        return new ListingPage().shouldBeOpened(name);
    }

    // объявление может быть не на первой странице, листаем до него
    private SelenideElement findCardOnAnyPage(String name) {
        cards.first().shouldBe(visible);
        while (true) {
            SelenideElement card = cards.findBy(text(name));
            if (card.exists()) {
                return card;
            }
            if (!nextPageButton.isEnabled()) {
                throw new AssertionError("Объявление «" + name + "» не найдено ни на одной странице ленты");
            }
            String pageBefore = pageCounter.getText();
            nextPageButton.click();
            pageCounter.shouldNotHave(exactText(pageBefore));
        }
    }
}
