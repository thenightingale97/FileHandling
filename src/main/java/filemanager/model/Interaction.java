package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;

@JacksonXmlRootElement
public class Interaction {

    @JsonProperty("client")
    @JacksonXmlProperty(localName = "ClientName")
    private String clientName;

    @JsonProperty("Dt")
    @JacksonXmlProperty(localName = "TransactionTime")
    private String transactionTime;

    @JsonProperty("email")
    @JacksonXmlProperty(localName = "Email")
    private String email;

    @JsonProperty("items")
    @JacksonXmlProperty(localName = "Product")
    @JacksonXmlElementWrapper(localName = "Products")
    private ArrayList<Product> product;

    @JsonProperty("userId")
    @JacksonXmlProperty(localName = "ExternalId")
    private String externalId;

    public Interaction() {
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

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
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
        return "Interaction{" +
                "clientName='" + clientName + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", email='" + email + '\'' +
                ", product=" + product +
                ", externalId='" + externalId + '\'' +
                '}';
    }
}
