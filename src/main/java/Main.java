import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            String jsonObject = new String(Files.readAllBytes(Paths.get("/home/ykato/Documents/bvpixel/qa/2017/10/2/21/data1")), StandardCharsets.UTF_8);
            JSONObject jsonFileObject = new JSONObject(jsonObject);
            System.out.println(jsonObject);
            String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<" + "root" + ">"
                    + XML.toString(jsonFileObject) + "</" + "root" + ">";
            FileWriter ofstream = new FileWriter("/home/ykato/Documents/bvpixel/qa/2017/10/2/21/data2.xml");
            try (BufferedWriter out = new BufferedWriter(ofstream)) {
                out.write(xml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) {

        Path path = Paths.get("/home/ykato/Documents/bvpixel/qa/2017/10/2/21/23");
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }

    }*/
}
