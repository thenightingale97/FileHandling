package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@JacksonXmlRootElement(localName = "Interaction")
public class XmlModel {

    @JsonProperty("client")
    @JacksonXmlProperty(localName = "ClientName")
    private String clientName;

    @JsonProperty("Dt")
    @JacksonXmlProperty(localName = "TransactionTime")
    private String transactionTime;

    @JsonProperty("email")
    private String email;

    @JsonProperty("items")
    @JacksonXmlProperty(localName = "Product")
    @JacksonXmlElementWrapper(localName = "{roducts")
    private ArrayList<XmlProductModel> product;

    @JsonProperty("UserId")
    private String externalId;

    public XmlModel() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<XmlProductModel> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<XmlProductModel> product) {
        this.product = product;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return "XmlModel{" +
                "clientName='" + clientName + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", email='" + email + '\'' +
                ", product=" + product +
                ", externalId='" + externalId + '\'' +
                '}';
    }
}
