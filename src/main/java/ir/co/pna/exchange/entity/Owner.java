package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Owner {

    @Id
    @Column(name = "bank_account_id")
    @JsonProperty("bank_account_id")
    private String bankAccountId;

    @JsonProperty("owner_type")
    @Column(name = "owner_type")
    protected OwnerType ownerType;

    @OneToMany(
            mappedBy = "dstOwner",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<ExternalTransaction> inExternalTransactions;
    //
    @OneToMany(
            mappedBy = "srcOwner",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<ExternalTransaction> outExternalTransactions;

    private void init() {
        inExternalTransactions = new ArrayList<>();
        outExternalTransactions = new ArrayList<>();
    }

    public Owner(String bankAccountId, OwnerType ownerType) {
        init();
        this.bankAccountId = bankAccountId;
        this.ownerType = ownerType;
    }

    public Owner() {
    }


    public void addInExternalTransactions(ExternalTransaction exTransaction) {
        this.inExternalTransactions.add(exTransaction);
    }

    public void addOutExternalTransactions(ExternalTransaction exTransaction) {
        this.outExternalTransactions.add(exTransaction);
    }

    public List<ExternalTransaction> getInExternalTransactions() {
        return inExternalTransactions;
    }

    public List<ExternalTransaction> getOutExternalTransactions() {
        return outExternalTransactions;
    }


    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public OwnerType getOwnerType() {
        return this.ownerType;

    }
}


