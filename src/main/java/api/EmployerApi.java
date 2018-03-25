package api;

import spark.Request;
import spark.Response;

public class EmployerApi {

    public static Object getEmployers(Request request, Response response) {
        return "Get all Students";
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
