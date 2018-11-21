package entities;

import javax.persistence.*;

@Entity
@Table(name = "relations")
public class Relations {
    @Column(name = "id_user")
    private int id_user;
    @Column(name = "id_question")
    private int id_question;
    @Column(name = "id_answer")
    private int id_answer;

    public Relations() {
    }

    public Relations(int id_user, int id_question) {
        this.id_user = id_user;
        this.id_question = id_question;
    }

    public Relations(int id_user, int id_question, int id_answer) {
        this.id_user = id_user;
        this.id_question = id_question;
        this.id_answer = id_answer;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public int getId_answer() {
        return id_answer;
    }

    public void setId_answer(int id_answer) {
        this.id_answer = id_answer;
    }
}
