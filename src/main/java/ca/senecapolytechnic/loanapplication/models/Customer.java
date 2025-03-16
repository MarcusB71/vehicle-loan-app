package ca.senecapolytechnic.loanapplication.models;

public class Customer {
    private String name;
    private String phone;
    private String city;
    private String province;

    public Customer(String name, String phone, String city, String province) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
