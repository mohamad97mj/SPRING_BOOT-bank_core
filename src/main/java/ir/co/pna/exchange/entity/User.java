package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "myuser")
@JsonIgnoreProperties({"publicOwners", "transactions", "id"})
public class User extends NormalUser{

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "users"
    )
    private List<PublicOwner> publicOwners;


    @OneToMany(
            mappedBy = "operator",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    List<Transaction> transactions;


    private void init() {
        publicOwners = new ArrayList<PublicOwner>();
    }


    public User() {

    }


    public User(String firstName, String lastName, String id) {
        init();

        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public void own(PublicOwner publicOwner) {
        this.publicOwners.add(publicOwner);
    }

    //custom serializing ...............................................................................................

    @JsonGetter("national_code")
    public String getNationalCode() {
        return getId();
    }


    // getters and setters .............................................................................................


    public void setId(String nationalCode) {
        this.id = nationalCode;
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

    public List<PublicOwner> getPublicOwners() {
        return publicOwners;
    }

    public void setPublicOwners(List<PublicOwner> publicOwners) {
        this.publicOwners = publicOwners;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
