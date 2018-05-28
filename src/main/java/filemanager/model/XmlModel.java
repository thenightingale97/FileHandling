package filemanager.model;

import java.util.ArrayList;

public class XmlModel {

    private String clientName;
    private String transactionTime;
    private String email;
    private ArrayList<XmlProductModel> product;
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
}
