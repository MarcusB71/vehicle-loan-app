package ca.senecapolytechnic.loanapplication.models;

import ca.senecapolytechnic.loanapplication.models.Loan;
import ca.senecapolytechnic.loanapplication.models.LoanAmortization;
import ca.senecapolytechnic.loanapplication.models.LoanCalculation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FixedRateLoan implements LoanCalculation {
    @Override
    public Double calculateMonthlyPayment(Loan loan) {
        double principal = loan.getAmount() - loan.getDownPayment();
        double annualRate = loan.getInterestRate() / 100;
        int numPayments = loan.getDuration();
        // Determine total number of payments based on frequency
        switch (loan.getFrequency()) {
            case "Weekly":
                numPayments = (numPayments / 12) * 52; // Convert months to weeks
                break;
            case "Bi-Weekly":
                numPayments = (numPayments / 12) * 26; // Convert months to bi-weeks
                break;
            case "Monthly":
                // No change needed, as duration is already in months
                break;
        }

        double ratePerPeriod = annualRate / 12; // Monthly interest rate
        return (principal * ratePerPeriod) / (1 - Math.pow(1 + ratePerPeriod, -numPayments));
    }

    @Override
    public List<LoanAmortization> generateAmortizationSchedule(Loan loan) {
        List<LoanAmortization> schedule = new ArrayList<>();
        double balance = loan.getAmount() - loan.getDownPayment();
        double monthlyPayment = calculateMonthlyPayment(loan);
        double monthlyRate = (loan.getInterestRate() / 100) / 12;

        LocalDate startDate = LocalDate.now();

        for (int i = 1; balance > 0 ; i++) {
            double interestPayment = balance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            balance -= principalPayment;

            LoanAmortization entry = new LoanAmortization(
                    i,
                    Date.valueOf(startDate.plusMonths(i)),
                    monthlyPayment,
                    interestPayment,
                    principalPayment,
                    Math.max(balance, 0)
            );
            schedule.add(entry);
        }
        return schedule;
    }
}
