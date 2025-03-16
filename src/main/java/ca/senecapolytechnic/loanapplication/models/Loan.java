package ca.senecapolytechnic.loanapplication.models;

public class Loan {
    private Double amount;
    private Double downPayment;
    private Double interestRate;
    private Integer duration;
    private String frequency;
    private Customer customer;
    private Vehicle vehicle;

    public Loan(){}

    public Loan(Double amount, Double downPayment, Double interestRate, Integer duration, String frequency, Customer customer, Vehicle vehicle) {
        this.amount = amount;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.duration = duration;
        this.frequency = frequency;
        this.customer = customer;
        this.vehicle = vehicle;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getFrequency() {
        return frequency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
