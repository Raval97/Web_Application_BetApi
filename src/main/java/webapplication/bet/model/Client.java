package webapplication.bet.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data

@Entity
@Table(name="client")
public class Client {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String placeOdResidence;
    private String street;
    private int nrApartment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String email;
    private String nrOfBankAccount;
    private float betAccountBalance;
    @OneToOne
    @MapsId
    private User user;

    public Client(String name, String surname, String placeOdResidence, String street, int nrApartment,
                  LocalDate dateOfBirth, String email, String nrOfBankAccount, float betAccountBalance) {
        this.name = name;
        this.surname = surname;
        this.placeOdResidence = placeOdResidence;
        this.street = street;
        this.nrApartment = nrApartment;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.nrOfBankAccount = nrOfBankAccount;
        this.betAccountBalance = betAccountBalance;
    }

    public Client() {
    }
}
