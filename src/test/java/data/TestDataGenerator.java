package data;

import models.Listing;
import models.User;

import java.util.List;
import java.util.Random;

public class TestDataGenerator {

    private static final String AUTHOR = "Valerie Mikhnenko";
    private static final Random random = new Random();

    public static final List<String> CATEGORIES = List.of("Авто", "Книги", "Садоводство", "Хобби", "Технологии");
    public static final List<String> CITIES = List.of(
            "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Нижний Новгород", "Казань");
    public static final List<String> CONDITIONS = List.of("Новый", "Б/У");

    public static User randomUser() {
        return new User(uniqueEmail(), randomPassword());
    }

    // время в миллисекундах делает email уникальным при каждом запуске
    public static String uniqueEmail() {
        return "valerie.mikhnenko." + System.currentTimeMillis() + random.nextInt(100) + "@test.ru";
    }

    public static String randomPassword() {
        return "Valerie" + (1000 + random.nextInt(9000));
    }

    public static Listing randomListing() {
        return randomListing(randomItem(CATEGORIES));
    }

    public static Listing randomListing(String category) {
        return new Listing(
                uniqueListingName(),
                category,
                randomItem(CONDITIONS),
                randomItem(CITIES),
                "Тестовое объявление " + AUTHOR,
                100 + random.nextInt(10000));
    }

    public static String uniqueListingName() {
        return AUTHOR + " " + (10000 + random.nextInt(90000));
    }

    private static String randomItem(List<String> items) {
        return items.get(random.nextInt(items.size()));
    }
}
