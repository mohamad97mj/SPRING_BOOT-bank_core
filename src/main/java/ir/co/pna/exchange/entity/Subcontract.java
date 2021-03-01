package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.emum.JudgeVote;
import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.emum.TransactionType;
import ir.co.pna.exchange.exception.EntityBadRequestException;
import ir.co.pna.exchange.utility.GlobalVariables;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;


@Entity
@JsonIgnoreProperties({"returnAccount", "claimAccount", "exporterAccount", "src_owner_bank_account_id"})
@JsonPropertyOrder({"id", "parent_id", "dst_owner_bank_account_id", "value_in_rial", "remittance_value", "judge_vote", "expire_date", "contract_status", "description", "payment_id"})
public class Subcontract extends Contract {

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "parent_id")
    @JsonProperty("parent_id")
    private NormalContract parent;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "exporter_account_id")
    protected Account exporterAccount;


    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }

    )
    @JoinColumn(name = "claim_account_id")
    protected Account claimAccount;

    public Subcontract() {

    }

    public Subcontract(NormalContract parent, long expireDate, PublicOwner dstPublicOwner, long valueInRial, long remittanceValue, String description) {
        super(expireDate, dstPublicOwner, valueInRial, remittanceValue, description);
        this.parent = parent;
        ((Exporter) dstPublicOwner).addInSubcontract(this);
        this.status = ContractStatus.WAITING_FOR_EXPORTER_ACCEPTANCE;
    }


    public void pay(TransactionOperatorType operatorType, User operator, SmsClient smsClient, YaghutClient yaghutClient) {
        if (this.parent.status == ContractStatus.DOING_BY_EXCHANGER) {
            if (this.status == ContractStatus.WAITING_FOR_EXCHANGER_PAYMENT) {
                this.setStatus(ContractStatus.DOING_BY_EXPORTER);
                this.parent.getExchangerAccount().decreaseCredit(this.valueInRial);
                this.exporterAccount.increaseCredit(this.valueInRial);

                //sms
                String message = "واریز به حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی صادرکننده ها)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(this.valueInRial) + "ریال";
                SendSMSResponse smsResponse = smsClient.sendSms(this.dstPublicOwner.getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
                System.out.println(smsResponse.toString());
                System.err.println(smsResponse.getSendSMSResult());


                String message2 = "برداشت از حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی صراف ها)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(this.valueInRial) + "ریال";
                SendSMSResponse smsResponse2 = smsClient.sendSms(this.parent.dstPublicOwner.getMobileNumber(), message2, SMSGateway.ADVERTISEMENT, "demo");
                System.out.println(smsResponse2.toString());
                System.err.println(smsResponse2.getSendSMSResult());
//
//                // transfer
                NormalTransferResponse transferResponse = yaghutClient.normalTransfer(GlobalVariables.operationalExchangerOwner.getIbUsername(), GlobalVariables.operationalExchangerOwner.getIbPassword(), GlobalVariables.operationalExchangerOwner.getBankAccountId(), GlobalVariables.operationalExporterOwner.getBankAccountId(), new BigDecimal(this.valueInRial), "destinationComment", "sourceComment");
                System.out.println("exchanger(tavalaee) to exporter(yaghli):");
                System.err.println(transferResponse.getNormalTransferResult());


                TransactionType transactionType = TransactionType.PAYMENT;
                Transaction transaction = new InternalTransaction(this, operator, operatorType, transactionType, this.parent.getExchangerAccount(), this.exporterAccount, this.valueInRial, GlobalVariables.getNow());
                ExternalTransaction exTransaction = new ExternalTransaction(0, transaction, GlobalVariables.operationalExchangerOwner, GlobalVariables.operationalExporterOwner, GlobalVariables.getNow());

            } else {
                throw new EntityBadRequestException("subcontract with id " + this.id + "can not be payed!");

            }
        } else {
            throw new EntityBadRequestException("subcontract with parent id" + this.parent.id + "can not be payed");
        }
    }


    public Transaction claim(TransactionOperatorType operatorType, User operator, SmsClient smsClient, YaghutClient yaghutClient) {
        this.status = ContractStatus.CLAIMED_BY_IMPORTER;
        this.judgeVote = JudgeVote.NOT_JUDGED;
        long value = this.exporterAccount.getCredit();
        this.claimAccount.setCredit(value);
        this.exporterAccount.setCredit(0);

        TransactionType transactionType = TransactionType.CLAIM;
        Transaction transaction = new InternalTransaction(this, operator, operatorType, transactionType, this.exporterAccount, this.claimAccount, value, GlobalVariables.getNow());

        //sms
//        String message = "واریز به حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی داوری)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
//        SendSMSResponse smsResponse = smsClient.sendSms(GlobalVariables.operationalClaimOwner.getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
//        System.out.println(smsResponse.toString());
//        System.err.println(smsResponse.getSendSMSResult());


        String message2 = "برداشت از حساب امانی شما نزد بانک اقتصاد نوین:\n" +  "\n(حساب عملیاتی صادرکننده ها)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
        SendSMSResponse smsResponse2 = smsClient.sendSms(this.dstPublicOwner.getMobileNumber(), message2, SMSGateway.ADVERTISEMENT, "demo");
        System.out.println(smsResponse2.toString());
        System.err.println(smsResponse2.getSendSMSResult());


        // transfer
        NormalTransferResponse transferResponse = yaghutClient.normalTransfer(GlobalVariables.operationalExporterOwner.getIbUsername(), GlobalVariables.operationalExporterOwner.getIbPassword(), GlobalVariables.operationalExporterOwner.getBankAccountId(), GlobalVariables.operationalClaimOwner.getBankAccountId(), new BigDecimal(value), "destinationComment", "sourceComment");
        System.out.println("exchanger(tavalaee) to exporter(yaghli):");
        System.err.println(transferResponse.getNormalTransferResult());


        return transaction;
    }

    private Transaction returnFromClaim2Return(TransactionOperatorType operatorType, Judge operator, SmsClient smsClient, YaghutClient yaghutClient) {
        this.status = ContractStatus.JUDGED;
        this.setJudgeVote(JudgeVote.NOT_DONE);
        long value = this.claimAccount.getCredit();
        this.returnAccount.setCredit(value);
        this.claimAccount.setCredit(0);

        TransactionType transactionType = TransactionType.JUDGEMENT_NOT_DONE;
        Transaction transaction = new InternalTransaction(this, operator, operatorType, transactionType, this.claimAccount, this.parent.returnAccount, value, GlobalVariables.getNow());

        //sms
        String message = "واریز به حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی بازگشت)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
        SendSMSResponse smsResponse = smsClient.sendSms(this.parent.getSrcPublicOwner().getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
        System.out.println(smsResponse.toString());
        System.err.println(smsResponse.getSendSMSResult());


//        String message2 = "برداشت از حساب امانی شما نزد بانک اقتصاد نوین:\n" + "\n(حساب عملیاتی داوری)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
//        SendSMSResponse smsResponse2 = smsClient.sendSms(GlobalVariables.operationalClaimOwner.getMobileNumber(), message2, SMSGateway.ADVERTISEMENT, "demo");
//        System.out.println(smsResponse2.toString());
//        System.err.println(smsResponse2.getSendSMSResult());
//
//        // transfer
        NormalTransferResponse transferResponse = yaghutClient.normalTransfer(GlobalVariables.operationalClaimOwner.getIbUsername(), GlobalVariables.operationalClaimOwner.getIbPassword(), GlobalVariables.operationalClaimOwner.getBankAccountId(), GlobalVariables.operationalReturnOwner.getBankAccountId(), new BigDecimal(value), "destinationComment", "sourceComment");
        System.out.println("claim(shahsavani) to return(mojahed):");
        System.err.println(transferResponse.getNormalTransferResult());

        return transaction;

    }

    private Transaction returnFromClaim2Exporter(TransactionOperatorType operatorType, Judge operator, SmsClient smsClient, YaghutClient yaghutClient) {

        this.status = ContractStatus.JUDGED;
        this.judgeVote = JudgeVote.DONE;
        long value = this.claimAccount.getCredit();
        this.exporterAccount.setCredit(value);
        this.claimAccount.setCredit(0);

        TransactionType transactionType = TransactionType.JUDGEMENT_DONE;
        Transaction transaction = new InternalTransaction(this, operator, operatorType, transactionType, this.claimAccount, this.exporterAccount, value, GlobalVariables.getNow());


        //sms
        String message = "واریز به حساب امانی شما نزد بانک اقتصاد نوین:\n" +  "\n(حساب عملیاتی صادرکننده ها)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
        SendSMSResponse smsResponse = smsClient.sendSms(this.dstPublicOwner.getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
        System.out.println(smsResponse.toString());
        System.err.println(smsResponse.getSendSMSResult());


//        String message2 = "برداشت از حساب امانی شما نزد بانک اقتصاد نوین:\n" +  "\n(حساب عملیاتی داوری)" + "\n" + "مبلغ:" + GlobalVariables.getThousandsSeparated(value) + "ریال";
//        SendSMSResponse smsResponse2 = smsClient.sendSms(GlobalVariables.operationalClaimOwner.getMobileNumber(), message2, SMSGateway.ADVERTISEMENT, "demo");
//        System.out.println(smsResponse2.toString());
//        System.err.println(smsResponse2.getSendSMSResult());


        // transfer
        NormalTransferResponse transferResponse = yaghutClient.normalTransfer(GlobalVariables.operationalClaimOwner.getIbUsername(), GlobalVariables.operationalClaimOwner.getIbPassword(), GlobalVariables.operationalClaimOwner.getBankAccountId(), GlobalVariables.operationalExporterOwner.getBankAccountId(), new BigDecimal(value), "destinationComment", "sourceComment");
        System.out.println("claim(shahsavani) to exporter(yaghli):");
        System.err.println(transferResponse.getNormalTransferResult());

        return transaction;
    }

    public Transaction judge(TransactionOperatorType operatorType, Judge operator, JudgeVote vote, SmsClient smsClient, YaghutClient yaghutClient) {
        if (this.status == ContractStatus.CLAIMED_BY_IMPORTER) {
            this.parent.numberOfJudgedSubcontracts++;
            this.judgeVote = vote;
            Transaction transaction = null;
            if (vote == JudgeVote.DONE) {

                this.parent.numberOfSuccessfulSubcontracts++;
                transaction = returnFromClaim2Exporter(operatorType, operator, smsClient, yaghutClient);
                ExternalTransaction exTransaction = new ExternalTransaction(0, transaction, GlobalVariables.operationalClaimOwner, GlobalVariables.operationalExporterOwner, GlobalVariables.getNow());

            } else {

                this.parent.numberOfFailedSubcontracts++;
                transaction = returnFromClaim2Return(operatorType, operator, smsClient, yaghutClient);
                ExternalTransaction exTransaction = new ExternalTransaction(0, transaction, GlobalVariables.operationalClaimOwner, GlobalVariables.operationalReturnOwner, GlobalVariables.getNow());

            }
            this.close();

            this.parent.judge();
            return transaction;
        } else {
            throw new EntityBadRequestException("subcontract with id" + this.id + "can not be judged");
        }
    }

    //custom serializing ...............................................................................................

    @JsonGetter("parent_id")
    public long getParentIdJson() {
        return this.parent.getId();
    }

    // getters and setters .............................................................................................


    public NormalContract getParent() {
        return parent;
    }

    public void setParent(NormalContract parent) {
        this.parent = parent;
    }

    public Account getExporterAccount() {
        return exporterAccount;
    }

    public void setExporterAccount(Account exporterAccount) {
        this.exporterAccount = exporterAccount;
    }

    public Account getClaimAccount() {
        return claimAccount;
    }

    public void setClaimAccount(Account claimAccount) {
        this.claimAccount = claimAccount;
    }


}

