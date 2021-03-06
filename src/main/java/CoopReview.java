import api.*;
import oauth.OAuthConfigFactory;
import org.json.JSONObject;
import org.kohsuke.github.GitHub;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.pac4j.sparkjava.SecurityFilter;
import org.pac4j.sparkjava.SparkWebContext;
import org.pac4j.sparkjava.LogoutRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.servlet.SparkApplication;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Co-op Review Main class
 * Handles configuring and starting the web server
 * and OAuth login using example code from github/pconrad
 */
public class CoopReview implements SparkApplication {
    /**
     * Set up the web application and handle requests
     */
    public void init() {
        port(assignPort()); // figure out which port to use;

        HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine(); // create template engine to render pages

        staticFiles.location("/public"); // set static files location to /public in resources

        SecurityFilter githubFilter = createSecurityFilter(templateEngine);
        gitHubOAuthSetup(templateEngine, githubFilter);
        frontEndPageRoutes(templateEngine);
        errorPageRoutes(templateEngine);
        internalAPIRoutes(githubFilter);
    }

    private Config buildConfig(TemplateEngine templateEngine){
        HashMap<String,String> envVars =
                getEnvironmentVariables("GITHUB_CLIENT_ID",
                        "GITHUB_CLIENT_SECRET",
                        "GITHUB_CALLBACK_URL",
                        "APPLICATION_SALT");

        return new
                OAuthConfigFactory(envVars.get("GITHUB_CLIENT_ID"),
                envVars.get("GITHUB_CLIENT_SECRET"),
                envVars.get("GITHUB_CALLBACK_URL"),
                envVars.get("APPLICATION_SALT"),
                templateEngine).build();
    }

    private SecurityFilter createSecurityFilter(TemplateEngine templateEngine) {
        Config config = buildConfig(templateEngine);
        return new SecurityFilter(config, "GithubClient", "", "");
    }

    private void gitHubOAuthSetup(TemplateEngine templateEngine, SecurityFilter githubFilter) {
        Config config = buildConfig(templateEngine);
        final org.pac4j.sparkjava.CallbackRoute callback =
                new org.pac4j.sparkjava.CallbackRoute(config);

        get("/callback", callback);
        post("/callback", callback);

        get("/",
                (request, response) -> new ModelAndView(buildModel(request,response),"login.hbs"),
                templateEngine);

        before("/login", githubFilter);

        get("/login",
                (request, response) -> new ModelAndView(buildModel(request,response),"login.hbs"),
                templateEngine);

        get("/logout", new LogoutRoute(config, "/"));

        /* ------------------ */
        get("/github",
                (request, response) ->
                        new ModelAndView(buildModel(request,response), "github.hbs"), templateEngine);
        /* ------------------ */
    }

    private void frontEndPageRoutes(TemplateEngine templateEngine) {
        before((req, res) -> {
            System.out.println("Received request: " + req.requestMethod() + " " + req.url());
        });

        get("/student", ((request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            String userid = model.get("userid").toString();
            boolean isAdmin = StudentApi.isAdmin(userid);
            if (isAdmin) {
                response.redirect("/admin");
            } else {
                response.redirect("/students/"+userid);
            }
            return "";
        }));

        get("/students/:sid", ((request, response) -> { // "logged in" page
            Map<String, Object> model = buildModel(request,response);
            model.put("sid", request.params(":sid"));

            // Get jobs from the GitHub jobs API
            List<Map<String, Object>> jobList = new ArrayList<>();
            for (JSONObject o : new ExternalApiRequest().getResults()) {
                String companyName = o.getString("company");
                if (companyName != null) {
                    jobList.add(o.toMap());
                }
            }
            model.put("jobs", jobList);

            return templateEngine.render(
                    new ModelAndView(model, "student.hbs")
            );
        }));

        // Form to register a new Coop
        get("/coops/new/", (request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            return templateEngine.render(
                    new ModelAndView(model, "registerCoop.hbs")
            );
        });

        // Specific Co-op page
        get("/coops/:cid", ((request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            model.put("cid",request.params(":cid"));
            return templateEngine.render(
                new ModelAndView(model, "coop.hbs")
            );
        }));

        // Form for External Reviewer to submit a StudentEvaluation
        get("/coops/:cid/eval/:token", ((request, response) -> { // external reviewer completing student eval
            Map<String, Object> model = buildModel(request,response);
            //validate token?
            model.put("cid", request.params(":cid"));
            model.put("token", request.params(":token"));
            return templateEngine.render(
                    new ModelAndView(model, "evaluateStudent.hbs")
            );
        }));

        /*
        // POST handled by API
        post("/review/:token", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            return templateEngine.render(
                    new ModelAndView(data, "evaluateStudent.hbs")
            );
        });
       */

        // All Employers page
        get("/employers", (request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            return templateEngine.render(new ModelAndView(model, "employers.hbs"));
        });

        // Employer page
        get("/employers/:eid", (request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            model.put("eid",request.params(":eid"));
            return templateEngine.render(new ModelAndView(model, "employer.hbs"));
        });

        /*
        // POST handled by API
        post("/employers/:eid/review", (request, response) -> {
            Map<String, Object> data = new HashMap<>();
            int id = Integer.parseInt(request.queryParams("employerID"));
            //data.put("employer", db.getEmployerDao().get(id));
            data.put("success", true);
            data.put("eid",request.params("eid"));
            return templateEngine.render(
                    new ModelAndView(data, "employer.hbs")
            );
        });
        */

        // Admin control main page
        get("/admin", (request, response) -> {
            Map<String, Object> model = buildModel(request,response);
            String userid = model.get("userid").toString();
            boolean canAccess = StudentApi.isAdmin(userid);
            if (canAccess) {
                return templateEngine.render(
                        new ModelAndView(model, "admin.hbs")
                );
            } else {
                return genericErrorPage(request, response,
                        "401: You're not allowed to go there!",
                        401,
                        templateEngine);
            }
        });
    }

