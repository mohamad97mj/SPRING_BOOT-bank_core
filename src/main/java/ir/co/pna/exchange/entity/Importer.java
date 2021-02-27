package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.pna.exchange.emum.OwnerType;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"users", "inExternalTransactions", "outExternalTransactions", "outNormalContracts"})
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Importer extends PublicOwner {



    private void init() {
        outNormalContracts = new ArrayList<>();
    }

    public Importer(String importerId, String mobileNumber) {
        super(importerId, OwnerType.IMPORTER, mobileNumber);
        init();

    }

    public Importer(){}

    @OneToMany(
            mappedBy = "srcPublicOwner",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<NormalContract> outNormalContracts; // this is just for importer

    // getters and setters .............................................................................................




    public void setOutNormalContracts(List<NormalContract> outNormalContracts) {
        this.outNormalContracts = outNormalContracts;
    }

    public List<NormalContract> getOutNormalContracts() {
        return outNormalContracts;
    }

    public void addOutNormalContract(NormalContract normalContract) {
        this.outNormalContracts.add(normalContract);
    }

}
