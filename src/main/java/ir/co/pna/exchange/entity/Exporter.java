package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"users", "inExternalTransactions", "outExternalTransactions", "inSubcontracts"})
public class Exporter extends PublicOwner {

    private void init() {
        inSubcontracts = new ArrayList<>();
    }

    public Exporter(){}

    public Exporter(String exporterId, String mobileNumber){
        super(exporterId, OwnerType.EXPORTER, mobileNumber);
        init();
    }

    @OneToMany(
            mappedBy = "dstPublicOwner",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Subcontract> inSubcontracts; // this is just for exporter

    // getters and setters .............................................................................................


    public void addInSubcontract(Subcontract subcontract) {
        this.inSubcontracts.add(subcontract);
    }

    public void setInSubcontracts(List<Subcontract> inSubcontracts) {
        this.inSubcontracts = inSubcontracts;
    }

    public List<Subcontract> getInSubcontracts() {
        return inSubcontracts;
    }


}
