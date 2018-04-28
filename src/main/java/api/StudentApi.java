package api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentApi extends DatabaseApi {

    public static Object getStudents(Request request, Response response) throws SQLException {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE admin = FALSE;");
                result = getQueryResultsJson(rs, "uid", "name");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) st.close();
            }
        }
        return result;
    }

    public static Object addStudent(Request request, Response response) {
        return "Add a Student";
    }

    public static Object getStudent(Request request, Response response) {
        return "Get a Student";
    }

    public static Object putStudent(Request request, Response response) {
        return "Create/Update a Student";
    }

    public static Object patchStudent(Request request, Response response) {
        return "Modify a Student";
    }

    public static Object deleteStudent(Request request, Response response) {
        return "Delete a Student";
    }
}
