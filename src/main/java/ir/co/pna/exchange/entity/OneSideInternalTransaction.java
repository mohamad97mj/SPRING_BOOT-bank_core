package ir.co.pna.exchange.entity;


import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;

@Entity
@DiscriminatorValue("onesideinternaltransaction")
public class OneSideInternalTransaction extends Transaction{

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "src_owner_bank_account_id")
    private Owner src;

    public OneSideInternalTransaction(){

    }

    public OneSideInternalTransaction(int id, User operator, TransactionOperatorType operatorType, TransactionType transactionType, Owner src, Account dst, long value) {
        super(id, operator, operatorType, transactionType, value, dst);
        src.addOneSideInternalTransactions(this);
        this.src = src;
    }

    public Owner getSrc() {
        return src;
    }

}


