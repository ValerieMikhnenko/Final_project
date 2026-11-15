package context;

import api.ListingApi;
import api.UserApi;
import models.Listing;
import models.User;

public class ScenarioContext {

    private final UserApi userApi = new UserApi();
    private final ListingApi listingApi = new ListingApi();

    private User user;
    private Listing listing;

    public UserApi userApi() {
        return userApi;
    }

    public ListingApi listingApi() {
        return listingApi;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
