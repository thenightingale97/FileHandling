import com.fasterxml.jackson.annotation.JsonProperty;
import filemanager.configuration.ApplicationConfiguration;
import filemanager.launcher.Launcher;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ClientResourceTest {

    @ClassRule
    public static final DropwizardAppRule<ApplicationConfiguration> RULE = new DropwizardAppRule<>(Launcher.class,
            "config.yml");

    private final String baseUrl = "http://localhost:%d/main/check";

    @Test
    public void requestInterceptorKeyPassTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test correct");

        Response response = client.target(String.format(baseUrl, RULE.getLocalPort()))
                .queryParam("keyPass", "someValue")
                .request()
                .post(Entity.json(new TestRequest("2018-05-31 18", "test")));

        assertEquals(200, response.getStatus());
    }


    @Test
    public void requestInterceptorEmptyOrNullKeyPassTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test emptyKey");

        Response response = client.target(String.format(baseUrl, RULE.getLocalPort()))
                .request()
                .post(Entity.json(new TestRequest("2018-05-31 18", "test")));

        assertEquals(403, response.getStatus());
    }

    @Test
    public void requestInterceptorWrongFieldTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test wrongFields");

        Response response = client.target(String.format(baseUrl, RULE.getLocalPort()))
                .queryParam("keyPass", "someValue")
                .request()
                .post(Entity.json(new TestBadRequest("2018-05-31 18", "test")));

        assertEquals(400, response.getStatus());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class TestRequest {

        @JsonProperty
        private String date;

        @JsonProperty
        private String client;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class TestBadRequest {

        @JsonProperty
        private String date;

        @JsonProperty
        private String badFieldClient;

    }
}
