package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"accounts", "oneSideInternalTransactions", "users", "inExternalTransactions", "outExternalTransactions"})
public class OperationalOwner extends Owner{

    private void init() {
        accounts = new ArrayList<>();
        oneSideInternalTransactions = new ArrayList<>();
    }

    public OperationalOwner(){}

    @JsonCreator
    public OperationalOwner(String bankAccountId, OwnerType type) {
        super(bankAccountId, type);
        init();
    }


    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Account> accounts;


    @OneToMany(
            mappedBy = "src",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<OneSideInternalTransaction> oneSideInternalTransactions;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<OneSideInternalTransaction> getOneSideInternalTransactions() {
        return oneSideInternalTransactions;
    }

    public void setOneSideInternalTransactions(List<OneSideInternalTransaction> oneSideInternalTransactions) {
        this.oneSideInternalTransactions = oneSideInternalTransactions;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addOneSideInternalTransactions(OneSideInternalTransaction transaction) {
        this.oneSideInternalTransactions.add(transaction);
    }

}
