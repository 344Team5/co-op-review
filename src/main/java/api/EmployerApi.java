package api;

import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        return "Add a Student";
    }

    public static Object getEmployer(Request request, Response response) {
        return "Get a Student";
    }

    public static Object putEmployer(Request request, Response response) {
        return "Create/Update a Student";
    }

    public static Object patchEmployer(Request request, Response response) {
        return "Modify a Student";
    }

    public static Object deleteEmployer(Request request, Response response) {
        return "Delete a Student";
    }
}
