package entities;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "content", length = 50)
    private String content;

    public Question() {
    }

    public Question(String content) {
        this.content = content;
    }

    public Question(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
