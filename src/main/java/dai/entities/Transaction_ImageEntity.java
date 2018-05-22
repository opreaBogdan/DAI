package dai.entities;

import javax.persistence.*;

@Entity
@Table(name="transaction_images")
public class Transaction_ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "image_id", nullable = false)
    private long image_id;

    @Column(name = "transaction_id", nullable = false)
    private long transaction_id;

    public Transaction_ImageEntity() {}

    public Transaction_ImageEntity(long image_id, long transaction_id){
        this.image_id = image_id;
        this.transaction_id = transaction_id;
    }

    public long getImage_id() { return image_id; }
    public long getTransaction_id() { return transaction_id; }
}
