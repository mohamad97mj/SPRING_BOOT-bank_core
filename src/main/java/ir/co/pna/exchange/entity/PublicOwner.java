package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "public_owner")
public class PublicOwner extends Owner {


    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "public_owner_user",
            joinColumns = @JoinColumn(name = "owner_bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "user_national_code")
    )
    private List<User> users;


    private void init() {
        users = new ArrayList<>();
    }

    public PublicOwner() {

    }

    @JsonCreator
    public PublicOwner(String bankAccountId, OwnerType type, String mobileNumber) {
        super(bankAccountId, type, mobileNumber);
        init();
    }


    //custom serializing ...............................................................................................


    // getters and setters .............................................................................................

    public void addUser(User user) {
        this.users.add(user);
        user.own(this);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}


