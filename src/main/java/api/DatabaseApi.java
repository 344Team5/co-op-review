package api;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class DatabaseApi {
    private static Connection dbConnection = null;

    /* Lazy initializer */
    static Connection db() {
        if (dbConnection == null) {
            dbConnection = getConnection();
        }
        return dbConnection;
    }

    private static Connection getConnection() {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        try {
            return DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Object getSQLQueryResultsJson(ResultSet rs, String ... keys) {
        Object results = "";
        List<JSONObject> rows = new ArrayList<>();
        try {
            while (rs.next()) {
                JSONObject json = new JSONObject();
                for (String k : keys) {
                    json.put(k,rs.getString(k));
                }
                rows.add(json);
            }

            results = new JSONArray(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // TODO: add required/option keys functionality
    static Map<String,String> getAjaxData(Request request) {
        String requestBody = request.body();
        String[] pairs = requestBody.split("&");
        HashMap<String,String> dataMap = new HashMap<>();
        for(String pair : pairs) {
            String[] temp = pair.split("=");
            dataMap.put(temp[0],temp[1]);
        }
        return dataMap;
    }

    static Map<String,String> getQueryParameters(Request request, String[] requiredKeys, String[] optionalKeys) {
        HashMap<String,String> queryMap = new HashMap<>();
        if (requiredKeys != null) {
            for (String k : requiredKeys) {
                String v = request.queryParams(k);
                if (v == null) {
                    System.err.println("Missing required query parameter: " + k);
                    return null;
                } else {
                    queryMap.put(k, v);
                }
            }
        }
        if (optionalKeys != null) {
            for (String k : optionalKeys) {
                String v = request.queryParams(k);
                if (v != null) {
                    queryMap.put(k, v);
                }
            }
        }
        return queryMap;
    }
}
