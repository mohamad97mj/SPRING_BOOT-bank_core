//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.06 at 01:42:25 PM IRDT 
//


package ir.co.pna.exchange.client.yaghut.generated_resources;

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
 *         &lt;element name="StatementResult" type="{http://service.yaghut.modern.tosan.com/}statementResponseBean" minOccurs="0"/&gt;
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
    "statementResult"
})
@XmlRootElement(name = "StatementResponse")
public class StatementResponse {

    @XmlElement(name = "StatementResult")
    protected StatementResponseBean statementResult;

    /**
     * Gets the value of the statementResult property.
     * 
     * @return
     *     possible object is
     *     {@link StatementResponseBean }
     *     
     */
    public StatementResponseBean getStatementResult() {
        return statementResult;
    }

    /**
     * Sets the value of the statementResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatementResponseBean }
     *     
     */
    public void setStatementResult(StatementResponseBean value) {
        this.statementResult = value;
    }

}