package ir.co.pna.exchange.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "judge")
@JsonIgnoreProperties({"normalContracts", "id"})
@JsonPropertyOrder({"name", "national_id"})
public class Judge extends NormalUser {


    @Column(name = "name")
    @JsonProperty("name")
    private String name;
    @OneToMany(
            mappedBy = "judge",
            fetch = FetchType.LAZY,
            cascade = {
//                    CascadeType.PERSIST,
                    CascadeType.MERGE,
//                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    List<NormalContract> normalContracts;

    private void init() {
        normalContracts = new ArrayList<NormalContract>();
    }

    public Judge() {

    }

    public Judge(String name, String id) {
        init();
        this.name = name;
        this.id = id;
    }

    //custom serializing ...............................................................................................

    @JsonGetter("national_id")
    public String getNationalId() {
        return getId();
    }


    // getters and setters .............................................................................................

    public void addNormalContract(NormalContract normalContract) {
        this.normalContracts.add(normalContract);
    }

    public void setId(String nationalId) {
        this.id = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NormalContract> getNormalContracts() {
        return normalContracts;
    }

    public void setNormalContracts(List<NormalContract> normalContracts) {
        this.normalContracts = normalContracts;
    }
}

