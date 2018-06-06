package filemanager.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Feed {

    @JacksonXmlProperty(localName = "Interaction")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Interaction> interactions;

    public Feed(List<Interaction> interactions) {
        this.interactions = interactions;
    }
    
}
