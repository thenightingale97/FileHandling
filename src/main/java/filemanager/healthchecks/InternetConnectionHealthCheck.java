package filemanager.healthchecks;

import com.codahale.metrics.health.HealthCheck;

import java.net.HttpURLConnection;
import java.net.URL;

public class InternetConnectionHealthCheck extends HealthCheck {

    private String connectionCheckUrl;

    public InternetConnectionHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        URL url = new URL(connectionCheckUrl);
        System.out.println(connectionCheckUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Internet connection is absent.");
        }
    }

    public String getConnectionCheckUrl() {
        return connectionCheckUrl;
    }

    public void setConnectionCheckUrl(String connectionCheckUrl) {
        this.connectionCheckUrl = connectionCheckUrl;
    }
}
