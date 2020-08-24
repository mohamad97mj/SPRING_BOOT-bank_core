package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "myuser")
@JsonIgnoreProperties({"owners", "transactions"})
public class User {

    @Id
    @Column(name = "national_code")
    @JsonProperty("national_code")
    private long nationalCode;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @ManyToMany(fetch= FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "owner_user",
            joinColumns = @JoinColumn(name = "user_national_code"),
            inverseJoinColumns = @JoinColumn(name = "owner_bank_account_id")
    )
    private List<Owner> owners;


    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    List<Transaction> transactions;


    private void init(){
        owners = new ArrayList<Owner>();
    }


    public User(){

    }



    public User(String firstName, String lastName, String strNationalCode) {
        init();

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = Long.parseUnsignedLong(strNationalCode);
    }

    public void own (Owner owner) {
        this.owners.add(owner);
    }


    // getters and setters .............................................................................................


    public long getNationalCode() {
        return nationalCode;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<Owner> listOfOwners) {
        this.owners = listOfOwners;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setNationalCode(long nationalCode) {
        this.nationalCode = nationalCode;
    }


}
