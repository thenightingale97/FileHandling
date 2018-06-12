package filemanager.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import lombok.Getter;
import lombok.Setter;

import java.net.HttpURLConnection;
import java.net.URL;

@Getter
@Setter
public class InternetConnectionHealthCheck extends HealthCheck {

    private String connectionCheckUrl;

    public InternetConnectionHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        URL url = new URL(connectionCheckUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Internet connection is absent.");
        }
    }
}
