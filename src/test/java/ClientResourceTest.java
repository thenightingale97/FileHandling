import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import filemanager.configuration.ApplicationConfiguration;
import filemanager.interceptor.RequestKeyCheckInterceptor;
import filemanager.launcher.Launcher;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ClientResourceTest {

    @Inject
    RequestKeyCheckInterceptor requestKeyCheckInterceptor;

    @ClassRule
    public static final DropwizardAppRule<ApplicationConfiguration> RULE = new DropwizardAppRule<>(Launcher.class,
            "config.yml");

    @After
    public void tearDown() {
        requestKeyCheckInterceptor = null;
    }

    @Before
    public void setUp() {
        requestKeyCheckInterceptor = new RequestKeyCheckInterceptor();
    }

    @Test
    public void requestInterceptorKeyPassTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test correct");

        Response response = client.target(String.format("http://localhost:%d/main/check?keyPass=someValue", RULE.getLocalPort()))
                .request()
                .post(Entity.json(new TestRequest("2018-05-31 18", "test")));

        assertEquals(200, response.getStatus());
    }


    @Test
    public void requestInterceptorEmptyOrNullKeyPassTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test emptyKey");

        Response response = client.target(String.format("http://localhost:%d/main/check", RULE.getLocalPort()))
                .request()
                .post(Entity.json(new TestRequest("2018-05-31 18", "test")));

        assertEquals(403, response.getStatus());
    }

    @Test
    public void requestInterceptorWrongFieldTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test wrongFields");

        Response response = client.target(String.format("http://localhost:%d/main/check?keyPass=someValue", RULE.getLocalPort()))
                .request()
                .post(Entity.json(new TestBadRequest("2018-05-31 18", "test")));

        assertEquals(400, response.getStatus());
    }

    private class TestRequest {

        @JsonProperty
        private String date;

        @JsonProperty
        private String client;

        public TestRequest(String date, String client) {
            this.date = date;
            this.client = client;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }
    }

    private class TestBadRequest {

        @JsonProperty
        private String date;

        @JsonProperty
        private String badFieldClient;

        public TestBadRequest(String date, String badFieldClient) {
            this.date = date;
            this.badFieldClient = badFieldClient;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClient() {
            return badFieldClient;
        }

        public void setClient(String client) {
            this.badFieldClient = client;
        }
    }
}
