package entities.profiles;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UP")
public class UserProfile extends Profile {
    @Column(name = "passport_key")
    private String passportKey;
}
