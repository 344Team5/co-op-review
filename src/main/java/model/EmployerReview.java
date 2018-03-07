package model;

public class EmployerReview {
    private Employer employer;
    private String review;

    public EmployerReview(Employer employer, String review) {
        this.employer = employer;
        this.review = review;
    }

    public Employer getEmployer() {
        return employer;
    }

    public String getReview() {
        return review;
    }
}
