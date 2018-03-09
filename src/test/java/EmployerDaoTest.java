import dao.DatabaseApi;
import dao.EmployerDao;
import db.FakeDB;
import model.Employer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployerDaoTest {
    private List<Employer> employerListTest;
    private EmployerDao employerDao;

    @BeforeEach
    void setUp() {

        employerDao = new DatabaseApi().getEmployerDao();
        FakeDB fakeDb = FakeDB.getFakeDB();
        employerListTest = fakeDb.employerList;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Employer> allEmployers = employerDao.getAll();
        for (int i = 0; i < allEmployers.size(); i++) {
            assertTrue(allEmployers.get(i).equals(employerListTest.get(i)));
        }
    }

    @Test
    void create() {
        //NOT IMPLEMENTED
    }

    @Test
    void get() {
        assertTrue(employerDao.get(0).equals(employerListTest.get(0)));
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