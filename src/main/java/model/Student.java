package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Student class represents a single Student.
 */
public class Student {
    private final int id; // ID of the Student (not uniID!)
    private String name; // The name of the Student
    private String username; // The username of the Student
    private String uniID; // The university ID of the Student
    private List<Integer> coops; // The list of the Student's Coops

    private static int idCount = 0; // private ID counter

    /**
     * The Student class represents a signle Student
     *
     * @param id       associated ID
     * @param name     Student's name
     * @param username Student's username
     * @param uniID    Student's university ID
     */
    public Student(int id, String name, String username, String uniID) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.uniID = uniID;
        coops = new ArrayList<Integer>();
    }

    /**
     * Construct a Student without providing an id
     */
    public Student(String name, String username, String uniID) {
        this(idCount++, name, username, uniID);
    }

    /**
     * Get the ID of the Student
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the Student
     */
    public String getName() {
        return name;
    }

    /**
     * Get the username of the Student
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the university ID of the Student
     */
    public String getUniID() {
        return uniID;
    }

    /**
     * Get the Student's list of Coops represented by IDs
     */
    public List<Integer> getCoops() {
        return coops;
    }

    /**
     * Associate a Coop with the Student
     */
    public void addCoop(Coop coop) {
        coops.add(coop.getId());
    }

    public boolean equals(Student student) {

        if (this.getId() == student.getId() &&
                this.getName().equals(student.getName()) &&
                this.getUsername().equals(student.getUsername()) &&
                this.getUniID().equals((student.getUniID()))
                ) {
            for (int i = 0; i < this.getCoops().size(); i++) {
                if (!this.getCoops().get(i).equals(student.getCoops().get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }
}
