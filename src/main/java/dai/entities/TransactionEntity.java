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

    @Column(name = "command", nullable = false)
    private String command;

    @Column(name = "time", nullable = false)
    private long time;

    public TransactionEntity() {}

    public TransactionEntity(String username, String command, long time){
        this.username = username;
        this.command = command;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }
    public String getCommand() { return command; }
    public long getTime() {return time;}
    public long getId() {return id;}
}
