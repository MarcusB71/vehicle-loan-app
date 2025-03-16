package ca.senecapolytechnic.loanapplication.models;

import java.util.List;

public interface LoanCalculation {
    Double calculateMonthlyPayment(Loan loan);
    List<LoanAmortization> generateAmortizationSchedule(Loan loan);
}

