package model;

import java.util.List;

public class Employer {
    private final int id;
    private String name;
    private String address;
    private String website;
    private List<Review> reviews;

    public Employer(int id, String name, String address, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public void addReview(Review review) {

    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return name;
    }

    public class Review {
        private String author;
        private String review;

        public Review(String author, String review) {
            this.author = author;
            this.review = review;
        }

        public String getAuthor() {
            return author;
        }

        public String getReview() {
            return review;
        }
    }
}
