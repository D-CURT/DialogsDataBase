package entities.users;

import entities.Relations;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("A")
public class Administrator extends User {
    @Column(name = "admin_password")
    private String adminPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relations> relations;

    public Administrator() {
    }

    public Administrator(String name, String passportKey, String password) {
        super(name, passportKey);
        this.adminPassword = password;
    }

    public Administrator(String name, String passportKey, String age, String password) {
        super(name, passportKey, age);
        this.adminPassword = password;
    }

    public String getPassword() {
        return adminPassword;
    }

    public void setPassword(String password) {
        this.adminPassword = password;
    }

    @Override
    public List<Relations> getRelations() {
        return relations;
    }

    @Override
    public String toString() {
        return adminPassword;
    }
}
