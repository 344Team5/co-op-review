import static spark.Spark.*;

import dao.CoopDao;
import dao.EmployerDao;
import dao.StudentDao;
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

/**
 * This is the starting point for the application.  Run this in IntelliJ to test
 * on the embedded Jetty webserver (localhost:4567)
 *
 * Read http://sparkjava.com/documentation.html
 */
public class CoopReview implements SparkApplication {

    private CoopDao coopDao = new CoopDao();
    private EmployerDao employerDao = new EmployerDao();
    private StudentDao studentDao = new StudentDao();

    public void init() {
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
                    new ModelAndView(null, "homepage.mustache")
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
            Map<String,Object> data = new HashMap<>();
            data.put("student", studentDao.get(0));

            // Get jobs from the GitHub jobs API
            List<Map<String,Object>> jobList = new ArrayList<>();
            for (JSONObject o : new ExternalApiRequest().getResults() ) {
                String companyName = o.getString("company");
                if (companyName != null) {
                    jobList.add(o.toMap());
                }
            }
            data.put("jobs", jobList);

            return templateEngine.render(
                    new ModelAndView(data, "student.mustache")
            );
        }));

        // Form to register a new Coop
        get("/student/coops/register", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "register.mustache")
            );
        });

        // Handle POST from new Coop form
        post("/student/coops/register", ((request, response) -> { // this will be login in R2
            Map<String,Object> data = new HashMap<>();
            data.put("success", true);
            return templateEngine.render(
                    new ModelAndView(data, "register.mustache")
            );
        }));

        // Specific Co-op page, query by ID in R2
        get("/student/coops", ((request, response) -> {
            return templateEngine.render(
                    new ModelAndView(coopDao.get(0), "coop.mustache")
            );
        }));

        // Form for External Reviewer to submit a StudentEvaluation
        get("/review/:token", ((request, response) -> { // external reviewer completing student eval
            return templateEngine.render(
                    new ModelAndView(coopDao.get(0), "eval.mustache")
            );
        }));

        // Handle POST from StudentEvaluation form
        post("/review/:token", (request, response) -> {
            return "Submitted successfully!";
        });

        // All Employers page, unless given specific query in R2
        get("/employers", (request, response) -> {
           return templateEngine.render(
                   new ModelAndView(employerDao.getAll(), "employers.mustache")
           );
        });

        // Admin control main page
        get("/admin",(request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "admin.mustache")
            );
        });

        /* END ROUTES */


        // handle 404 error
        notFound((request, response) -> {
            Map<String,Object> data = new HashMap<>();
            data.put("message", "404: Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });

        // handle 500 error
        internalServerError((request, response) -> {
            Map<String,Object> data = new HashMap<>();
            data.put("message", "500: Something went wrong...");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });
    }

    /** Find out what port to use based on where the application is (Default: 4567) */
    private int assignPort() {
        int port = 4567;
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return port;
    }

    /** Start the web application */
    public static void main(String[] args) {
        new CoopReview().init();
    }
}
