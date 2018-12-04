package entities.profiles;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile extends Profile {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "user_profile_id_seq", allocationSize = 1)
    private int id;*/

    @Column(name = "content")
    private String content;

    public UserProfile() {
    }

    public UserProfile(String owner, String content) {
        super(owner);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}