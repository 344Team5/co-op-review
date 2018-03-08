package db;

import dao.StudentDao;
import model.Coop;
import model.Employer;
import model.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Fake DB for testing in R1
 */
public class FakeDB
{
    public static FakeDB db;
    public List<Coop> coopList;
    public List<Employer> employerList;
    public List<Student> studentList;

    public FakeDB() {
        coopList = new ArrayList<>();
        employerList = new ArrayList<>();
        studentList = new ArrayList<>();

        Student s1,s2;
        s1 = new Student(1,"Betty White", "bxw7777", "3545223421");
        s2 = new Student(2, "John Stamos", "jxs3424", "2359234943");
        studentList.add(s1);
        studentList.add(s2);

        Employer e1,e2;
        e1 = new Employer(1, "Google", "1600 Amphitheatre Pkwy", "google.com");
        e2 = new Employer(2, "Amazon", "410 Terry Ave. North", "amazon.com");
        employerList.add(e1);
        employerList.add(e2);

        Coop c1,c2;
        c1 = new Coop(1, s1, e1, new Date(), new Date());
        c1.setStudentEvaluation(c1.new StudentEvaluation("Betty was an awesome co-op student. 10/10 would hire"));
        c1.setWorkReport(c1.new WorkReport("During my co-op at Google, I wrote code."));
        c2 = new Coop(2, s2, e2, new Date(), new Date());
        coopList.add(c1);
        coopList.add(c2);
    }

    public static FakeDB getFakeDB() {
        if (db == null){
            db = new FakeDB();
        }
        return db;
    }
}
