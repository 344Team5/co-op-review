package model;

public class Coop {
    private Student student;
    private Employer employer;

    private CoopWorkReport coopWorkReport;
    private StudentEvaluation studentEvaluation;

    public Coop(Student student, Employer employer) {
        this.student = student;
        this.employer = employer;
    }

    public Student getStudent() {
        return student;
    }

    public Employer getEmployer() {
        return employer;
    }

    public CoopWorkReport getCoopWorkReport() {
        return coopWorkReport;
    }

    public StudentEvaluation getStudentEvaluation() {
        return studentEvaluation;
    }

    public void setCoopWorkReport(CoopWorkReport coopWorkReport) {
        this.coopWorkReport = coopWorkReport;
    }

    public void setStudentEvaluation(StudentEvaluation studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
    }
}
