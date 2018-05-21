package dai.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "bought", nullable = false)
    private boolean bought;

    @Column(name = "sold", nullable = false)
    private int sold;

    public ImageEntity() {
    }

    public ImageEntity(String username, String location, int price) {
        this.username = username;
        this.location = location;
        this.price = price;
        this.bought = false;
        this.sold = 0;
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

    public long getId() {
        return id;
    }

    public boolean isBought() {
        return bought;
    }

    public int getSold() { return sold; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username);
    }
}
