package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    static Object getQueryResultsJson(ResultSet rs, String ... keys) {
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
}
