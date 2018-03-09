package dao;

/**
 * The entire database API, consisting of a collection of individual Data Access Objects
 */
public class DatabaseApi {
    private CoopDao coopDao; // the Coop DAO
    private EmployerDao employerDao; // the Employer DAO
    private StudentDao studentDao; // the Student DAO

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
}
