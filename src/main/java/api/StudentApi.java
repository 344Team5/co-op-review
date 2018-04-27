package api;

import spark.Request;
import spark.Response;

import javax.xml.crypto.Data;

public class StudentApi {

    public static Object getStudents(Request request, Response response) {
        return "";
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
