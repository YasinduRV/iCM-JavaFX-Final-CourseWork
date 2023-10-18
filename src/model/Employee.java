package model;

public class Employee {
    String id;
    String name;
    String nic;
    String address;
    String dob;
    String contact;
    String acc_no;
    String acc_branch;

    public Employee() {
    }

    public Employee(String id, String name, String nic, String address, String dob, String contact, String acc_no, String acc_branch) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.dob = dob;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
