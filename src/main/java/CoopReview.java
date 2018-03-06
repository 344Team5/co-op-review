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

        get("/coops", ((request, response) -> { // "logged in" page
            return templateEngine.render(
                    new ModelAndView(model, "coops.mustache")
            );
        }));

        /* Example path route for API
        path("/api", () -> {
            path("/email", () -> {
                post("/add",       EmailApi.addEmail);
                put("/change",     EmailApi.changeEmail);
                delete("/remove",  EmailApi.deleteEmail);
            });
            path("/username", () -> {
                post("/add",       UserApi.addUsername);
                put("/change",     UserApi.changeUsername);
                delete("/remove",  UserApi.deleteUsername);
            });
        });
        */
    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
