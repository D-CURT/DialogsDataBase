package entities.users;

import entities.Answer;
import entities.Question;
import entities.Relations;
import org.hibernate.annotations.ColumnTransformer;
import utils.UserNameConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    @Convert(converter = UserNameConverter.class)
    private String name;

    /*To postgres db need to be added following script: CREATE EXTENSION IF NOT EXISTS pgcrypto;*/
    @Column(name = "passport_key")
    @ColumnTransformer(read = "pgp_sym_decrypt(passport_key::bytea, 'secret')", write = "pgp_sym_encrypt(?, 'secret')")
    private String passportKey;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relations> relations;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, String passportKey) {
        this(name);
        this.passportKey = passportKey;
    }

    public User(int id, String name, String passportKey) {
        this(id, name);
        this.passportKey = passportKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportKey() {
        return passportKey;
    }

    public void setPassportKey(String passportKey) {
        this.passportKey = passportKey;
    }

    public List<Relations> getRelations() {
        return relations;
    }

    public List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();
        relations.forEach(relations1 ->  list.add(relations1.getQuestion()));
        return list;

    }

    public List<Answer> getAnswers() {
        List<Answer> list = new ArrayList<>();
        relations.forEach(relations1 ->  list.add(relations1.getAnswer()));
        return list;
    }

    @Override
    public String toString() {
        return name + ";" + passportKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
