import model.*;

import java.util.HashMap;
import java.util.Map;

public class FakeDB {
    protected Map<Integer, Coop> coops;
    protected Map<Integer, CoopWorkReport> coopWorkReports;
    protected Map<Integer, Employer> employers;
    protected Map<Integer, EmployerReview> employerReviews;
    protected Map<Integer, Student> students;
    protected Map<Integer, StudentEvaluation> studentEvaluations;

    public FakeDB() {
        coops = new HashMap<>();
        coopWorkReports = new HashMap<>();
        employers = new HashMap<>();
        employerReviews = new HashMap<>();
        students = new HashMap<>();
        studentEvaluations = new HashMap<>();

        makeFakeData();
    }

    private void makeFakeData() {
        Student s1 = new Student("Betty", "bxw6666", "34292032");
        students.put(1,s1);

        Employer e1 = new Employer("Software Bros Ltd.", "1 Loop Way", "http://www.google.com/");
        employers.put(1,e1);

        Coop c1 = new Coop(s1,e1);
        CoopWorkReport cwr1 = new CoopWorkReport("I fixed some bugs.");
        StudentEvaluation se1 = new StudentEvaluation("Student fixed bugs.");
        c1.setCoopWorkReport(cwr1);
        c1.setStudentEvaluation(se1);
        coops.put(1,c1);
        coopWorkReports.put(1,cwr1);
        studentEvaluations.put(1,se1);

        EmployerReview er1 = new EmployerReview(e1, "Good co-op company.");
        employerReviews.put(1,er1);
    }
}
