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
        port(assignPort());

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        staticFiles.location("/public"); // set static files location to /public in resources

        before((req, res) -> { // redirect requests with trailing '/'
            String path = req.pathInfo();
            if (path.endsWith("/") && !path.equals("/"))
                res.redirect(path.substring(0, path.length() - 1));
        });

        get("/", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "homepage.mustache")
            );
        });

        post("/", ((request, response) -> { // this will be login in R2
            if (request.queryParams("username").equals("admin")) {
                response.redirect("/admin");
            }
            response.redirect("/student");
            return "";
        }));

        get("/student", ((request, response) -> { // "logged in" page
            Map<String,Object> data = new HashMap<>();
            data.put("student", studentDao.get(0));

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

        get("/student/coops/register", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "register.mustache")
            );
        });

        post("/student/coops/register", ((request, response) -> { // this will be login in R2
            Map<String,Object> data = new HashMap<>();
            data.put("success", true);
            return templateEngine.render(
                    new ModelAndView(data, "register.mustache")
            );
        }));

        get("/student/coops", ((request, response) -> {
            return templateEngine.render(
                    new ModelAndView(coopDao.get(0), "coop.mustache")
            );
        }));

        get("/review/:token", ((request, response) -> { // external reviewer completing student eval
            return templateEngine.render(
                    new ModelAndView(coopDao.get(0), "eval.mustache")
            );
        }));

        post("/review/:token", (request, response) -> {
            return "Submitted successfully!";
        });

        get("/employers", (request, response) -> {
           return templateEngine.render(
                   new ModelAndView(employerDao.getAll(), "employers.mustache")
           );
        });

        get("/admin",(request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "admin.mustache")
            );
        });

        notFound((request, response) -> { // handle 404 error
            Map<String,Object> data = new HashMap<>();
            data.put("message", "404: Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });

        internalServerError((request, response) -> { // handle 500 error
            Map<String,Object> data = new HashMap<>();
            data.put("message", "500: Something went wrong...");
            return templateEngine.render(
                    new ModelAndView(data, "error.mustache")
            );
        });
    }

    private int assignPort() {
        int port = 4567;
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return port;
    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
