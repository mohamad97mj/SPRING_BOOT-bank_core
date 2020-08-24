package ir.co.pna.exchange.entity;


import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "transaction")
public abstract class Transaction {

    @Id
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "operator_type")
    private TransactionOperatorType operatorType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "operator_national_code")
    private User operator;

    @Column(name = "transaction_type")
    private TransactionType type;

    @Column(name = "amount")
    private long amount;

//    TODO to change this many to many to two one to many

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "src_account_id")
    protected Account srcAccount;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "dst_account_id")
    protected Account dstAccount;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "external_transaction_id")
    private ExternalTransaction externalTransaction;


    private void init() {
    }

    public Transaction() {

    }

    public Transaction(int id, User operator, TransactionOperatorType operatorType, TransactionType transactionType, long amount, Account dst) {

        init();

        this.id = id;
        this.operator = operator;
        this.operatorType = operatorType;
        this.type = transactionType;
        this.amount = amount;

        dst.addTransaction(this);
        this.dstAccount = dst;
    }


    // getters and setters .............................................................................................

    public void setExternalTransaction(ExternalTransaction externalTransaction) {
        this.externalTransaction = externalTransaction;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionOperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(TransactionOperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public ExternalTransaction getExternalTransaction() {
        return externalTransaction;
    }

    public Account getDst() {
        return dstAccount;
    }

    public void setId(int id) {
        this.id = id;
    }
}
