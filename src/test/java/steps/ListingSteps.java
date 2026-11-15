package steps;

import context.ScenarioContext;
import data.TestDataGenerator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import models.Listing;
import pages.Header;
import pages.MainPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListingSteps {

    private final ScenarioContext context;
    private final Header header = new Header();
    // старое название, чтобы потом проверить, что его больше нет
    private String nameBeforeEdit;

    public ListingSteps(ScenarioContext context) {
        this.context = context;
    }

    @Дано("у пользователя есть опубликованное объявление")
    public void userHasListing() {
        Listing listing = context.listingApi()
                .create(context.getUser().getToken(), TestDataGenerator.randomListing());
        context.setListing(listing);
    }

    @Когда("пользователь размещает объявление в категории {string}")
    public void createListing(String category) {
        Listing listing = TestDataGenerator.randomListing(category);
        context.setListing(listing);
        header.clickCreateListing().fill(listing).submit();
    }

    @Когда("пользователь меняет название своего объявления")
    public void editListingName() {
        Listing listing = context.getListing();
        nameBeforeEdit = listing.getName();
        String newName = TestDataGenerator.uniqueListingName();
        header.openProfile()
                .editListing(nameBeforeEdit)
                .setName(newName)
                .submit();
        listing.setName(newName);
    }

    @Когда("пользователь удаляет своё объявление")
    public void deleteListing() {
        // открываем по адресу, а не по логотипу, иначе лента может не обновиться
        new MainPage().open()
                .openListing(context.getListing().getName())
                .delete();
    }

    @Тогда("объявление отображается в разделе «Мои объявления»")
    public void listingShownInProfile() {
        header.openProfile().shouldHaveListing(context.getListing().getName());
    }

    @Тогда("в разделе «Мои объявления» отображается объявление с новым названием")
    public void editedListingShownInProfile() {
        header.openProfile()
                .shouldHaveListing(context.getListing().getName())
                .shouldNotHaveListing(nameBeforeEdit);
    }

    @Тогда("объявления нет в разделе «Мои объявления»")
    public void listingNotShownInProfile() {
        String name = context.getListing().getName();
        header.openProfile().shouldNotHaveListing(name);
        // проверяем ещё и через API, что объявление удалено на сервере
        List<String> namesOnServer = context.listingApi().ownListingNames(context.getUser().getToken());
        assertFalse(namesOnServer.contains(name), "Объявление «" + name + "» всё ещё есть на сервере");
    }
}
