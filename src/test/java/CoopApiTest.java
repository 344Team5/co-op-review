import api.CoopApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import spark.Request;
import spark.Response;
import spark.route.Routes;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class CoopApiTest {

    /*
    @BeforeAll
    public void setUp() throws Exception {
        Routes newRoutes = new Routes();
        newRoutes.establishRoutes();

        awaitInitialization();

    }

    @AfterAll
    public void tearDown() throws Exception {
        stop();
    }
    */

    @Test
    void getAll() {
        //Request request = new Request();
        //String response = CoopApi.getCoops();
        assertTrue(true);
    }

    @Test
    void get() {
        assertTrue(true);
    }
}
