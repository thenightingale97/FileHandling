package filemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
class Product {

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

}
