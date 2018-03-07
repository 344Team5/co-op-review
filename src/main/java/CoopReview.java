import static spark.Spark.*;

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
    static FakeDB db = new FakeDB();
    public void init() {
        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        staticFiles.location("/public"); // set static files location to /public in resources

        HashMap<String, Object> modelData = new HashMap<>();

        /* Front End Routes */
        get("/", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(modelData, "homepage.mustache")
            );
        });

        post("/", ((request, response) -> { // this will be login in R2
            modelData.put("user", db.students.get(1).getName());
            response.redirect("/student");
            return "";
        }));

        get("/student", ((request, response) -> { // "logged in" page
            return templateEngine.render(
                    new ModelAndView(modelData, "student.mustache")
            );
        }));

        get("/student/coop/:cid", ((request, response) -> {
            int id = Integer.parseInt(request.params(":cid"));
            modelData.put("coop", db.coops.get(id));
            return templateEngine.render(
                    new ModelAndView(modelData, "coop.mustache")
            );
        }));

        get("/review/:token", ((request, response) -> null)); // external reviewer
        post("/review/:token", (request, response) -> "");
    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
