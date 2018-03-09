import dao.DatabaseApi;
import db.FakeDB;
import model.Coop;
import model.Employer;
import model.Student;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.mustache.MustacheTemplateEngine;

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
        FakeDB.getFakeDB(); // initialize FakeDB
        DatabaseApi db = new DatabaseApi(); // create db API

        port(assignPort()); // figure out which port to use

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine(); // create template engine to render pages

        staticFiles.location("/public"); // set static files location to /public in resources

        /* START ROUTES */
        before((req, res) -> { // redirect requests with trailing '/'
            String path = req.pathInfo();
            if (path.endsWith("/") && !path.equals("/"))
                res.redirect(path.substring(0, path.length() - 1));
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
            return templateEngine.render(
                    new ModelAndView(data, "registerCoop.mustache")
            );
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

        /* END ROUTES */


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
}
