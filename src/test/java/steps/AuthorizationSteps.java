package steps;

import context.ScenarioContext;
import io.cucumber.java.ru.Когда;
import models.User;
import pages.Header;

public class AuthorizationSteps {

    private final ScenarioContext context;
    private final Header header = new Header();

    public AuthorizationSteps(ScenarioContext context) {
        this.context = context;
    }

    @Когда("пользователь входит со своим email и паролем")
    public void loginWithOwnCredentials() {
        User user = context.getUser();
        header.openLoginModal().login(user.getEmail(), user.getPassword());
    }
}
