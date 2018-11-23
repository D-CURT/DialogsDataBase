package entities;

import dao.impl.hibernate.AnswerDAO;
import dao.impl.hibernate.QuestionDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50)
    private String name;

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
        return name;
    }
}
