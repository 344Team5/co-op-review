package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Employer class represents a single Employer/External Reviewer.
 */
public class Employer {
    private final int id; // ID of the Employer
    private String name; // name of the Employer
    private String address; // address of the Employer
    private String website; // website of the Employer
    private List<Review> reviews; // reviews associated with the Employer

    private static int idCount = 0; // private ID counter

    /**
     * Construct an Employer
     * @param id associated ID
     * @param name name of Employer
     * @param address address of Employer
     * @param website website of Employer
     */
    public Employer(int id, String name, String address, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        reviews = new ArrayList<>();
    }

    /** Create an Employer without providing an id */
    public Employer(String name, String address, String website) {
        this(idCount++, name, address, website);
    }

    /** Get the ID of the Employer */
    public int getId() {
        return id;
    }

    /** Get the name of the Employer */
    public String getName() {
        return name;
    }

    /** Get the address of the Employer */
    public String getAddress() {
        return address;
    }

    /** Get the website url of the Employer */
    public String getWebsite() {
        return website;
    }

    /** Add a Review of the Employer to its list of Reviews */
    public void addReview(Review review) {
        reviews.add(review);
    }

    /** Get the Employer's list of Reviews */
    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return name;
    }

    /** A Review contains a Student author and a String representing the Review content */
    public class Review {
        /** The Student who wrote the review.  This is not shown publicly */
        private Student author;

        /** The content of the Review */
        private String review;

        /** Construct a Review */
        public Review(Student author, String review) {
            this.author = author;
            this.review = review;
        }

        /** Get the Student who is the author of the Review */
        public Student getAuthor() {
            return author;
        }

        /** Get the content of the Review */
        public String getReview() {
            return review;
        }

        @Override
        public String toString() {
            return review;
        }
    }
}
