package filemanager.metrics;

import com.yammer.metrics.core.MetricName;

import java.util.Iterator;
import java.util.Map;

public class TaggedMetricName extends MetricName {

    private static String FULL_NAME_PATTERN = "%s[%s]";
    private static String TAG_PATTERN = "%s:%s";
    private static String TAG_JOINER = ",";

    public TaggedMetricName(Class clazz, String metricName, Map<String, String> tags) {
        super(clazz, formatMetricName(metricName, tags));
    }

    private static String formatMetricName(String metricName, Map<String, String> tags) {
        if (tags == null || tags.isEmpty()) {
            return metricName;
        }
        StringBuffer tagJoiner = new StringBuffer();
        Iterator<Map.Entry<String, String>> tagIterator = tags.entrySet().iterator();
        while (tagIterator.hasNext()) {
            Map.Entry<String, String> tag = tagIterator.next();
            tagJoiner.append(
                    String.format(
                            TAG_PATTERN,
                            tag.getKey(),
                            tag.getValue())
            );
            if (tagIterator.hasNext()) {
                tagJoiner.append(TAG_JOINER);
            }
        }
        return String.format(
                FULL_NAME_PATTERN,
                metricName,
                tagJoiner.toString()
        );
    }

}
