package ir.co.pna.exchange.entity;

import ir.co.pna.exchange.emum.AccountType;
import ir.co.pna.exchange.exception.EntityBadRequestException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    //    @TableGenerator(name = "ACCOUNT_ID_Gen", initialValue = 10000, allocationSize = 100)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy   = GenerationType.TABLE, generator = "ACCOUNT_ID_Gen")
    private long id;

    @Column(name = "type")
    private AccountType type; // role, could be money exchanger, importer or exporter

    @Column(name = "credit")
    private long credit;

    @Column(name = "expire_date")
    private long expireTime;


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "owner_bank_account_id")
    private OperationalOwner owner;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "srcAccount",
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Transaction> inTransactions;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "dstAccount",
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Transaction> outTransactions;


    private void init() {
        inTransactions = new ArrayList<>();
        outTransactions = new ArrayList<>();
    }


    public Account() {
    }

    public Account(AccountType type, OperationalOwner owner, long expireTime, Contract contract) {

        init();

        owner.addAccount(this);
//        this.id = id;
        this.type = type;
        this.owner = owner;
        this.expireTime = expireTime;
        this.contract = contract;
    }

    public void decreaseCredit(long value) {
        if (this.credit >= value) {
            this.credit = this.credit - value;
        }
        else{
            throw new EntityBadRequestException("not enough credit! for account: " + this.id);
        }
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

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public OperationalOwner getOwner() {
        return owner;
    }

    public void setOwner(OperationalOwner owner) {
        this.owner = owner;
    }

    public List<Transaction> getInTransactions() {
        return inTransactions;
    }

    public void setInTransactions(List<Transaction> inTransactions) {
        this.inTransactions = inTransactions;
    }

    public List<Transaction> getOutTransactions() {
        return outTransactions;
    }

    public void setOutTransactions(List<Transaction> outTransactions) {
        this.outTransactions = outTransactions;
    }

    public void addInTransaction(Transaction transaction) {
        this.inTransactions.add(transaction);
    }

    public void addOutTransaction(Transaction transaction) {
        this.outTransactions.add(transaction);
    }
}