    private Object genericErrorPage(Request request, Response response, String message, int statusCode, TemplateEngine templateEngine) {
        Map<String, Object> model = buildModel(request,response);
        response.status(statusCode);
        model.put("message", message);
        return templateEngine.render(
                new ModelAndView(model, "error.hbs")
        );
    }

    private void errorPageRoutes(TemplateEngine templateEngine) {
        // handle 404 error
        notFound((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "404: Whoops, couldn't find that page!");
            return templateEngine.render(
                    new ModelAndView(model, "error.hbs")
            );
        });

        // handle 500 error
        internalServerError((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "500: Something went wrong...");
            return templateEngine.render(
                    new ModelAndView(model, "error.hbs")
            );
        });
    }

    private void internalAPIRoutes(SecurityFilter githubFilter) {
        /* API Routes */
        path("/api/v1", () -> {
            //before("/*", githubFilter);
            path("/students", () -> {
                get("", StudentApi::getStudents); // get all students
                post("", StudentApi::addStudent); // create a student

                path("/:sid", () -> { // one student
                    get("", StudentApi::getStudent); // read
                    //put("", StudentApi::putStudent); // update/replace
                    patch("", StudentApi::patchStudent); // update/modify
                    delete("", StudentApi::deleteStudent); // delete

                    get("/coops", StudentApi::getCoops); // get student with their co-ops
                });

            });
            path("/employers", () -> {
                get("", EmployerApi::getEmployers); // get all employers
                post("", EmployerApi::addEmployer); // create an employer

                path("/:eid", () -> { // one employer
                    get("", EmployerApi::getEmployer); // read
                    //put("", EmployerApi::putEmployer); // update/replace
                    patch("", EmployerApi::patchEmployer); // update/modify
                    delete("", EmployerApi::deleteEmployer); // delete
                });
            });

            path("/coops", () -> {
                get("", CoopApi::getCoops); // get all co-ops
                post("", CoopApi::addCoop); // create a co-op

                path("/:cid", () -> { // one co-op
                    get("", CoopApi::getCoop); // read
                    //put("", CoopApi::putCoop); // update/replace
                    patch("", CoopApi::patchCoop); // update/modify
                    delete("", CoopApi::deleteCoop); // delete

                    post("/eval/:token", CoopApi::handleStudentEval); // handle student evaluation form
                });
            });
        });
        /* End API Routes */
    }

    /**
     * Find out what port to use based on where the application is (Default: 4567)
     */
    private int assignPort() {
        int port = 4567;
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            port = Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return port;
    }

    private static List<CommonProfile> getProfiles(final Request request,
                                                             final Response response) {
        final SparkWebContext context = new SparkWebContext(request, response);
        final ProfileManager manager = new ProfileManager(context);
        return manager.getAll(true);
    }

    private static Map<String,Object> buildModel(Request request, Response response) {

        final Map<String,Object> model = new HashMap<>();

        Map<String, Object> map = new HashMap<String, Object>();
        for (String k: request.session().attributes()) {
            Object v = request.session().attribute(k);
            map.put(k,v);
        }

        model.put("session", map.entrySet());

        java.util.List<CommonProfile> userProfiles = getProfiles(request,response);



        try {
            if (userProfiles.size()>0) {
                CommonProfile firstProfile = userProfiles.get(0);
                map.put("firstProfile", firstProfile);

                GitHubProfile ghp = (GitHubProfile) firstProfile;
                model.put("ghp", ghp);
                model.put("userid",ghp.getUsername());
                model.put("name",ghp.getDisplayName());
                model.put("avatar_url",ghp.getPictureUrl());
                model.put("email",ghp.getEmail());
                if (StudentApi.isAdmin(ghp.getUsername())) {
                    model.put("admin", "true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    private HashMap<String,String> getEnvironmentVariables(String... variables) {
        HashMap<String,String> envVars = new HashMap<String,String>();

        for (String k : variables) {
            String v = System.getenv(k);
            envVars.put(k,v);
        }

        boolean error=false;
        for (String k:variables) {
            if (envVars.get(k)==null) {
                error = true;
                System.err.println("Error: Must define environment variable " + k);
            }
        }
        if (error) { System.exit(1); }

        return envVars;
    }

    /**
     * Start the web application
     */
    public static void main(String[] args) {
        new CoopReview().init();
    }
}
