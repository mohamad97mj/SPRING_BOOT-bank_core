package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Entity
@Table(name = "owner")
@JsonIgnoreProperties({"users", "contracts", "accounts" , "oneSideInternalTransactions", "inExternalTransactions", "outExternalTransactions"})
public class Owner {

    @Id
    @Column(name = "bank_account_id")
    @JsonProperty("bank_account_id")
    private long bankAccountId;

    @Column(name = "owner_type")
    @JsonProperty("owner_type")
    private OwnerType ownerType;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "owner_user",
            joinColumns = @JoinColumn(name = "owner_bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "user_national_code")
    )
    private List<User> users;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Account> accounts;

    @OneToMany(mappedBy = "src", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<OneSideInternalTransaction> oneSideInternalTransactions;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "owner_contract",
            joinColumns = @JoinColumn(name = "owner_bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    private List<Contract> contracts;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "in_external_transaction_id")
    private List<ExternalTransaction> inExternalTransactions;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "out_external_transaction_id")
    private List<ExternalTransaction> outExternalTransactions;

    @Transient
    private Scanner scanner;


    private void init() {
        scanner = new Scanner(System.in);
        users = new ArrayList<>();
        accounts = new ArrayList<>();
        contracts = new ArrayList<>();
        oneSideInternalTransactions = new ArrayList<>();
        inExternalTransactions = new ArrayList<>();
        outExternalTransactions = new ArrayList<>();
    }

    public Owner() {

    }

    public Owner(long bankAccountId, OwnerType ownerType) {
        init();
        this.bankAccountId = bankAccountId;
        this.ownerType = ownerType;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.own(this);
    }

    // getters and setters .............................................................................................

    public long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> listOfUsers) {
        this.users = listOfUsers;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    public void addOneSideInternalTransactions(OneSideInternalTransaction transaction) {
        this.oneSideInternalTransactions.add(transaction);
    }

    //custom serializing ...............................................................................................

//    @JsonGetter("users")
//    public ArrayList<Long> getListOfUsers() {
//        ArrayList<Long> tmp = new ArrayList<>();
//        for (User user : users) {
//            tmp.add(user.getNationalCode());
//        }
//        return tmp;
//    }
//
//    @JsonGetter("accounts")
//    public ArrayList<Long> getListOfAccounts() {
//        ArrayList<Long> tmp = new ArrayList<>();
//        for (Account account : accounts) {
//            tmp.add(account.getId());
//        }
//        return tmp;
//    }
//
//    @JsonGetter("contracts")
//    public ArrayList<Integer> getListOfContracts() {
//        ArrayList<Integer> tmp = new ArrayList<>();
//        for (Contract contract : contracts) {
//            tmp.add(contract.getId());
//        }
//        return tmp;
//    }
}


