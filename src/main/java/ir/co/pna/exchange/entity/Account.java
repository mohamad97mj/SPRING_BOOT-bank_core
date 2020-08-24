package ir.co.pna.exchange.entity;
import ir.co.pna.exchange.emum.AccountType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private AccountType type; // role, could be money exchanger, importer or exporter

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "owner_bank_account_id")
    private Owner owner;

    @Column(name = "expire_date")
    private Calendar expireTime;

    @Column(name = "credit")
    private long credit;

    @OneToMany(mappedBy = "srcAccount")
    private List<Transaction> inTransactions;

    @OneToMany(mappedBy = "dstAccount")
    private List<Transaction> outTransactions;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions;

    private void init() {
        transactions = new ArrayList<>();
    }


    public Account(){ }

    public Account(long id, AccountType type, Owner owner, Calendar expireTime, Contract contract) {

        init();

        owner.addAccount(this);
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.expireTime = expireTime;
        this.contract = contract;
    }

    public void decreaseCredit(long value) {
        this.credit = this.credit - value;
    }

    public void increaseCredit(long value) {
        this.credit = this.credit + value;
    }

    // getters and setters .............................................................................................

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Calendar getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Calendar expireTime) {
        this.expireTime = expireTime;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getCredit() {
        return credit;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
}
