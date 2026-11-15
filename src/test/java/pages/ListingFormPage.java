package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.Listing;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ListingFormPage extends BasePage {

    private final SelenideElement title = $("h1[class*=createListing_title]");
    private final SelenideElement form = $("form[class*=createListing]");
    private final SelenideElement nameInput = form.$("input[name=name]");
    private final SelenideElement descriptionInput = form.$("textarea[name=description]");
    private final SelenideElement priceInput = form.$("input[name=price]");
    private final SelenideElement submitButton = form.$("button[type=submit]");

    public ListingFormPage shouldBeOpened(String expectedTitle) {
        title.shouldHave(text(expectedTitle));
        return this;
    }

    @Step("Заполнить объявление: {listing}")
    public ListingFormPage fill(Listing listing) {
        setName(listing.getName());
        selectDropdown("category", listing.getCategory());
        selectCondition(listing.getCondition());
        selectDropdown("city", listing.getCity());
        clearAndType(descriptionInput, listing.getDescription());
        clearAndType(priceInput, String.valueOf(listing.getPrice()));
        return this;
    }

    @Step("Ввести название «{name}»")
    public ListingFormPage setName(String name) {
        clearAndType(nameInput, name);
        nameInput.shouldHave(value(name));
        return this;
    }

    @Step("Выбрать «{optionText}» в списке {inputName}")
    public ListingFormPage selectDropdown(String inputName, String optionText) {
        SelenideElement menu = form.$x(".//input[@name='" + inputName + "']"
                + "/ancestor::div[contains(@class,'dropDownMenu_dropMenu')]");
        menu.$("button[class*=dropDownMenu_arrowDown]").click();
        menu.$$("button[class*=dropDownMenu_btn]").findBy(exactText(optionText)).shouldBe(visible).click();
        menu.$("input[name=" + inputName + "]").shouldHave(value(optionText));
        return this;
    }

    @Step("Выбрать состояние «{condition}»")
    public ListingFormPage selectCondition(String condition) {
        form.$x(".//fieldset//label[normalize-space()='" + condition + "']").click();
        return this;
    }

    @Step("Нажать кнопку сохранения объявления")
    public void submit() {
        submitButton.click();
        title.should(disappear);
    }
}
