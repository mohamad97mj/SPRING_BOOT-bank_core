package ir.co.pna.exchange.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reporter")
@JsonIgnoreProperties({"id"})
@JsonPropertyOrder({"username", "reporter"})
public class Reporter extends NormalUser {

    @Column(name = "full_name")
    @JsonProperty("full_name")
    private String fullName;

    public Reporter() {

    }

    public Reporter(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }


    //custom serializing ...............................................................................................

    @JsonGetter("username")
    public String getUsername() {
        return getId();
    }

    // getters and setters .............................................................................................

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setId(String reporterId) {
        this.id = reporterId;
    }


}
