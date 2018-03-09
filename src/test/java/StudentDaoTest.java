import dao.DatabaseApi;
import dao.StudentDao;
import db.FakeDB;
import model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentDaoTest {

    private List<Student> studentListTest;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new DatabaseApi().getStudentDao();
        FakeDB fakeDb = FakeDB.getFakeDB();
        studentListTest = fakeDb.studentList;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Student> allStudents = studentDao.getAll();
        for (int i = 1; i < allStudents.size(); i++) {
            assertTrue(allStudents.get(i).equals(studentListTest.get(i)));
        }
    }

    @Test
    void create() {
        //NOT IMPLEMENTED
    }

    @Test
    void get() {
        assertTrue(studentDao.get(1).equals(studentListTest.get(1)));
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