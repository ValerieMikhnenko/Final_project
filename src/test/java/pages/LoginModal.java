package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginModal extends BasePage {

    private final SelenideElement root = $("form[class*=popUp_shell]");
    private final SelenideElement title = root.$("h1");
    private final SelenideElement emailInput = root.$("input[name=email]");
    private final SelenideElement passwordInput = root.$("input[name=password]");
    private final SelenideElement submitButton = root.$("button[type=submit]");
    private final SelenideElement noAccountButton = root.$(byText("Нет аккаунта"));

    public LoginModal shouldBeOpened() {
        title.shouldHave(text("Войти"));
        return this;
    }

    @Step("Перейти к регистрации («Нет аккаунта»)")
    public RegistrationModal goToRegistration() {
        noAccountButton.click();
        return new RegistrationModal().shouldBeOpened();
    }

    @Step("Войти: email {email}")
    public void login(String email, String password) {
        clearAndType(emailInput, email);
        clearAndType(passwordInput, password);
        submitButton.click();
        root.should(disappear);
    }
}
