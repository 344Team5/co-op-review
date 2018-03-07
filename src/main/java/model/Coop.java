package model;

public class Coop {
    private final int id;
    private Student student;
    private Employer employer;

    private WorkReport workReport;
    private StudentEvaluation studentEvaluation;

    public Coop(int id, Student student, Employer employer) {
        this.id = id;
        this.student = student;
        this.employer = employer;
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

    public void setWorkReport(WorkReport workReport) {
        this.workReport = workReport;
    }

    public void setStudentEvaluation(StudentEvaluation studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
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
