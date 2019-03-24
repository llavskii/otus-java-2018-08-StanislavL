package example.javax_json;

import java.util.Arrays;

public class Employee {

    private int id;
    private String name;
    private boolean permanent;
    private Address address;
    private long[] phoneNumbers;
    private String role;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isPermanent() {
        return permanent;
    }
    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public long[] getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(long[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){

        String sb = "***** Employee Details *****\n" +
                "ID=" + getId() + "\n" +
                "Name=" + getName() + "\n" +
                "Permanent=" + isPermanent() + "\n" +
                "Role=" + getRole() + "\n" +
                "Phone Numbers=" + Arrays.toString(getPhoneNumbers()) + "\n" +
                "Address=" + getAddress() +
                "\n*****************************";
        return sb;
    }
}
