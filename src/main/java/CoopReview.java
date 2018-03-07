import static spark.Spark.*;

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
        MustacheTemplateEngine templateEngine = new MustacheTemplateEngine();

        staticFiles.location("/public"); // set static files location to /public in resources

        Map<String, Object> model = new HashMap<>();

        get("/", (request, response) -> {
            return templateEngine.render(
                    new ModelAndView(model, "homepage.mustache")
            );
        });

        post("/", ((request, response) -> { // this will be login in R2
            String username = request.queryParams("username");
            model.put("user", username); // fake login
            response.redirect("/coops");
            return "";
        }));

        get("/student", ((request, response) -> { // "logged in" page
            return templateEngine.render(
                    new ModelAndView(model, "student.mustache")
            );
        }));


        /* API ROUTES
        path("api/v1", () -> {
            path("/students", () -> {
                get("/", (request, response) -> null); // get all students
                get("/:sid", (request, response) -> "hi"); // get one student
            });

            path("/employers", () -> {
                get("/", ((request, response) -> null)); // get all employers
                get("/:eid", (request, response) -> null); // get one employer
            });

            get("/coops", ((request, response) -> null)); // get all co-ops
            path("/coops/:cid", () -> {
                get("/", ((request, response) -> null)); // get one co-op
                get("/student", (request, response) -> null); // get the co-op's associated student
                get("/employer", ((request, response) -> null)); // get the co-op's associated employer
                get("/studentEvaluation", ((request, response) -> null)); // get the student evaluation
                get("/coopWorkReport", ((request, response) -> null)); // get the co-op work report
                get("/employerReview", (request, response) -> null); //get the employer review
            });
        });
        */
        /* END API ROUTES */


    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
