import dao.DatabaseApi;
import dao.StudentDao;
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

class StudentDaoTest {

    private List<Coop> coopListTest;
    private List<Employer> employerListTest;
    private List<Student> studentListTest;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {

        studentDao = new DatabaseApi().getStudentDao();

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
        List<Student> allStudents = studentDao.getAll();
        for(int i = 0; i < allStudents.size(); i++){
            assertTrue(allStudents.get(i).equals(studentListTest.get(i)));
        }
    }

    @Test
    void create() {
        //NOT IMPLEMENTED
    }

    @Test
    void get() {
        assertTrue(studentDao.get(0).equals(studentListTest.get(0)));
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