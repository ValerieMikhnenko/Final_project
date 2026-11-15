package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage extends BasePage {

    private final SelenideElement title = $("form[class*=profile_shell] h1");
    private final SelenideElement myListingsBlock =
            $$("[class*=profilePage_listningBlock]").findBy(text("Мои объявления"));

    public ProfilePage shouldBeOpened() {
        title.shouldHave(text("Мой профиль"));
        myListingsBlock.shouldBe(visible);
        return this;
    }

    private ElementsCollection myCards() {
        return myListingsBlock.$$(".card");
    }

    private SelenideElement myCard(String name) {
        return myCards().findBy(text(name));
    }

    @Step("Проверить, что в «Мои объявления» есть «{name}»")
    public ProfilePage shouldHaveListing(String name) {
        myCard(name).shouldBe(visible).$("h2").shouldHave(exactText(name));
        return this;
    }

    @Step("Проверить, что в «Мои объявления» нет «{name}»")
    public ProfilePage shouldNotHaveListing(String name) {
        myListingsBlock.shouldBe(visible).shouldNotHave(text(name));
        return this;
    }

    @Step("Нажать «Редактировать» на карточке «{name}»")
    public ListingFormPage editListing(String name) {
        myCard(name).$(".editButton").click();
        return new ListingFormPage().shouldBeOpened("Редактировать объявление");
    }
}
