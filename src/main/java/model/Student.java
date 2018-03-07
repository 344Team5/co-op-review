package model;

public class Student {
    private String name;
    private String username;
    private String uniID;

    public Student(String name, String username, String uniID) {
        this.name = name;
        this.username = username;
        this.uniID = uniID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUniID() {
        return uniID;
    }
}
