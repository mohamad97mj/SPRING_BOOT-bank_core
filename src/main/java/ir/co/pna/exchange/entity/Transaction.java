package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "amount")
    private long amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "operator_type")
    private TransactionOperatorType operatorType;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "operator_id")
    private NormalUser operator;

    @Column(name = "date")
    @JsonProperty("date")
    private long date;

//    TODO to change this many to many to two one to many

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "src_account_id")
    protected Account srcAccount;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "dst_account_id")
    protected Account dstAccount;

    @OneToOne(
            mappedBy = "internalTransaction",
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private ExternalTransaction externalTransaction;




    private void init() {
    }

    public Transaction() {

    }

    public Transaction(Contract contract, NormalUser operator, TransactionOperatorType operatorType, TransactionType transactionType, long amount, Account dst, long date) {

        init();

        this.contract = contract;
        this.operator = operator;
        this.operatorType = operatorType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;

        dst.addInTransaction(this);
        this.dstAccount = dst;
    }


    // getters and setters .............................................................................................

    public void setExternalTransaction(ExternalTransaction externalTransaction) {
        this.externalTransaction = externalTransaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    public TransactionOperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(TransactionOperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public NormalUser getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Account getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(Account srcAccount) {
        this.srcAccount = srcAccount;
    }

    public Account getDstAccount() {
        return dstAccount;
    }

    public void setDstAccount(Account dstAccount) {
        this.dstAccount = dstAccount;
    }

    public ExternalTransaction getExternalTransaction() {
        return externalTransaction;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }
}
