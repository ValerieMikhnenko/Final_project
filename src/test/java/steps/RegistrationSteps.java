package steps;

import context.ScenarioContext;
import data.TestDataGenerator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import models.User;
import pages.Header;
import pages.RegistrationModal;

public class RegistrationSteps {

    private final ScenarioContext context;
    private final Header header = new Header();
    private final RegistrationModal registrationModal = new RegistrationModal();

    public RegistrationSteps(ScenarioContext context) {
        this.context = context;
    }

    @Дано("пользователь открыл форму регистрации")
    public void openRegistrationForm() {
        header.openLoginModal().goToRegistration();
    }

    @Когда("пользователь регистрируется с новым email")
    public void registerWithNewEmail() {
        User newUser = TestDataGenerator.randomUser();
        context.setUser(newUser);
        registrationModal.fill(newUser.getEmail(), newUser.getPassword()).submit();
    }

    @Когда("пользователь регистрируется с email уже зарегистрированного пользователя")
    public void registerWithExistingEmail() {
        User existing = context.getUser();
        registrationModal.fill(existing.getEmail(), TestDataGenerator.randomPassword()).submit();
    }

    @Тогда("окно регистрации закрывается")
    public void registrationModalClosed() {
        registrationModal.shouldBeClosed();
    }

    @Тогда("пользователь видит ошибку в поле email")
    public void emailErrorShown() {
        registrationModal.shouldShowEmailError();
    }
}
