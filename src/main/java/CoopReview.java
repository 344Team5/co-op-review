import static spark.Spark.*;

import model.Coop;
import model.Employer;
import model.Student;
import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

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
            response.redirect("/student");
            return "";
        }));

        get("/student", ((request, response) -> { // "logged in" page
            return templateEngine.render(
                    new ModelAndView(null, "student.mustache")
            );
        }));

        get("/student/coops/register", (request, response) -> {
            response.status(501);
            return "Not implemented yet";
        });

        get("/student/coops", ((request, response) -> {
            return templateEngine.render(
                    new ModelAndView(null, "coop.mustache")
            );
        }));

        get("/review/:token", ((request, response) -> { // external reviewer completing student eval
            return templateEngine.render(
                    new ModelAndView(null, "eval.mustache")
            );
        }));

        post("/review/:token", (request, response) -> {
            return "Submitted successfully!";
        });

        get("/admin", (request, response) -> null);

        Map<String,Object> modelData = new HashMap<>();
        notFound((request, response) -> { // handle 404 error
            modelData.put("message", "404: Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(modelData, "error.mustache")
            );
        });

        internalServerError((request, response) -> { // handle 500 error
            modelData.put("message", "500: Something went wrong...");
            return templateEngine.render(
                    new ModelAndView(modelData, "error.mustache")
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
