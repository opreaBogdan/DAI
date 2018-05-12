package dai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "validated")
    private Boolean validated;

    public UserEntity() {}

    public UserEntity(String username, String password, String type, String email, String token){
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
        this.token = token;
        this.validated = false;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getType() {
        return type;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }
    public boolean isValidated() { return validated; }
}
