package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class JsonModel {
    @JsonProperty("client")
    private String clientName;

    @JsonProperty("Dt")
    private String transactionTime;

    @JsonProperty("email")
    private String email;

    @JsonProperty("items")
    private ArrayList<JsonProductModel> products;

    @JsonProperty("userId")
    private String externalId;

    public JsonModel() {
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

    public ArrayList<JsonProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<JsonProductModel> products) {
        this.products = products;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return "JsonModel{" +
                "clientName='" + clientName + '\'' +
                ", transactionTime=" + transactionTime +
                ", email='" + email + '\'' +
                ", products=" + products +
                ", externalId=" + externalId +
                '}';
    }
}
