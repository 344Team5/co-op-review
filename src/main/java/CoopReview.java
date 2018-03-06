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
        staticFiles.location("/public"); // set static files location to /public in resources

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("name", "Student");
            return new MustacheTemplateEngine().render(
                    new ModelAndView(model, "homepage.mustache")
            );
        });
    }

    public static void main(String[] args) {
        new CoopReview().init();
    }
}
