//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.06 at 01:42:25 PM IRDT 
//


package ir.co.pna.exchange.client.yaghut.generated_resources;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sourceDepositNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="destinationDepositNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="destinationComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sourceComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="payId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="referenceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="getBalance" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userName",
    "password",
    "sourceDepositNo",
    "destinationDepositNo",
    "amount",
    "destinationComment",
    "sourceComment",
    "payId",
    "referenceNumber",
    "getBalance"
})
@XmlRootElement(name = "NormalTransfer")
public class NormalTransfer {

    protected String userName;
    protected String password;
    protected String sourceDepositNo;
    protected String destinationDepositNo;
    @XmlElement(required = true)
    protected BigDecimal amount;
    protected String destinationComment;
    protected String sourceComment;
    protected String payId;
    protected String referenceNumber;
    protected boolean getBalance;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the sourceDepositNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceDepositNo() {
        return sourceDepositNo;
    }

    /**
     * Sets the value of the sourceDepositNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceDepositNo(String value) {
        this.sourceDepositNo = value;
    }

    /**
     * Gets the value of the destinationDepositNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationDepositNo() {
        return destinationDepositNo;
    }

    /**
     * Sets the value of the destinationDepositNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationDepositNo(String value) {
        this.destinationDepositNo = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the destinationComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationComment() {
        return destinationComment;
    }

    /**
     * Sets the value of the destinationComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationComment(String value) {
        this.destinationComment = value;
    }

    /**
     * Gets the value of the sourceComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceComment() {
        return sourceComment;
    }

    /**
     * Sets the value of the sourceComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceComment(String value) {
        this.sourceComment = value;
    }

    /**
     * Gets the value of the payId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayId() {
        return payId;
    }

    /**
     * Sets the value of the payId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayId(String value) {
        this.payId = value;
    }

    /**
     * Gets the value of the referenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Sets the value of the referenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceNumber(String value) {
        this.referenceNumber = value;
    }

    /**
     * Gets the value of the getBalance property.
     * 
     */
    public boolean isGetBalance() {
        return getBalance;
    }

    /**
     * Sets the value of the getBalance property.
     * 
     */
    public void setGetBalance(boolean value) {
        this.getBalance = value;
    }

}
