package ca.senecapolytechnic.loanapplication.models;

public class Vehicle {
    private String type;
    private String age;
    private Double price;

    public Vehicle(String type, String age, Double price) {
        this.type = type;
        this.age = age;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
