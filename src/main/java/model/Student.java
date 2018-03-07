package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final int id;
    private String name;
    private String username;
    private String uniID;
    private List<Coop> coops;

    public Student(int id, String name, String username, String uniID) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.uniID = uniID;
        coops = new ArrayList<>();
    }

    public int getId() {
        return id;
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

    public List<Coop> getCoops() {
        return coops;
    }

    public void addCoop(Coop coop) {
        coops.add(coop);
    }
}
