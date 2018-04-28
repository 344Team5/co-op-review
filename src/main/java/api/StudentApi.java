package api;

import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.Map;

public class StudentApi extends DatabaseApi {

    public static Object getStudents(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE admin = FALSE;");
                result = getSQLQueryResultsJson(rs, "uid", "name");
                response.type("application/json");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static Object addStudent(Request request, Response response) {
        return "Add a Student";
    }

    public static Object getStudent(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM users WHERE uid = ? AND admin = FALSE;");
                st.setString(1, request.params().get(":sid"));
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "uid", "name");
                response.type("application/json");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
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
