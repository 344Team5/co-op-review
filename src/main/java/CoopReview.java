import static spark.Spark.*;

import model.Coop;
import model.Employer;
import model.Student;
import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

/**
 * This is the starting point for the application.  Run this in IntelliJ to test
 * on the embedded Jetty webserver (localhost:4567)
 *
 * Read http://sparkjava.com/documentation.html
 */
public class CoopReview implements SparkApplication {
    public void init() {
        port(assignPort());

        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        staticFiles.location("/public"); // set static files location to /public in resources

        HashMap<String, Object> modelData = new HashMap<>();

        get("/", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(modelData, "homepage.mustache")
            );
        });

        post("/", ((request, response) -> { // this will be login in R2
            response.redirect("/student");
            return "";
        }));

        get("/student", ((request, response) -> { // "logged in" page
            Student s = new Student(1, "David", "dwg", "242342");
            s.addCoop(new Coop(2, s, new Employer(3, "Emp","Addr","Site")));

            return templateEngine.render(
                    new ModelAndView(s, "student.mustache")
            );
        }));

        get("/student/coop/:cid", ((request, response) -> {
            int id = Integer.parseInt(request.params(":cid"));
            return templateEngine.render(
                    new ModelAndView(modelData, "coop.mustache")
            );
        }));

        get("/review/:token", ((request, response) -> null)); // external reviewer
        post("/review/:token", (request, response) -> "");

        get("/admin", (request, response) -> null);
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
