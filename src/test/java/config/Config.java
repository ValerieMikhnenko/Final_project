package config;

public final class Config {

    public static final String BASE_URL =
            System.getProperty("base.url", "https://qa-desk.education-services.ru");

    public static final String API_URL =
            System.getProperty("api.url", BASE_URL + "/api");

    private Config() {
    }
}
