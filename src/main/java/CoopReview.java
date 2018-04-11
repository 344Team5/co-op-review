import api.CoopApi;
import api.DatabaseApi;
import api.EmployerApi;
import api.StudentApi;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import db.FakeDB;
import model.Coop;
import model.Student;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Co-op Review Main class
 * Handles configuring and starting the web server
 */
public class CoopReview implements SparkApplication {
    /**
     * Set up the web application and handle requests
     */
    public void init() {
        port(assignPort()); // figure out which port to use
        DatabaseApi db = new DatabaseApi();

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine(); // create template engine to render pages

        staticFiles.location("/public"); // set static files location to /public in resources

        frontEndPageRoutes(templateEngine);
        errorPageRoutes(templateEngine);
        internalAPIRoutes(db);
    }

    private void frontEndPageRoutes(MustacheTemplateEngine templateEngine) {
        FakeDB.getFakeDB(); // initialize FakeDB
        DatabaseApi db = new DatabaseApi(); // create db API

        /*
        before((req, res) -> { // redirect requests with trailing '/'
            String path = req.pathInfo();
            if (path.endsWith("/") && !path.equals("/"))
                res.redirect(path.substring(0, path.length() - 1));
        });
        */
        before((req, res) -> { // redirect requests with trailing '/'
            System.out.println("Received request: " + req.url());
        });

        // Homepage (Login
        get("/", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "login.mustache")
            );
        });

        // Handle POST from login form
        post("/", ((request, response) -> { // this will be login in R2
            if (request.queryParams("username").equals("admin")) {
                response.redirect("/admin");
            }
            response.redirect("/student");
            return "";
        }));

        // Logged-in Student homepage
        get("/student", ((request, response) -> { // "logged in" page
            Map<String, Object> data = new HashMap<>();
            Student s = db.getStudentDao().get(1);
            data.put("student", s);
            List<Coop> coops = new ArrayList<>();
            for ( int id : s.getCoopIDs() ) {
                coops.add(db.getCoopDao().get(id));
            }
            data.put("coops", coops);

            // Get jobs from the GitHub jobs API
            List<Map<String, Object>> jobList = new ArrayList<>();
            for (JSONObject o : new ExternalApiRequest().getResults()) {
                String companyName = o.getString("company");
                if (companyName != null) {
                    jobList.add(o.toMap());
                }
            }
            data.put("jobs", jobList);

            return templateEngine.render(
                    new ModelAndView(data, "studentHome.mustache")
            );
        }));

        // Form to register a new Coop
        get("/student/coops/register", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "registerCoop.mustache")
            );
        });

        // Handle POST from new Coop form
        post("/student/coops/register", ((request, response) -> { // this will be login in R2
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            response.redirect("/student");
            return "";
        }));

        // Handle POST of Work Report form
        post("/student/coops/workreport", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            int id = Integer.parseInt(request.queryParams("coopID"));
            data.put("coop", db.getCoopDao().get(id));
            data.put("success", true);
            response.redirect("/student/coops?id=" + id);
            return "";
        });

        // Specific Co-op page, query by ID in R2
        get("/student/coops", ((request, response) -> {
            if (request.queryParams().contains("id")) {
                Map<String, Object> data = new HashMap<>();
                int id = Integer.parseInt(request.queryParams("id"));
                data.put("coop", db.getCoopDao().get(id));
                return templateEngine.render(
                        new ModelAndView(data, "coop.mustache")
                );
            } else {
                return ""; // This should 404 if no id was provided
            }
        }));

        // Form for External Reviewer to submit a StudentEvaluation
        get("/review/:token", ((request, response) -> { // external reviewer completing student eval
            return templateEngine.render(
                    new ModelAndView(db.getCoopDao().get(request.params("token")), "evaluateStudent.mustache")
            );
        }));

        // Handle POST from StudentEvaluation form
        post("/review/:token", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            return templateEngine.render(
                    new ModelAndView(data, "evaluateStudent.mustache")
            );
        });

        // All Employers page, unless given specific query in R2
        get("/employers", (request, response) -> {
            if (request.queryParams().contains("id")) {
                Map<String, Object> data = new HashMap<>();
                int id = Integer.parseInt(request.queryParams("id"));
                data.put("employer", db.getEmployerDao().get(id));
                return templateEngine.render(
                        new ModelAndView(data, "employer.mustache")
                );
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("employers", db.getEmployerDao().getAll());
                return templateEngine.render(
                        new ModelAndView(data, "employers.mustache")
                );
            }
        });

        // Handle POST from Employer Review form
        post("/employers/review", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            int id = Integer.parseInt(request.queryParams("employerID"));
            data.put("employer", db.getEmployerDao().get(id));
            data.put("success", true);
            return templateEngine.render(
                    new ModelAndView(data, "employer.mustache")
            );
        });

        // Admin control main page
        get("/admin", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("coops", db.getCoopDao().getAll());
            data.put("employers", db.getEmployerDao().getAll());
            return templateEngine.render(
                    new ModelAndView(data, "admin.mustache")
            );
        });

    }

    private void errorPageRoutes(MustacheTemplateEngine templateEngine) {
        // handle 404 error
        notFound((request, response) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "404: Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });

        // handle 500 error
        internalServerError((request, response) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "500: Something went wrong...");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });
    }

    private void internalAPIRoutes(DatabaseApi dbAPI) {
        /* API Routes */
        path("/api/v1", () -> {
            path("/students", () -> {
                get("", StudentApi::getStudents); // get all students
                post("", StudentApi::addStudent); // create a student

                path("/:sid", () -> { // one student
                    get("", StudentApi::getStudent); // read
                    put("", StudentApi::putStudent); // update/replace
                    patch("", StudentApi::patchStudent); // update/modify
                    delete("", StudentApi::deleteStudent); // delete
                });

            });
            path("/employers", () -> {
                get("", EmployerApi::getEmployers); // get all employers
                post("", EmployerApi::addEmployer); // create an employer

                path("/:eid", () -> { // one employer
                    get("", EmployerApi::getEmployer); // read
                    put("", EmployerApi::putEmployer); // update/replace
                    patch("", EmployerApi::patchEmployer); // update/modify
                    delete("", EmployerApi::deleteEmployer); // delete
                });
            });

            path("/coops", () -> {
                get("", CoopApi::getCoops); // get all co-ops
                post("", CoopApi::addCoop); // create a co-op

                path("/:cid", () -> { // one co-op
                    get("", CoopApi::getCoop); // read
                    put("", CoopApi::putCoop); // update/replace
                    patch("", CoopApi::patchCoop); // update/modify
                    delete("", CoopApi::deleteCoop); // delete
                });
            });
        });
        /* End API Routes */
    }

    /**
     * Find out what port to use based on where the application is (Default: 4567)
     */
    private int assignPort() {
        int port = 4567;
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return port;
    }

    /**
     * Start the web application
     */
    public static void main(String[] args) {
        new CoopReview().init();
    }

    // initial db stuff
//    /**
//     * Connect to database
//     */
//    private static ConnectionSource configureDatabaseConnection() throws SQLException {
//        String dbUrl = "jdbc:postgresql://" +
//                "ec2-23-21-121-220.compute-1.amazonaws.com" + ":" + // db host
//                "5432" + "/" + "postgresql-round-13420"; // db port and db name
//
//        //dbUrl = "postgres://vzyphdktribuxj:b4114ffd44cf0f42fce1dc46b2bc6af259ecd573c44eafa07154957e402f9546@ec2-23-21-121-220.compute-1.amazonaws.com:5432/d3qd2c89etbdbh"; // as per URI on heroku page
//        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
//        ((JdbcConnectionSource) connectionSource).setUsername("vzyphdktribuxj");
//        ((JdbcConnectionSource) connectionSource).setPassword("b4114ffd44cf0f42fce1dc46b2bc6af259ecd573c44eafa07154957e402f9546");
//        return connectionSource;
//    }
}
