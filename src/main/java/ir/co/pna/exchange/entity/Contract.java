package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.emum.JudgeVote;

import javax.persistence.*;



@Entity
@Table(name = "contract")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Contract {
    @TableGenerator(name = "CONTRACT_ID_Gen", table = "CONTRACT_ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "ID_Gen", initialValue = 10000, allocationSize = 100)
    @Id
    @Column(name = "id")
    @JsonProperty("id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CONTRACT_ID_Gen")
    protected int id;

    @Column(name = "judge_vote")
    @JsonProperty("judge_vote")
    protected JudgeVote judgeVote;


    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH}
    )
    @JoinColumn(name = "dst_owner_id")
    @JsonProperty("dst_owner_bank_account_id")
    protected PublicOwner dstPublicOwner;

    @Column(name = "expire_date")
    @JsonProperty("expire_date")
    private long expireDate;

    @Column(name = "value_in_rial")
    @JsonProperty("value_in_rial")
    protected long valueInRial;

    @Column(name = "remittance_value")
    @JsonProperty("remittance_value")
    protected long remittanceValue;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;


    @Column(name = "status")
    @JsonProperty("status")
    protected ContractStatus status;

    private void init() {
    }

    public Contract() {

    }

    public Contract(long expireDate, PublicOwner dstPublicOwner, long valueInRial, long remittanceValue, String description) {

        init();

//        this.id = id;
        this.expireDate = expireDate;
        this.valueInRial = valueInRial;
        this.remittanceValue = remittanceValue;
        this.description = description;
        this.judgeVote = JudgeVote.NOT_CLAIMED;
        this.dstPublicOwner = dstPublicOwner;

    }

    //custom serializing ...............................................................................................

    @JsonGetter("dst_owner_bank_account_id")
    public String getDstOwnerJson() {
        return this.dstPublicOwner.getBankAccountId();
    }


    // getters and setters .............................................................................................

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JudgeVote getJudgeVote() {
        return judgeVote;
    }

    public void setJudgeVote(JudgeVote judgeVote) {
        this.judgeVote = judgeVote;
    }

    public PublicOwner getDstPublicOwner() {
        return dstPublicOwner;
    }

    public void setDstPublicOwner(PublicOwner dstPublicOwner) {
        this.dstPublicOwner = dstPublicOwner;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public long getValueInRial() {
        return valueInRial;
    }

    public void setValueInRial(long valueInRial) {
        this.valueInRial = valueInRial;
    }

    public long getRemittanceValue() {
        return remittanceValue;
    }

    public void setRemittanceValue(long remittanceValue) {
        this.remittanceValue = remittanceValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus contractStatus) {
        this.status = contractStatus;
    }

}
