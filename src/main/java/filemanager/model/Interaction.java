package filemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Interaction {

    @JsonProperty("client")
    @JacksonXmlProperty(localName = "UserName")
    private String clientName;

    @JsonProperty("Dt")
    @JacksonXmlProperty(localName = "TransactionDate")
    private String transactionTime;

    @JsonProperty("email")
    @JacksonXmlProperty(localName = "EmailAddress")
    private String email;

    @JsonProperty("items")
    @JacksonXmlProperty(localName = "Product")
    @JacksonXmlElementWrapper(localName = "Products")
    private ArrayList<Product> product;

    @JsonProperty("userId")
    @JacksonXmlProperty(localName = "ExternalId")
    private String externalId;

}
