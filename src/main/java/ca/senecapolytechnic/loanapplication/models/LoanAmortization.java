package ca.senecapolytechnic.loanapplication.models;

import java.util.Date;

public class LoanAmortization {
    private Integer paymentNo;
    private Date paymentDate;
    private Double paymentAmount;
    private Double interestPayment;
    private Double principalAmount;
    private Double remainingBalance;

    public LoanAmortization(Integer paymentNo, Date paymentDate, Double paymentAmount, Double interestPayment, Double principalAmount, Double remainingBalance) {
        this.paymentNo = paymentNo;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.interestPayment = interestPayment;
        this.principalAmount = principalAmount;
        this.remainingBalance = remainingBalance;
    }

    public Integer getPaymentNo() {
        return paymentNo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public Double getInterestPayment() {
        return interestPayment;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public Double getRemainingBalance() {
        return remainingBalance;
    }
}
