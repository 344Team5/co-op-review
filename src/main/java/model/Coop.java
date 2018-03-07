package model;

public class Coop {
    private Student student;
    private Employer employer;

    private String coopWorkReport;
    private String studentEvaluation;

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

    public String getCoopWorkReport() {
        return coopWorkReport;
    }

    public String getStudentEvaluation() {
        return studentEvaluation;
    }

    public void setCoopWorkReport(String coopWorkReport) {
        this.coopWorkReport = coopWorkReport;
    }

    public void setStudentEvaluation(String studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
    }
}
