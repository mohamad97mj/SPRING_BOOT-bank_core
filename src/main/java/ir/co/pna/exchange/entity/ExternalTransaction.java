package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;

@Entity
@Table(name = "external_transaction")
@JsonIgnoreProperties({"id", "internalTransaction"})
@JsonPropertyOrder({"transaction_type", "relevant_contract_id", "src_owner_bank_account_id", "src_owner_type", "dst_owner_bank_account_id", "dst_owner_type", "amount", "operator_type", "operator_id", "date"})
public class ExternalTransaction {

    @Id
    @Column(name = "bank_transaction_id")
    @JsonProperty("bank_transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bankTransactionId;

    @Column(name = "date")
    @JsonProperty("date")
    private long date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "src_owner_bank_account_id")
    @JsonProperty("src_owner_bank_account_id")
    private Owner srcOwner;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dst_owner_bank_account_id")
    @JsonProperty("dst_owner_bank_account_id")
    private Owner dstOwner;


    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "internal_transaction_id")
//    @JsonProperty("internal_transaction_id")
    private Transaction internalTransaction;


    public ExternalTransaction() {

    }

    public ExternalTransaction(int bankTransactionId, Transaction internalTransaction, Owner srcOwner, Owner dstOwner, long date) {

        this.bankTransactionId = bankTransactionId;
        srcOwner.addOutExternalTransactions(this);
        dstOwner.addInExternalTransactions(this);
        this.srcOwner = srcOwner;
        this.dstOwner = dstOwner;
        internalTransaction.setExternalTransaction(this);
        this.internalTransaction = internalTransaction;
        this.date = date;
    }


    //custom serializing ...............................................................................................

    @JsonGetter("src_owner_bank_account_id")
    public String getSrcOwnerJson() {
        return this.srcOwner.getBankAccountId();
    }

    @JsonGetter("dst_owner_bank_account_id")
    public String getDstOwnerJson() {
        return this.dstOwner.getBankAccountId();
    }

    @JsonGetter("transaction_type")
    public TransactionType getTypeJson() {
        return this.internalTransaction.getTransactionType();
    }

    @JsonGetter("relevant_contract_id")
    public int getRelevantContractIdJson() {
        return this.internalTransaction.getContract().getId();
    }

    @JsonGetter("src_owner_type")
    public OwnerType getSrcOwnerTypeJson() {
        return this.srcOwner.getOwnerType();
    }

    @JsonGetter("dst_owner_type")
    public OwnerType getDstOwnerTypeJson() {
        return this.dstOwner.getOwnerType();
    }

    @JsonGetter("amount")
    public long getInternalTransactionAmountJson() {
        return this.internalTransaction.getAmount();
    }

    @JsonGetter("operator_type")
    public TransactionOperatorType getOperatorTypeJson() {
        return this.internalTransaction.getOperatorType();
    }

    @JsonGetter("operator_id")
    public String getOperatorNationalCodeJson() {
        return this.internalTransaction.getOperator().getId();
    }

    // getters and setters .............................................................................................

    public int getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(int bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public Transaction getInternalTransaction() {
        return internalTransaction;
    }

    public Owner getSrcOwner() {
        return srcOwner;
    }

    public Owner getDstOwner() {
        return dstOwner;
    }

    public long getDate() {
        return date;
    }
}
