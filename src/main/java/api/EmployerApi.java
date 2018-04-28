package api;

import spark.Request;
import spark.Response;

import java.sql.*;

public class EmployerApi extends DatabaseApi {

    public static Object getEmployers(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM employers;");
                result = getSQLQueryResultsJson(rs, "id", "name", "avg_salary", "reviews");
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

    public static Object addEmployer(Request request, Response response) {
        return "Add an Employer";
    }

    public static Object getEmployer(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM employers WHERE id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":eid"));
                System.out.println(st);
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "reviews", "name", "avg_salary", "id");
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

    public static Object putEmployer(Request request, Response response) {
        return "Create/Update an Employer";
    }

    public static Object patchEmployer(Request request, Response response) {
        return "Modify an Employer";
    }

    public static Object deleteEmployer(Request request, Response response) {
        return "Delete an Employer";
    }
}
