package model;

import java.util.Date;

/**
 * The Coop class represents a single Coop.
 */
public class Coop {
    private final int id; // ID of the Coop
    private Student student; // Student associated with the Coop
    private Employer employer; // Employer associated with the Coop

    private Date startDate; // start date of the Coop
    private Date endDate; // end date of the Coop

    private WorkReport workReport; // the WorkReport (by the Student) of the Coop (can be null!)
    private StudentEvaluation studentEvaluation; //the StudentEvaluation (by the Employer) of the Coop (can be null!)
    private String reviewToken; // the token for the External Reviewer link

    private static int idCount = 0; // private ID counter

    /**
     * Construct a Coop
     * @param id associated ID
     * @param student associated Student
     * @param employer associated Employer
     * @param startDate start date of Coop
     * @param endDate end date of Coop
     */
    public Coop(int id, Student student, Employer employer, Date startDate, Date endDate) {
        this.id = id;

        this.student = student;
        this.student.addCoop(this);

        this.employer = employer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewToken = "12345678";
    }

    /** Construct a Coop without providing an id */
    public Coop(Student student, Employer employer, Date startDate, Date endDate) {
        this(idCount++, student, employer, startDate, endDate);
    }

    /** Get the ID of the Coop */
    public int getId() {
        return id;
    }
    
    /** Get the Student associated with the Coop */
    public Student getStudent() {
        return student;
    }

    /** Get the Employer associated with the Coop */
    public Employer getEmployer() {
        return employer;
    }

    /** Get the WorkReport (can be null!) */
    public WorkReport getWorkReport() {
        return workReport;
    }

    /** Get the StudentEvaluation (can be null!) */
    public StudentEvaluation getStudentEvaluation() {
        return studentEvaluation;
    }

    /** Get the start date of the Coop */
    public Date getStartDate() {
        return startDate;
    }

    /** Get the end date of the Coop */
    public Date getEndDate() {
        return endDate;
    }

    /** Add a WorkReport to the Coop */
    public void setWorkReport(WorkReport workReport) {
        this.workReport = workReport;
    }

    /** Add a StudentEvaluation to the Coop */
    public void setStudentEvaluation(StudentEvaluation studentEvaluation) {
        this.studentEvaluation = studentEvaluation;
    }

    /** Get the review token (for the external reviewer link) */
    public String getReviewToken() {
        return reviewToken;
    }

    @Override
    public String toString() {
        return "Coop at " + employer;
    }


    public boolean equals(Coop coop){
        if( this.getId() == coop.getId() &&
            this.getEmployer().equals(coop.getEmployer()) &&
            this.getStudent().equals(coop.getStudent()) &&
            //this.getStartDate().equals(coop.getStartDate()) &&
            //this.getEndDate().equals(coop.getEndDate()) &&
            this.getReviewToken().equals(coop.getReviewToken()) &&
            workReportNull(coop) &&
            studentEvaluationNull(coop)
        ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean workReportNull(Coop coop){
        boolean result;
        try{
            result = this.getWorkReport().equals(coop.getWorkReport());
        }catch (NullPointerException nptr){
            return true;
        }
        return result;
    }

    private boolean studentEvaluationNull(Coop coop){
        boolean result;
        try{
            result = this.getStudentEvaluation().equals(coop.getStudentEvaluation());
        }catch (NullPointerException nptr){
            return true;
        }
        return result;
    }

    /** A WorkReport contains a single String representing its content */
    public class WorkReport {

        /** The content of the WorkReport */
        private String content;

        public WorkReport(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return content;
        }

        public boolean equals(WorkReport workReport){
            if(this.getContent().equals(workReport.getContent())){
                return true;
            } else {
                return false;
            }
        }
    }

    /** A StudentEvaluation contains a single String representing its content */
    public class StudentEvaluation {

        /** The content of the StudentEvaluation */
        private String content;

        /** Construct a StudentEvaluation */
        public StudentEvaluation(String content) {
            this.content = content;
        }

        /** Get the content of the StudentEvaluation */
        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return content;
        }

        public boolean equals(StudentEvaluation studentEvaluation){
            if(this.getContent().equals(studentEvaluation.getContent())){
                return true;
            } else {
                return false;
            }
        }
    }
}
