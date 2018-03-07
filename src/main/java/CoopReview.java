import static spark.Spark.*;

import api.Coops;
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
        /* End Front End Routes */

        /* API Routes */
        path("api/v1", () -> {
            path("/students", () -> {
                get("/", (request, response) -> null); // get all students
                post("/", (request, response) -> null); // create a student

                path("/:sid", () -> { // one student
                    get("/", (request, response) -> null); // read
                    put("/", (request, response) -> null); // update/replace
                    patch("/", (request, response) -> null); // update/modify
                    delete("/", (request, response) -> null); // delete
                });

            });
            path("/employers", () -> {
                get("/", (request, response) -> null); // get all employers
                post("/", (request, response) -> null); // create an employer

                path("/:eid", () -> { // one employer
                    get("/", (request, response) -> null); // read
                    put("/", (request, response) -> null); // update/replace
                    patch("/", (request, response) -> null); // update/modify
                    delete("/", (request, response) -> null); // delete

                    get("/name", (request, response) -> null); // get employer name
                    get("/address", (request, response) -> null); // get employer address
                    get("/website", ((request, response) -> null)); // get employer website
                });
            });

            path("/coops", () -> {
                get("/", ((request, response) -> null)); // get all co-ops
                post("/", ((request, response) -> null)); // create a co-op

                path("/:cid", () -> { // one co-op
                    get("/", (request, response) -> null); // read
                    put("/", (request, response) -> null); // update/replace
                    patch("/", (request, response) -> null); // update/modify
                    delete("/", (request, response) -> null); // delete

                    get("/student", (request, response) -> null); // get the co-op's associated student
                    get("/employer", ((request, response) -> null)); // get the co-op's associated employer
                    get("/studentEvaluation", ((request, response) -> null)); // get the student evaluation
                    get("/coopWorkReport", ((request, response) -> null)); // get the co-op work report
                    get("/employerReview", (request, response) -> null); //get the employer review
                });
            });
        });
        /* End API Routes */
    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
