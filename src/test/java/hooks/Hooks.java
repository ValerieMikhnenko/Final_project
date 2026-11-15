package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.Config;
import context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.selenide.AllureSelenide;
import models.User;

public class Hooks {

    private final ScenarioContext context;

    public Hooks(ScenarioContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setUpBrowser() {
        Configuration.baseUrl = Config.BASE_URL;
        Configuration.browser = System.getProperty("selenide.browser", "chrome");
        Configuration.browserSize = System.getProperty("selenide.browserSize", "1920x1080");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "false"));
        Configuration.timeout = 10_000;
        Configuration.pageLoadTimeout = 30_000;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }

    @After(order = 10)
    public void closeBrowser() {
        Selenide.closeWebDriver();
    }

    // удаляем свои объявления после теста, стенд общий
    @After(order = 20)
    public void cleanUpListings() {
        User user = context.getUser();
        if (user != null && user.getToken() != null) {
            try {
                context.listingApi().deleteAll(user.getToken());
            } catch (RuntimeException ignored) {
                // если удаление не получилось, тест не роняем
            }
        }
    }
}
