package dai.entities;

import javax.persistence.*;

@Entity
@Table(name="transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "time", nullable = false)
    private long time;

    public TransactionEntity() {}

    public TransactionEntity(String username, long time){
        this.username = username;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }
    public long getTime() {return time;}
    public long getId() {return id;}
}
