package ir.co.pna.exchange.entity;

import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@DiscriminatorValue("internal_transaction")
public class InternalTransaction extends Transaction {


    public InternalTransaction(){

    }

    public InternalTransaction(Contract contract, NormalUser operator, TransactionOperatorType operatorType, TransactionType transactionType, Account src, Account dst, long value, long date) {
        super(contract, operator, operatorType, transactionType, value, dst, date);
        src.addOutTransaction(this);
        this.srcAccount = src;
    }
}

