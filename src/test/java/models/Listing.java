package models;

public class Listing {

    private Integer id;
    private String name;
    private String category;
    private String condition;
    private String city;
    private String description;
    private int price;

    public Listing(String name, String category, String condition, String city, String description, int price) {
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.city = city;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Listing{name='" + name + "', category='" + category + "', city='" + city + "', price=" + price + "}";
    }
}
