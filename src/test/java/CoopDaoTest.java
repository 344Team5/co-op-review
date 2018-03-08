import dao.CoopDao;
import dao.DatabaseApi;
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

class CoopDaoTest {

    private ArrayList<Coop> coopListTest;
    private ArrayList<Employer> employerListTest;
    private ArrayList<Student> studentListTest;
    private CoopDao coopDao;

    @BeforeEach
    void setUp() {

        coopDao = new DatabaseApi().getCoopDao();

        coopListTest  = new ArrayList<>();
        employerListTest = new ArrayList<>();
        studentListTest = new ArrayList<>();

        Student s1,s2;
        s1 = new Student(1,"Betty White", "bxw7777", "3545223421");
        s2 = new Student(2, "John Stamos", "jxs3424", "2359234943");
        studentListTest.add(s1);
        studentListTest.add(s2);

        Employer e1,e2;
        e1 = new Employer(1, "Google", "1600 Amphitheatre Pkwy", "google.com");
        e2 = new Employer(2, "Amazon", "410 Terry Ave. North", "amazon.com");
        employerListTest.add(e1);
        employerListTest.add(e2);

        Coop c1,c2;
        c1 = new Coop(1, s1, e1, new Date(), new Date());
        c1.setStudentEvaluation(c1.new StudentEvaluation("Betty was an awesome co-op student. 10/10 would hire"));
        c1.setWorkReport(c1.new WorkReport("During my co-op at Google, I wrote code."));
        c2 = new Coop(2, s2, e2, new Date(), new Date());
        coopListTest.add(c1);
        coopListTest.add(c2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Coop> allCoops = coopDao.getAll();
        for(int i = 0; i < allCoops.size(); i++){
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