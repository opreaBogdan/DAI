package dai.mapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import dai.entities.ImageEntity;

public class Image {
    @JsonProperty("id")
    private long id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("price")
    private int price;
    @JsonProperty("bought")
    private boolean bought;

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Image(long id, String userName, String location, int price, boolean bought) {
        this.userName = userName;
        this.location = location;
        this.id = id;
        this.price = price;
        this.bought = bought;
    }

    public Image(ImageEntity imageEntity) {
        this.userName = imageEntity.getUsername();
        this.location = imageEntity.getLocation();
        this.id = imageEntity.getId();
        this.price = imageEntity.getPrice();
        this.bought = imageEntity.isBought();
    }

    public static class Builder {
        long id;
        String userName;
        String location;
        int price;
        boolean bought = false;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder bought() {
            this.bought = true;
            return this;
        }

        public Image build() {
            return new Image(this.id, this.userName, this.location, this.price, this.bought);
        }
    }

}
