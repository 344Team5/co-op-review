package model;

public class Employer {
    private String name;
    private String address;
    private String website;

    public Employer(String name, String address, String website) {
        this.name = name;
        this.address = address;
        this.website = website;
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
}
