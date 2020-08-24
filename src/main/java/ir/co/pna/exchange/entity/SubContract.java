package ir.co.pna.exchange.entity;



import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;
import java.util.Calendar;


@Entity
@DiscriminatorValue("subcontract")
public class SubContract extends Contract {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "parent_id")
    private NormalContract parent;

    public SubContract(){

    }

    public SubContract(int id, NormalContract parent, Calendar expireDate, Owner srcOwner, Owner dstOwner, long valueInRial, long remittanceValue, String description) {
        super(id, expireDate, srcOwner, dstOwner, valueInRial, remittanceValue, description);
        this.parent = parent;
        this.contractStatus = ContractStatus.NONE;

    }


    public Transaction pay(int id, User operator, TransactionOperatorType operatorType, long value) {

        this.setContractStatus(ContractStatus.PAYED);
        this.accounts.get(2).increaseCredit(value);
        TransactionType transactionType = TransactionType.PAYMENT;

        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.parent.getExchangerAccount(), this.accounts.get(2), value);

        return transaction;
    }


    public Transaction claim(int id, User operator, TransactionOperatorType operatorType) {
        long value = this.accounts.get(2).getCredit();
        this.accounts.get(1).setCredit(value);
        this.accounts.get(2).setCredit(0);

        TransactionType transactionType = TransactionType.CLAIM;
        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(2), this.accounts.get(1), value);
        return transaction;
    }

    public Transaction returnFromClaim2Return(int id, TransactionOperatorType operatorType, User operator){

        this.contractStatus = ContractStatus.FAILED;
        long value = this.accounts.get(1).getCredit();
        this.accounts.get(0).setCredit(value);
        this.accounts.get(1).setCredit(0);

        TransactionType transactionType = TransactionType.RETURN_FROM_CLAIM2RETURN;
        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(1), this.accounts.get(0), value);
        return transaction;
    }

    public Transaction returnFromClaim2Exporter(int id, TransactionOperatorType operatorType, User operator){

        this.contractStatus = ContractStatus.SUCCESSFUL;
        long value = this.accounts.get(1).getCredit();
        this.accounts.get(2).setCredit(value);
        this.accounts.get(1).setCredit(0);

        TransactionType transactionType = TransactionType.RETURN_FROM_CLAIM2EXPORTER;
        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(1), this.accounts.get(2), value);
        return transaction;
    }

    // getters and setters .............................................................................................

    public Account getExporterAccount() {
        return accounts.get(2);
    }

    public NormalContract getParent() {
        return parent;
    }
}
