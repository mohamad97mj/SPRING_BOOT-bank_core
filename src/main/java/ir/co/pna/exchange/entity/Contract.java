package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ir.co.pna.exchange.emum.ContractStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Entity
@Table(name = "contract")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType= DiscriminatorType.STRING)
@DiscriminatorValue(value="contract")
public class Contract {

    @Id
    @Column(name = "id")
    protected int id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "owner_contract",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_bank_account_id")
    )
    protected List<Owner> owners; // first is srcOwner and second is dstOwner

    @Column(name = "expire_date")
    private Calendar expireDate;

    @Column(name = "value_in_rial")
    protected long valueInRial;

    @Column(name = "remittance_value")
    protected long remittanceValue;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "contract", fetch= FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    protected List<Account> accounts;

    @Column(name = "contract_status")
    protected ContractStatus contractStatus;

    private void init () {
        accounts = new ArrayList<>();
        owners = new ArrayList<>();
    }

    public Contract(){

    }

    public Contract(int id, Calendar expireDate, Owner srcOwner, Owner dstOwner, long valueInRial, long remittanceValue, String description) {

        init();

        this.id = id;
        this.expireDate = expireDate;
        this.valueInRial = valueInRial;
        this.remittanceValue = remittanceValue;
        this.description = description;

        srcOwner.addContract(this);
        dstOwner.addContract(this);

//        returnAccount.setContract(this);
//        claimAccount.setContract(this);

        owners.add(srcOwner);
        owners.add(dstOwner);

//        accounts.add(returnAccount);
//        accounts.add(claimAccount);

    }



    // getters and setters .............................................................................................

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Owner getSrcOwner() {
        return owners.get(0);
    }

    public Owner getDstOwner() {
        return owners.get(1);
    }

    public Calendar getExpireDate() {
        return expireDate;
    }

    public long getValueInRial() {
        return valueInRial;
    }

    public long getRemittanceValue() {
        return remittanceValue;
    }

    public String getDescription() {
        return description;
    }

    public Account getReturnAccount() {
        return accounts.get(0);
    }

    public Account getClaimAccount() {
        return accounts.get(1);
    }

    public int getId() {
        return id;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setId(int id) {
        this.id = id;
    }
}
