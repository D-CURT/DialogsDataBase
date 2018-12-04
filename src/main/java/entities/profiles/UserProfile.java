package entities.profiles;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile extends Profile {

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