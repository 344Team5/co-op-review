import dao.DatabaseApi;
import dao.EmployerDao;
import db.FakeDB;
import model.Coop;
import model.Employer;
import model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployerDaoTest {
    private List<Coop> coopListTest;
    private List<Employer> employerListTest;
    private List<Student> studentListTest;
    private EmployerDao employerDao;

    @BeforeEach
    void setUp() {

        employerDao = new DatabaseApi().getEmployerDao();

        coopListTest  = new ArrayList<>();
        employerListTest = new ArrayList<>();
        studentListTest = new ArrayList<>();

        FakeDB fakeDb = FakeDB.getFakeDB();

        coopListTest = fakeDb.coopList;
        employerListTest = fakeDb.employerList;
        studentListTest = fakeDb.studentList;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Employer> allEmployers = employerDao.getAll();
        for(int i = 0; i < allEmployers.size(); i++){
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