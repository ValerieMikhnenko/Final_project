package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationModal extends BasePage {

    private final SelenideElement root = $("form[class*=popUp_shell]");
    private final SelenideElement title = root.$("h1");
    private final SelenideElement emailInput = root.$("input[name=email]");
    private final SelenideElement passwordInput = root.$("input[name=password]");
    private final SelenideElement repeatPasswordInput = root.$("input[name=submitPassword]");
    private final SelenideElement submitButton = root.$("button[type=submit]");

    private final SelenideElement emailField = root.$x(".//input[@name='email']/parent::div");
    private final SelenideElement emailErrorText =
            root.$x(".//input[@name='email']/ancestor::div[2]/following-sibling::span[1]");

    public RegistrationModal shouldBeOpened() {
        title.shouldHave(text("Зарегистрироваться"));
        return this;
    }

    @Step("Заполнить форму регистрации: email {email}")
    public RegistrationModal fill(String email, String password) {
        clearAndType(emailInput, email);
        clearAndType(passwordInput, password);
        clearAndType(repeatPasswordInput, password);
        return this;
    }

    @Step("Нажать «Создать аккаунт»")
    public RegistrationModal submit() {
        submitButton.click();
        return this;
    }

    @Step("Проверить, что окно регистрации закрылось")
    public void shouldBeClosed() {
        root.should(disappear);
    }

    @Step("Проверить ошибку у поля email")
    public RegistrationModal shouldShowEmailError() {
        root.shouldBe(visible);
        emailField.shouldHave(attributeMatching("class", ".*input_inputError.*").because("поле email подсвечено как ошибочное"));
        emailErrorText.shouldHave(exactText("Ошибка"));
        return this;
    }
}
