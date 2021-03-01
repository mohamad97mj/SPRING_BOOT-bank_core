package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.*;
import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.emum.*;
import ir.co.pna.exchange.exception.EntityBadRequestException;
import ir.co.pna.exchange.utility.GlobalVariables;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Entity
@Configurable(preConstruction = true)
@JsonIgnoreProperties({"subcontracts", "returnAccount", "claimAccount", "exchangerAccount", "numberOfPayedSubcontracts", "numberOfSuccessfulSubcontracts", "numberOfJudgedSubcontracts"})
@JsonPropertyOrder({"id", "src_owner_bank_account_id", "dst_owner_bank_account_id", "value_in_rial", "remittance_currency", "remittance_value", "settlement_type", "judge_name", "judge_national_id", "judge_vote", "expire_date", "status", "description", "payment_id", "available_value_in_rial", "parentable"})
public class NormalContract extends Contract {

//    @Transient
//    SubcontractService subContractService;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "judge_national_id")
    @JsonProperty("judge_national_id")
    private Judge judge;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
//            CascadeType.PERSIST,
                    CascadeType.MERGE,
//            CascadeType.DETACH,
                    CascadeType.REFRESH}
    )
    @JoinColumn(name = "src_owner_id")
    @JsonProperty("src_owner_bank_account_id")
    private PublicOwner srcPublicOwner;

    @Column(name = "settlement_type")
    @JsonProperty("settlement_type")
    private SettlementType settlementType;

    @Column(name = "remittance_currency")
    @JsonProperty("remittance_currency")
    private Currency remittanceCurrency;

    @Column(name = "number_of_successful_subcontracts")
    protected int numberOfSuccessfulSubcontracts;

    @Column(name = "number_of_failed_subcontracts")
    protected int numberOfFailedSubcontracts;

    @Column(name = "number_of_judged_subcontracts")
    protected int numberOfJudgedSubcontracts;

    @Column(name = "is_parentable")
    @JsonProperty("is_parentable")
    private boolean isParentable;


    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Subcontract> subcontracts;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "exchanger_account_id")
    protected Account exchangerAccount;


    @Column(name = "available_value_in_rial")
    @JsonProperty("available_value_in_rial")
    protected long availableValueInRial;


    private void init() {
        this.subcontracts = new ArrayList<>();
        this.numberOfJudgedSubcontracts = 0;
        this.numberOfSuccessfulSubcontracts = 0;
        this.numberOfFailedSubcontracts = 0;
        this.judgeVote = JudgeVote.NOT_CLAIMED;
        this.isParentable = true;
    }

    public NormalContract() {

    }

    public NormalContract(
//            SubcontractService subcontractService,
            SettlementType settlementType, PublicOwner srcPublicOwner, PublicOwner dstPublicOwner, Judge judge, long expireDate, long valueInRial, long remittanceValue, Currency remittanceCurrency, String description) {

        super(expireDate, dstPublicOwner, valueInRial, remittanceValue, description);

        ((Importer) srcPublicOwner).addOutNormalContract(this);
        ((Exchanger) dstPublicOwner).addInNormalContract(this);

        judge.addNormalContract(this);

        this.srcPublicOwner = srcPublicOwner;
        this.judge = judge;
        this.settlementType = settlementType;
        this.remittanceCurrency = remittanceCurrency;
        this.status = ContractStatus.WAITING_FOR_EXCHANGER_ACCEPTANCE;
        this.availableValueInRial = valueInRial;
        init();
    }

    public Subcontract createSubcontract(long expireDate, PublicOwner dstPublicOwner, long valueInRial, long remittanceValue, String description) {
        if (this.isParentable) {
            if (valueInRial <= this.availableValueInRial) {
                this.availableValueInRial -= valueInRial;
                Subcontract newSubcontract = new Subcontract(this, expireDate, dstPublicOwner, valueInRial, remittanceValue, description);
                subcontracts.add(newSubcontract);
                if (this.settlementType == SettlementType.SINGLE) {
                    isParentable = false;
                }
                return newSubcontract;
            } else {
                throw new EntityBadRequestException("normal contract with id " + this.id + " has no available value in rial");
            }
        }
        throw new EntityBadRequestException("parent contract with id " + this.id + " is not multiple settlement type");
    }

    private int getSubContractIndex(int subContractId) {
        for (int i = 0; i < this.subcontracts.size(); i++) {
            if (subcontracts.get(i).getId() == subContractId) {
                return i;
            }
        }
        return -1;
    }

    public void charge(User operator, TransactionOperatorType operatorType, SmsClient smsClient) {
        if (this.status == ContractStatus.WAITING_FOR_IMPORTER_PAYMENT) {
            // to check whether payment is really done or not

            this.status = ContractStatus.DOING_BY_EXCHANGER;
            this.exchangerAccount.setCredit(this.valueInRial);

            String message = "واریز به حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی صراف ها)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(this.valueInRial) + "ریال";
            SendSMSResponse smsResponse = smsClient.sendSms(GlobalVariables.operationalExchangerOwner.getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
            System.out.println(smsResponse.toString());
            System.err.println(smsResponse.getSendSMSResult());

            TransactionType transactionType = TransactionType.CHARGE;
            Transaction transaction = new OneSideInternalTransaction(this, operator, operatorType, transactionType, GlobalVariables.getNow());
            ExternalTransaction exTransaction = new ExternalTransaction(0, transaction, this.srcPublicOwner, GlobalVariables.operationalExchangerOwner, GlobalVariables.getNow());
        } else {
            throw new EntityBadRequestException("normal contract with id" + this.id + "can not be charged");
        }
    }

    public void claim(User operator, TransactionOperatorType operatorType, SmsClient smsClient, YaghutClient yaghutClient) { // is called after a claim happens

        if (this.status == ContractStatus.WAITING_FOR_IMPORTER_CONFIRMATION) {
            this.close();
            Transaction[] transactions = new Transaction[subcontracts.size()];
            ExternalTransaction[] exTransaction = new ExternalTransaction[subcontracts.size()];
            this.status = ContractStatus.CLAIMED_BY_IMPORTER;
            this.judgeVote = JudgeVote.NOT_JUDGED;
            this.returnFromExchanger2Return(operator, operatorType);
            for (int i = 0; i < subcontracts.size(); i++) {
                transactions[i] = subcontracts.get(i).claim(operatorType, operator, smsClient, yaghutClient);
                exTransaction[i] = new ExternalTransaction(0, transactions[i], GlobalVariables.operationalExporterOwner, GlobalVariables.operationalClaimOwner, GlobalVariables.getNow());
            }
        } else {
            throw new EntityBadRequestException("normal contract with id" + this.id + "can not be claimed");
        }
    }


    //    to transfer remaining money from exchanger account to return account
    public void returnFromExchanger2Return(User operator, TransactionOperatorType operatorType) { // is called when contract is NOT_PAYED
        long value = this.exchangerAccount.getCredit();

        this.exchangerAccount.setCredit(0);
        this.returnAccount.setCredit(value);

        // sms
        //transfer

        TransactionType transactionType = TransactionType.RETURN_REMAINING;
        Transaction transaction = new InternalTransaction(this, operator, operatorType, transactionType, this.exchangerAccount, this.returnAccount, value, GlobalVariables.getNow());
        ExternalTransaction exTransaction = new ExternalTransaction(0, transaction, GlobalVariables.operationalExchangerOwner, GlobalVariables.operationalReturnOwner, GlobalVariables.getNow());
    }

    public void judge() {
        if (numberOfJudgedSubcontracts == this.subcontracts.size()) {
            this.status = ContractStatus.JUDGED;
            if (numberOfSuccessfulSubcontracts == this.subcontracts.size()) {
                this.judgeVote = JudgeVote.DONE;
            } else if (numberOfFailedSubcontracts == this.subcontracts.size()) {
                this.judgeVote = JudgeVote.NOT_DONE;
            } else {
                this.judgeVote = JudgeVote.SEMI_DONE;
            }
        }
    }

    //custom serializing ...............................................................................................

    @JsonGetter("src_owner_bank_account_id")
    public String getSrcOwnerJson() {
        return this.srcPublicOwner.getBankAccountId();
    }

    @JsonGetter("judge_national_id")
    public String getJudgeNationalIdJson() {
        return this.judge.getId();
    }

    @JsonGetter("judge_name")
    public String getJudgeNameJson() {
        return this.judge.getName();
    }

    @JsonGetter("judge_vote")
    public JudgeVote getJudgeVoteJson() {
        return this.judgeVote;
    }


    // getters and setters .............................................................................................

    public PublicOwner getSrcPublicOwner() {
        return srcPublicOwner;
    }

    public void setSrcPublicOwner(PublicOwner srcPublicOwner) {
        this.srcPublicOwner = srcPublicOwner;
    }

    public Judge getJudge() {
        return judge;
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    public Currency getRemittanceCurrency() {
        return remittanceCurrency;
    }

    public void setRemittanceCurrency(Currency remittanceCurrency) {
        this.remittanceCurrency = remittanceCurrency;
    }

    public int getNumberOfSuccessfulSubcontracts() {
        return numberOfSuccessfulSubcontracts;
    }

    public void setNumberOfSuccessfulSubcontracts(int numberOfSuccessfulSubContracts) {
        this.numberOfSuccessfulSubcontracts = numberOfSuccessfulSubContracts;
    }

    public int getNumberOfJudgedSubcontracts() {
        return numberOfJudgedSubcontracts;
    }

    public void setNumberOfJudgedSubcontracts(int numberOfJudgedSubcontracts) {
        this.numberOfJudgedSubcontracts = numberOfJudgedSubcontracts;
    }

    public List<Subcontract> getSubcontracts() {
        return subcontracts;
    }

    public void setSubcontracts(List<Subcontract> subcontracts) {
        this.subcontracts = subcontracts;
    }

    public Account getExchangerAccount() {
        return exchangerAccount;
    }

    public void setExchangerAccount(Account exchangerAccount) {
        this.exchangerAccount = exchangerAccount;
    }

    public void countSuccessfulContracts() {
        this.numberOfSuccessfulSubcontracts = this.numberOfSuccessfulSubcontracts++;
    }

    public long getAvailableValueInRial() {
        return availableValueInRial;
    }

    public void setAvailableValueInRial(long availableValueInRial) {
        this.availableValueInRial = availableValueInRial;
    }

}

