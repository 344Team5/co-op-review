package api;

import spark.Request;
import spark.Response;

import java.sql.*;

public class CoopApi extends DatabaseApi {
    public static Object getCoops(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM coops;");
                result = getSQLQueryResultsJson(rs, "id", "start_date", "end_date", "work_report",
                        "student_eval", "eval_token", "student_uid", "employer_id");
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

    public static Object addCoop(Request request, Response response) {
        return "Add a Student";
    }

    public static Object getCoop(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM coops where id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":cid"));
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "id", "start_date", "end_date", "work_report",
                        "student_eval", "eval_token", "student_uid", "employer_id");
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

    public static Object putCoop(Request request, Response response) {
        return "Create/Update a Student";
    }

    public static Object patchCoop(Request request, Response response) {
        return "Modify a Student";
    }

    public static Object deleteCoop(Request request, Response response) {
        return "Delete a Student";
    }
}
