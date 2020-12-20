package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.pna.exchange.emum.OwnerType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"users", "inExternalTransactions", "outExternalTransactions", "inNormalContracts"})
public class Exchanger extends PublicOwner {


    private void init() {
        inNormalContracts = new ArrayList<>();
    }

    public Exchanger(){}

    public Exchanger(String exchangerId){
        super(exchangerId, OwnerType.EXCHANGER);
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
    private List<NormalContract> inNormalContracts; // this is just for exchanger


    // getters and setters .............................................................................................


    public void setInNormalContracts(List<NormalContract> inNormalContracts) {
        this.inNormalContracts = inNormalContracts;
    }

    public List<NormalContract> getInNormalContracts() {
        return inNormalContracts;
    }

    public void addInNormalContract(NormalContract normalContract) {
        this.inNormalContracts.add(normalContract);
    }


}
