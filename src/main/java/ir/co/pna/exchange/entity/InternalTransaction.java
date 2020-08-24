package ir.co.pna.exchange.entity;

import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("internaltransaction")
public class InternalTransaction extends Transaction {

    public InternalTransaction(){

    }

    public InternalTransaction(int id, User operator, TransactionOperatorType operatorType, TransactionType transactionType, Account src, Account dst, long value) {
        super(id, operator, operatorType, transactionType, value, dst);
        src.addTransaction(this);
        this.srcAccount = src;
    }

    public Account getSrc() {
        return this.getSrc();
    }


}

