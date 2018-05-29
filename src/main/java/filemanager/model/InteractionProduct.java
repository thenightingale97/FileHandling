package filemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InteractionProduct {

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JsonProperty("price")
    @JacksonXmlProperty(localName = "Price")
    private String price;

    @JsonProperty("orderId")
    @JacksonXmlProperty(localName = "OrderId")
    private String externalId;

    @JsonProperty("imageUrl")
    @JacksonXmlProperty(localName = "ImageUrl")
    private String imageUrl;

    public InteractionProduct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "InteractionProduct{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", externalId='" + externalId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
