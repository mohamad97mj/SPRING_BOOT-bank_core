package ir.co.pna.exchange.entity;


import ir.co.pna.exchange.emum.*;
import ir.co.pna.exchange.service.Contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@DiscriminatorValue("normalcontract")
public class NormalContract extends Contract {

    @Transient
    ContractService subContractService;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "judge_national_id")
    private Judge judge;

    @OneToMany(mappedBy = "parent", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubContract> subContracts;

    @Column(name = "settlement_type")
    private SettlementType settlementType;

    @Column(name = "remittance_currency")
    private Currency remittanceCurrency;

    @Column(name = "number_of_payed_sub_contracts")
    private int numberOfPayedSubContracts;

    @Column(name = "number_of_successful_sub_contracts")
    private int numberOfSuccessfulSubContracts;


    private void init() {
        subContracts = new ArrayList<SubContract>();
        numberOfPayedSubContracts = 0;
        numberOfSuccessfulSubContracts = 0;
    }

    public NormalContract(){

    }

    public NormalContract(ContractService contractService, int id, SettlementType settlementType, Owner srcOwner, Owner dstOwner, Judge judge, Calendar expireDate, long valueInRial, long remittanceValue, Currency remittanceCurrency, String description) {

        super(id, expireDate, srcOwner, dstOwner, valueInRial, remittanceValue, description);

        judge.addNormalContract(this);

        this.subContractService = contractService;
        this.judge = judge;
        this.settlementType = settlementType;
        this.remittanceCurrency = remittanceCurrency;
        this.contractStatus = ContractStatus.NOT_CHARGED;


        init();
    }

    public SubContract createSubContract(int id, Calendar expireDate, Owner srcOwner, Owner dstOwner, long valueInRial, long remittanceValue, String description) {
        SubContract newSubContract = new SubContract(id, this, expireDate, srcOwner, dstOwner, valueInRial, remittanceValue, description);

        subContractService.save(newSubContract);

        subContracts.add(newSubContract);
        return newSubContract;
    }

    public int getSubContractIndex(long subContractId) {
        for (int i = 0; i < this.subContracts.size(); i++) {
            if (subContracts.get(i).getId() == subContractId) {
                return i;
            }
        }
        return -1;
    }

    public Transaction charge(int id, User operator, Owner owner, long value, TransactionOperatorType operatorType) {
        this.contractStatus = ContractStatus.CHARGED;
        this.accounts.get(2).setCredit(value);

        TransactionType transactionType = TransactionType.CHARGE;
        Transaction transaction = new OneSideInternalTransaction(id, operator, operatorType, transactionType, owner, this.accounts.get(2), value);
        return transaction;
    }

    public Transaction pay(int id, User operator, long subContractId, long value, TransactionOperatorType operatorType) {
        numberOfPayedSubContracts++;
        if (numberOfPayedSubContracts == subContracts.size()) {
            this.contractStatus = ContractStatus.PAYED;

        }

        this.accounts.get(2).decreaseCredit(value);

        SubContract subContract = this.subContracts.get(getSubContractIndex(subContractId));
        Transaction transaction = subContract.pay(id, operator, operatorType, value);

        return transaction;

    }

    public Transaction[] claim(int id, int[] childrenIds, User operator, TransactionOperatorType operatorType) { // is called after a claim happens

        Transaction[] transactions = null;

//        if (childrenIds.length == subContracts.size()) {
        this.contractStatus = ContractStatus.CLAIMED;
        long value = this.accounts.get(2).getCredit();
        this.accounts.get(1).setCredit(value);
        this.accounts.get(2).setCredit(0);

        TransactionType transactionType = TransactionType.CLAIM;
        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(2), this.accounts.get(1), value);

        transactions = new Transaction[subContracts.size() + 1];
        transactions[0] = transaction;

        for (int i = 0; i < subContracts.size(); i++) {
            transactions[i + 1] = subContracts.get(i).claim(childrenIds[i], operator, operatorType);
        }
//        } else {
//            System.out.println("Error: claim request fail for contract: " + this.getId() + " - child length does not match the subcontracts size");
//        }


        return transactions;
    }

    public Transaction returnFromExchanger2Return(int id, TransactionOperatorType operatorType, User operator) { // is called when contract is NOT_PAYED
        long value = this.accounts.get(2).getCredit();

        this.accounts.get(0).setCredit(value);
        this.accounts.get(2).setCredit(0);

        TransactionType transactionType = TransactionType.RETURN_FROM_EXCHANGER2RETURN;
        Transaction transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(2), this.accounts.get(0), value);

        return transaction;
    }


    public Transaction returnFromClaim2Return(int id, int contractId, TransactionOperatorType operatorType, User operator) { // is called after fail in judgement

        if (this.numberOfSuccessfulSubContracts == 0) {
            this.contractStatus = ContractStatus.FAILED;
        }

        Transaction transaction;
        TransactionType transactionType = TransactionType.RETURN_FROM_CLAIM2RETURN;

        if (contractId == this.id) {
            long value = this.accounts.get(1).getCredit();
            this.accounts.get(0).setCredit(value);
            this.accounts.get(1).setCredit(0);
            transaction = new InternalTransaction(id, operator, operatorType, transactionType, this.accounts.get(1), this.accounts.get(0), value);
        } else {
            transaction = subContracts.get(getSubContractIndex(contractId)).returnFromClaim2Return(id, operatorType, operator);
        }

        return transaction;
    }

    public Transaction returnFromClaim2Exporter(int id, int subContractId, TransactionOperatorType operatorType, User operator) { // is called after success in judgement

        numberOfSuccessfulSubContracts++;

        if (this.numberOfSuccessfulSubContracts == this.subContracts.size()) {
            this.contractStatus = ContractStatus.SUCCESSFUL;
        } else {
            this.contractStatus = ContractStatus.SEMI_SUCCESSFUL;
        }

        SubContract subContract = this.subContracts.get(getSubContractIndex(subContractId));
        Transaction transaction = subContract.returnFromClaim2Exporter(id, operatorType, operator);


        return transaction;
    }

    // getters and setters .............................................................................................

    public Currency getRemittanceCurrency() {
        return remittanceCurrency;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public List<SubContract> getSubContracts() {
        return subContracts;
    }

    public Account getExchangerAccount() {
        return accounts.get(2);
    }

    public Judge getJudge() {
        return judge;
    }

    public void countSuccessfulContracts() {
        this.numberOfSuccessfulSubContracts = this.numberOfSuccessfulSubContracts++;
    }

}
