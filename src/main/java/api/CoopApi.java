package api;

import spark.Request;
import spark.Response;

public class CoopApi extends DatabaseApi {
    public static Object getCoops(Request request, Response response) {
        return "Get all Students";
    }

    public static Object addCoop(Request request, Response response) {
        return "Add a Student";
    }

    public static Object getCoop(Request request, Response response) {
        return "Get a Student";
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
