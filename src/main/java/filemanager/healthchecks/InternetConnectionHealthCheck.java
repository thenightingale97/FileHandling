package filemanager.healthchecks;

import com.codahale.metrics.health.HealthCheck;

import java.net.HttpURLConnection;
import java.net.URL;

public class InternetConnectionHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        URL url = new URL("https://www.pravda.com.ua/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Internet connection is absent.");
        }
    }
}
