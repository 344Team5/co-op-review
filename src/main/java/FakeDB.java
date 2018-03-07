import model.*;

import java.util.HashMap;
import java.util.Map;

public class FakeDB {
    protected Map<Integer, Coop> coops;
    protected Map<Integer, Employer> employers;
    protected Map<Integer, EmployerReview> employerReviews;
    protected Map<Integer, Student> students;

    public FakeDB() {
        coops = new HashMap<>();
        employers = new HashMap<>();
        employerReviews = new HashMap<>();
        students = new HashMap<>();

        makeFakeData();
    }

    private void makeFakeData() {
        Student s1 = new Student("Betty", "bxw6666", "34292032");
        students.put(1,s1);

        Employer e1 = new Employer("Software Bros Ltd.", "1 Loop Way", "http://www.google.com/");
        employers.put(1,e1);

        Coop c1 = new Coop(s1,e1);
        c1.setCoopWorkReport("I fixed some bugs.");
        c1.setStudentEvaluation("The student fixed some bugs. 4/10 would not hire again");
        coops.put(1,c1);

        EmployerReview er1 = new EmployerReview(e1, "Good co-op company.");
        employerReviews.put(1,er1);
    }
}
