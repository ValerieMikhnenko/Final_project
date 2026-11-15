package steps;

import context.ScenarioContext;
import data.TestDataGenerator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import models.User;
import pages.Header;
import pages.MainPage;

public class CommonSteps {

    private final ScenarioContext context;
    private final Header header = new Header();

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Дано("зарегистрирован пользователь")
    public void userIsRegistered() {
        User user = context.userApi().register(TestDataGenerator.randomUser());
        context.setUser(user);
    }

    @Дано("пользователь открыл главную страницу")
    public void openMainPage() {
        new MainPage().open();
    }

    @Дано("пользователь вошёл в систему")
    public void userIsLoggedIn() {
        userIsRegistered();
        openMainPage();
        User user = context.getUser();
        header.openLoginModal().login(user.getEmail(), user.getPassword());
        header.shouldShowLoggedInUser();
    }

    @Тогда("пользователь авторизован на сайте")
    public void userIsAuthorized() {
        header.shouldShowLoggedInUser();
    }

    @Тогда("пользователь не авторизован на сайте")
    public void userIsNotAuthorized() {
        header.shouldShowGuest();
    }
}
