package ir.co.pna.exchange.entity;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "external_transaction")
public class ExternalTransaction {

    @Id
    @Column(name = "bank_transaction_id")
    private int bankTransactionId;

    public void setBankTransactionId(int bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "owner_external_transaction",
            joinColumns = @JoinColumn(name = "bank_external_transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_bank_account_id")
    )
    private List<Owner> owners; //first is src and second is dst


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "src_owner_id")
    private Owner srcOwner;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "dst_owner_id")
    private Owner dstOwner;


    @OneToOne(mappedBy = "externalTransaction", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private Transaction internalTransaction;


    private void init() {
        owners = new ArrayList<>();
    }

    public ExternalTransaction() {

    }

    public ExternalTransaction(int bankTransactionId, Owner src, Owner dst, Transaction internalTransaction) {

        init();


        this.bankTransactionId = bankTransactionId;
        owners.add(src);
        owners.add(dst);
        this.internalTransaction = internalTransaction;

    }

    public int getBankTransactionId() {
        return bankTransactionId;
    }

    public Owner getSrc() {
        return owners.get(0);
    }

    public Owner getDst() {
        return owners.get(1);
    }

    public Transaction getInternalTransaction() {
        return internalTransaction;
    }
}
