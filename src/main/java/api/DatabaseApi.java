package api;

import org.json.JSONStringer;
import spark.Request;
import spark.Response;

/**
 * The entire database API, consisting of a collection of individual Data Access Objects
 */
public class DatabaseApi {
    protected CoopDao coopDao; // the Coop DAO
    protected EmployerDao employerDao; // the Employer DAO
    protected StudentDao studentDao; // the Student DAO

    /**
     * Initialize the API
     */
    public DatabaseApi() {
        coopDao = new CoopDao();
        employerDao = new EmployerDao();
        studentDao = new StudentDao();
    }

    /**
     * Get the Coop DAO
     */
    public CoopDao getCoopDao() {
        return coopDao;
    }

    /**
     * Get the Employer DAO
     */
    public EmployerDao getEmployerDao() {
        return employerDao;
    }

    /* Get the Student DAO */
    public StudentDao getStudentDao() {
        return studentDao;
    }

    public Object studentsRequest(Request request, Response response) {
        return "GET: All Students";
    }
}
