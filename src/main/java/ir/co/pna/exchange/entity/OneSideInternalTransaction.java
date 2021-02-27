package ir.co.pna.exchange.entity;


import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;
import ir.co.pna.exchange.utility.GlobalVariables;

import javax.persistence.*;

@Entity
@DiscriminatorValue("one_side_internal_transaction")
public class OneSideInternalTransaction extends Transaction {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "src_owner_bank_account_id")
    private PublicOwner src;


    public OneSideInternalTransaction() {

    }

    public OneSideInternalTransaction(NormalContract normalContract, User operator, TransactionOperatorType operatorType, TransactionType transactionType, long date) {
        super(normalContract, operator, operatorType, transactionType, normalContract.getValueInRial(), normalContract.getExchangerAccount(), date);
        GlobalVariables.operationalExchangerOwner.addOneSideInternalTransactions(this);
        this.src = normalContract.getSrcPublicOwner();
    }

    public PublicOwner getSrc() {
        return src;
    }

}


