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

        before((req, res) -> { // redirect requests with trailing '/'
            String path = req.pathInfo();
            if (path.endsWith("/") && !path.equals("/"))
                res.redirect(path.substring(0, path.length() - 1));
        });

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
            s.addCoop(new Coop(3, s, new Employer(4, "Emp2","Addr2","Site2")));

            return templateEngine.render(
                    new ModelAndView(s, "student.mustache")
            );
        }));

        get("/student/coops/register", (request, response) -> {
            return "Not implemented yet";
        });

        get("/student/coops", ((request, response) -> {
            Student s = new Student(1, "David", "dwg", "242342");
            Coop c = new Coop(4, s, new Employer(5, "Emp3", "Addr3", "Site3"));
            c.setStudentEvaluation(c.new StudentEvaluation("Good job!"));
            return templateEngine.render(
                    new ModelAndView(c, "coop.mustache")
            );
        }));

        get("/review/:token", ((request, response) -> { // external reviewer completing student eval
            return templateEngine.render(
                    new ModelAndView(modelData, "eval.mustache")
            );
        }));

        post("/review/:token", (request, response) -> {
            return "Submitted successfully!";
        });

        get("/admin", (request, response) -> null);

        notFound((request, response) -> { // handle 404 error
            modelData.put("message", "Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(modelData, "error.mustache")
            );
        });

        internalServerError((request, response) -> { // handle 500 error
            modelData.put("message", "Something went wrong...");
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
