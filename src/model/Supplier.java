package model;

public class Supplier {
    private String id;
    private String name;
    private String company;
    private String address;
    private String contact;
    private String acc_no;
    private String acc_branch;

    public Supplier(String id, String name, String company, String address, String contact, String acc_no, String acc_branch) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.address = address;
        this.contact = contact;
        this.acc_no = acc_no;
        this.acc_branch = acc_branch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getAcc_branch() {
        return acc_branch;
    }

    public void setAcc_branch(String acc_branch) {
        this.acc_branch = acc_branch;
    }
}
