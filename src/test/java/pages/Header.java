package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Header {

    private final SelenideElement root = $("[class*=header_shell]");
    private final SelenideElement logo = root.$("svg");
    private final SelenideElement loginButton = root.$(byText("Вход и регистрация"));
    private final SelenideElement logoutButton = root.$(byText("Выйти"));
    private final SelenideElement avatarButton = root.$("button.circleSmall");
    private final SelenideElement createListingButton = root.$(byText("Разместить объявление"));

    @Step("Нажать «Вход и регистрация»")
    public LoginModal openLoginModal() {
        loginButton.click();
        return new LoginModal().shouldBeOpened();
    }

    @Step("Нажать «Разместить объявление»")
    public ListingFormPage clickCreateListing() {
        createListingButton.click();
        return new ListingFormPage().shouldBeOpened("Новое объявление");
    }

    @Step("Перейти на главную по логотипу")
    public MainPage goToMainPage() {
        logo.click();
        return new MainPage().shouldBeOpened();
    }

    @Step("Открыть профиль по аватару")
    public ProfilePage openProfile() {
        avatarButton.click();
        return new ProfilePage().shouldBeOpened();
    }

    @Step("Проверить, что пользователь авторизован")
    public Header shouldShowLoggedInUser() {
        logoutButton.shouldBe(visible);
        avatarButton.shouldBe(visible);
        loginButton.shouldNot(exist);
        return this;
    }

    @Step("Проверить, что пользователь не авторизован")
    public Header shouldShowGuest() {
        loginButton.shouldBe(visible);
        logoutButton.shouldNot(exist);
        return this;
    }
}
