import dao.CoopDao;
import dao.DatabaseApi;
import db.FakeDB;
import model.Coop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CoopDaoTest {

    private List<Coop> coopListTest;
    private CoopDao coopDao;

    @BeforeEach
    void setUp() {
        coopDao = new DatabaseApi().getCoopDao();
        FakeDB fakeDb = FakeDB.getFakeDB();
        coopListTest = fakeDb.coopList;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Coop> allCoops = coopDao.getAll();
        for (int i = 0; i < allCoops.size(); i++) {
            assertTrue(allCoops.get(i).equals(coopListTest.get(i)));
        }
    }

    @Test
    void create() {
        //NOT IMPLEMENTED
    }

    /**
     * Can't test this accurately with dates yet. The dates are always different due to way dates are generated
     */
    @Test
    void get() {
        assertTrue(coopDao.get(0).equals(coopListTest.get(0)));
    }

    @Test
    void update() {
        //NOT IMPLEMENTED
    }

    @Test
    void delete() {
        //NOT IMPLEMENTED
    }
}