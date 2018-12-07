package entities.users;

import entities.Answer;
import entities.Question;
import entities.Relations;
import org.hibernate.annotations.ColumnTransformer;
import utils.converters.UserNameConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@DiscriminatorValue("U")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Convert(converter = UserNameConverter.class)
    private String name;

    /*To postgres db need to be added following script: CREATE EXTENSION IF NOT EXISTS pgcrypto;*/
    @Column(name = "passport_key")
    @ColumnTransformer(read = "pgp_sym_decrypt(passport_key::bytea, 'secret')", write = "pgp_sym_encrypt(?, 'secret')")
    private String passportKey;

    @Column(name = "age")
    private String age;

    @Convert(converter = UserNameConverter.class)
    private String login;

    @Convert(converter = UserNameConverter.class)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Relations> relations;

    public User() {
    }

    public User(String name, String passportKey) {
        this.name = name;
        this.passportKey = passportKey;
    }

    public User(String login, String password, String name, String passportKey, String age, String role) {
        this(name, passportKey);
        this.login = login;
        this.password = password;
        this.age = age;
        this.role = role;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        return name + ";" + passportKey + ";" + age;
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
