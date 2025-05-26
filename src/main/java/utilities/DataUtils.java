package utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class DataUtils {

    public final static String test_Data_Path = "src/test/resources/testData/";

    //ToDo :read data from json file
    public static String getJsonData(String jsonFilename, String filed) {
        try {
            //Define object of file reader
            FileReader reader = new FileReader(test_Data_Path + jsonFilename + ".json");
            //parse the Json directory into  a JSONElement
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(filed).getAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //Todo get properties from any .properties file
    public static String getPropertyValue(String fileName, String key) throws IOException {
        Properties predicate = new Properties();
        predicate.load(new FileInputStream(test_Data_Path + fileName + ".properties"));
        return predicate.getProperty(key);
    }


}
