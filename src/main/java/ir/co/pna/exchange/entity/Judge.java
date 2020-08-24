package ir.co.pna.exchange.entity;



import ir.co.pna.exchange.emum.ContractStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "judge")
public class Judge {

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

    @Id
    @Column(name = "national_id")
    private long nationalId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "judge", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    List<NormalContract> normalContracts;

    private void init(){
        normalContracts = new ArrayList<NormalContract>();
    }

    public Judge(){

    }

    public Judge(String name, long nationalId) {
        init();
        this.name = name;
        this.nationalId = nationalId;
    }

    public void addNormalContract(NormalContract normalContract){
        this.normalContracts.add(normalContract);
    }

    public int getContractIndex(int contractId){
        for (int i = 0; i< normalContracts.size(); i++){
            if (normalContracts.get(i).getId() == contractId){
                return i;
            }
        }
        return -1;
    }

    public void judge(int subContractId, ContractStatus contractStatus){

        for (NormalContract normalContract: normalContracts) {
            for (SubContract subContract: normalContract.getSubContracts()){
                if (subContract.getId() == subContractId) {
                    subContract.setContractStatus(contractStatus);
                }
            }
        }
    }


    // getters and setters .............................................................................................

    public String getName() {
        return name;
    }

    public long getNationalId() {
        return nationalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NormalContract> getNormalContracts() {
        return normalContracts;
    }
}

