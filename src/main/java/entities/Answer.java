package entities;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@OneToMany(mappedBy = "id_answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private int id;
    @Column(name = "content")
    private String content;

    public Answer() {
    }

    public Answer(String content) {
        this.content = content;
    }

    public Answer(int id, String content) {
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
