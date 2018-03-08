import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalApiRequest {
    private final String URL = "https://jobs.github.com/positions.json?location=new+york";
    private final int MAX_OBJS = 3;

    public List<JSONObject> getResults() {
        InputStream dataStream = null;
        BufferedReader br = null;
        List<JSONObject> jList = new ArrayList<>();
        try {
            java.net.URL url = new URL(URL);
            dataStream = url.openStream();
            br = new BufferedReader(new InputStreamReader(dataStream));
            StringBuilder data = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                data.append(line);
            }
            String dataString = data.toString();
            JSONArray jArray = new JSONArray(dataString);
            List<JSONObject> tempList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++){
                JSONObject o = jArray.getJSONObject(i);
                tempList.add(o);
            }
            Collections.shuffle(tempList);
            int limit = MAX_OBJS < jArray.length() ? MAX_OBJS : jArray.length();
            for (int i = 0; i < limit; i++) {
                jList.add(tempList.get(i));
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (dataStream != null) dataStream.close();
                if (br != null) br.close();
            } catch (Exception ignored) {}
        }

        return jList;
    }

    public static void main(String[] args) {
        ExternalApiRequest e = new ExternalApiRequest();
        System.out.println(e.getResults());
    }

}
