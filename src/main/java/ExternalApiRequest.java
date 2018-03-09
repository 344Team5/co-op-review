import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class encapsulates a single request to our external API resource,
 * GitHub jobs.
 */
public class ExternalApiRequest {
    /**
     * The URL of the API
     */
    private final String URL = "https://jobs.github.com/positions.json?location=new+york";

    /**
     * The max number of objects to keep from the response
     */
    private final int MAX_OBJS = 3;

    /**
     * Request info from the API, parse it, and return a List of JSONObjects
     */
    public List<JSONObject> getResults() {
        InputStream dataStream = null;
        BufferedReader br = null;
        List<JSONObject> jList = new ArrayList<>();
        try {
            // request info
            java.net.URL url = new URL(URL);
            dataStream = url.openStream();

            // parse the response
            br = new BufferedReader(new InputStreamReader(dataStream));
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
            String dataString = data.toString();
            JSONArray jArray = new JSONArray(dataString);

            // store all of the objects in a temporary list
            List<JSONObject> tempList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject o = jArray.getJSONObject(i);
                tempList.add(o);
            }

            // then shuffle and pick no more than MAX_OBJs to be returneds
            Collections.shuffle(tempList);
            int limit = MAX_OBJS < jArray.length() ? MAX_OBJS : jArray.length();
            for (int i = 0; i < limit; i++) {
                jList.add(tempList.get(i));
            }
        } catch (Exception ignored) { // fail silently
        } finally {
            try {
                if (dataStream != null) dataStream.close();
                if (br != null) br.close();
            } catch (Exception ignored) { // fail silently
            }
        }

        return jList;
    }

    /**
     * Test main
     */
    public static void main(String[] args) {
        ExternalApiRequest e = new ExternalApiRequest();
        System.out.println(e.getResults());
    }

}
