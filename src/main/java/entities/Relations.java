package entities;

import javax.persistence.*;

@Entity
@Table(name = "relations")
public class Relations {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_answer")
    private Answer answer;

    public Relations() {
    }

    public Relations(User user, Question question) {
        this.user = user;
        this.question = question;
    }

    public Relations(User user, Question question, Answer answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return fieldsToString();
    }

    private String fieldsToString() {
        StringBuilder builder = new StringBuilder();
        builder.append(user).append(';');
        builder.append(question).append(';');
        builder.append(answer != null ? answer : "the answer is not received yet");
        return builder.toString();
    }
}
