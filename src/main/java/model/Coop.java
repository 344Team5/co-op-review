package model;

import java.util.Date;

public class Coop {
    private final int id;
    private Student student;
    private Employer employer;

    private Date startDate;
    private Date endDate;

    private WorkReport workReport;
    private StudentEvaluation studentEvaluation;
    private String reviewToken;

    public Coop(int id, Student student, Employer employer, Date startDate, Date endDate) {
        this.id = id;

        this.student = student;
        this.student.addCoop(this);

        this.employer = employer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewToken = "12345678";
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Employer getEmployer() {
        return employer;
    }

    public WorkReport getWorkReport() {
        return workReport;
    }

    public StudentEvaluation getStudentEvaluation() {
        return studentEvaluation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setWorkReport(WorkReport workReport) {
        this.workReport = workReport;
    }

    public void setStudentEvaluation(StudentEvaluation studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
    }

    public String getReviewToken() {
        return reviewToken;
    }

    @Override
    public String toString() {
        return "Coop at" + employer;
    }

    public class WorkReport {
        private String content;

        public WorkReport(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    public class StudentEvaluation {
        private String content;

        public StudentEvaluation(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
