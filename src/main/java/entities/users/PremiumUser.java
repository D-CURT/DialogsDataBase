package entities.users;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PU")
public class PremiumUser extends User {
    @Column(name = "credit_card")
    @ColumnTransformer(read = "pgp_sym_decrypt(credit_card::bytea, 'card')", write = "pgp_sym_encrypt(?, 'card')")
    private String creditCard;

    public PremiumUser() {
    }

    public PremiumUser(String name, String passportKey, String creditCard) {
        super(name, passportKey);
        this.creditCard = creditCard;
    }

    public PremiumUser(int id, String name, String passportKey, String creditCard) {
        super(id, name, passportKey);
        this.creditCard = creditCard;
    }

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