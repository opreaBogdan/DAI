package dai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class ImageEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private int price;

    public ImageEntity() {}

    public ImageEntity(String username, String location, int price){
        this.username = username;
        this.location = location;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }
    public String getLocation() {
        return location;
    }
    public int getPrice() {
        return price;
    }
}
