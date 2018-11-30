package entities.users;

import entities.Relations;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("PU")
public class PremiumUser extends User {
    @Column(name = "credit_card")
    @ColumnTransformer(read = "pgp_sym_decrypt(credit_card::bytea, 'card')", write = "pgp_sym_encrypt(?, 'card')")
    private String creditCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relations> relations;

    public PremiumUser() {
    }

    public PremiumUser(String name, String passportKey, String creditCard) {
        super(name, passportKey);
        this.creditCard = creditCard;
    }

    public PremiumUser(String name, String passportKey, String age, String creditCard) {
        super(name, passportKey, age);
        this.creditCard = creditCard;
    }

    /*public PremiumUser(int id, String name, String passportKey, String creditCard) {
        super(name, passportKey);
        super.setId(id);
        this.creditCard = creditCard;
    }*/

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return creditCard;
    }
}