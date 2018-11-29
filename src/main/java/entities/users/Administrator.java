package entities.users;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Administrator extends User {
    @Column(name = "admin_password")
    private String password;

    public Administrator() {
    }

    public Administrator(String name, String passportKey, String password) {
        super(name, passportKey);
        this.password = password;
    }

    public Administrator(int id, String name, String passportKey, String password) {
        super(id, name, passportKey);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return password;
    }
}
