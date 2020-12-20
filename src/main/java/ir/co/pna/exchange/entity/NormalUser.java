package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class NormalUser {
    @Id
    @Column(name = "id")
    protected String id;

    public String getId() {
        return id;
    }
}
