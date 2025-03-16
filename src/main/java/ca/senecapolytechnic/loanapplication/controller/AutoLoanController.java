package ca.senecapolytechnic.loanapplication.controller;

import ca.senecapolytechnic.loanapplication.models.*;
import ca.senecapolytechnic.loanapplication.utility.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoLoanController {
    // Customer Information Fields
    @FXML private TextField nameField, phoneField, cityField;
    @FXML private ChoiceBox<String> provinceChoiceBox;

    // Vehicle Information Fields
    @FXML private ToggleGroup vehicleTypeToggle;
    @FXML private RadioButton carOption, truckOption, vanOption;
    @FXML private ToggleGroup vehicleAgeToggle;
    @FXML private RadioButton newOption, usedOption;

    // Loan Information Fields
    @FXML private TextField loanAmountField, downPaymentField;
    @FXML private Slider loanDurationSlider;
    @FXML private ToggleGroup interestRateToggle;
    @FXML private RadioButton rbInterest099, rbInterest199, rbInterest299, rbInterestOther;
    @FXML private TextField customInterestRateField;
    @FXML private ToggleGroup paymentFrequencyToggle;
    @FXML private RadioButton weeklyOption, biWeeklyOption, monthlyOption;
    @FXML private Label fixedRateLoanPayment;
    @FXML private Label loanDurationTracker;

    private List<Loan> savedLoans = new ArrayList<>();

    @FXML
    public void initialize() {
        provinceChoiceBox.getItems().addAll("Ontario", "Quebec", "British Columbia", "Alberta", "Manitoba");
        loanDurationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            loanDurationTracker.setText(String.format("%d mon", newValue.intValue()));
        });
        // Disable custom interest rate field initially
        customInterestRateField.setDisable(true);

        // Add listener to enable/disable custom input field
        interestRateToggle.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == rbInterestOther) {
                customInterestRateField.setDisable(false);
            } else {
                customInterestRateField.setDisable(true);
                customInterestRateField.clear();
            }
        });
    }

    // Get selected interest rate
    private double getSelectedInterestRate() {
        if (rbInterest099.isSelected()) return 0.99;
        if (rbInterest199.isSelected()) return 1.99;
        if (rbInterest299.isSelected()) return 2.99;
        if (rbInterestOther.isSelected()) {
            try {
                return Double.parseDouble(customInterestRateField.getText());
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid custom interest rate.");
                return -1; // Invalid value
            }
        }
        return -1; // No selection
    }
    private String getSelectedPaymentFrequency() {
        RadioButton selectedRadio = (RadioButton) paymentFrequencyToggle.getSelectedToggle();
        return (selectedRadio != null) ? selectedRadio.getText() : null;
    }
    private String getSelectedVehicleType(){
        RadioButton selectedRadio = (RadioButton) vehicleTypeToggle.getSelectedToggle();
        return (selectedRadio != null) ? selectedRadio.getText() : null;
    }
    private String getSelectedVehicleAge(){
        RadioButton selectedRadio = (RadioButton) vehicleAgeToggle.getSelectedToggle();
        return (selectedRadio != null) ? selectedRadio.getText() : null;
    }
    private void setVehicleAgeRadio(String vehicleAge){
        if (Objects.equals(vehicleAge, "New")){
            newOption.setSelected(true);
        }else if (Objects.equals(vehicleAge, "Used")){
            usedOption.setSelected(true);
        }
    }
    private void setVehicleTypeRadio(String vehicleType){
        if (Objects.equals(vehicleType, "Car")){
            carOption.setSelected(true);
        } else if (Objects.equals(vehicleType, "Truck")) {
            truckOption.setSelected(true);
        } else if (Objects.equals(vehicleType, "Family Van")){
            vanOption.setSelected(true);
        }
    }
    private void setInterestRadio(Double interestRate){
        if (interestRate == 0.99){
            rbInterest099.setSelected(true);
        } else if (interestRate == 1.99) {
            rbInterest199.setSelected(true);
        } else if (interestRate == 2.99){
            rbInterest299.setSelected(true);
        } else {
            rbInterestOther.setSelected(true);
            customInterestRateField.setText(String.valueOf(interestRate));
        }
    }
    private void setPaymentFrequencyRadio(String frequency){
        if (Objects.equals(frequency, "Weekly")){
            weeklyOption.setSelected(true);
        } else if (Objects.equals(frequency, "Bi-Weekly")) {
            biWeeklyOption.setSelected(true);
        } else if (Objects.equals(frequency, "Monthly")) {
            monthlyOption.setSelected(true);
        }
    }
    // Show alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void clearFields(){
    nameField.clear();
    phoneField.clear();
    cityField.clear();
    provinceChoiceBox.setValue(null);
    carOption.setSelected(false);
    vanOption.setSelected(false);
    truckOption.setSelected(false);
    usedOption.setSelected(false);
    newOption.setSelected(false);

    loanAmountField.clear();
    downPaymentField.clear();
    rbInterest099.setSelected(false);
    rbInterest199.setSelected(false);
    rbInterest199.setSelected(false);
    rbInterest299.setSelected(false);
    rbInterestOther.setSelected(false);
    customInterestRateField.clear();
    loanDurationSlider.setValue(1);
    weeklyOption.setSelected(false);
    biWeeklyOption.setSelected(false);
    monthlyOption.setSelected(false);
    fixedRateLoanPayment.setText("0");
    }
    @FXML
    public void calculateLoan() {
        try {
            double amount = Double.parseDouble(loanAmountField.getText());
            double downPayment = Double.parseDouble(downPaymentField.getText());
            double interestRate = getSelectedInterestRate();
            int duration = (int) loanDurationSlider.getValue();
            String paymentFrequency = getSelectedPaymentFrequency();

            // Validate interest rate input
            if (interestRate == -1) {
                showAlert("Please select a valid interest rate.");
                return;
            }

            // Validate payment frequency selection
            if (paymentFrequency == null) {
                showAlert("Please select a payment frequency.");
                return;
            }

            // Create loan and fixed rate loan objects
            Loan loan = new Loan(amount, downPayment, interestRate, duration, paymentFrequency, null, null);
            FixedRateLoan fixedRateLoan = new FixedRateLoan();

            // Calculate and display monthly payment
            double monthlyPayment = fixedRateLoan.calculateMonthlyPayment(loan);
            fixedRateLoanPayment.setText(String.format("$%.2f",monthlyPayment));
            System.out.println("Monthly Payment: " + String.format("$%.2f", monthlyPayment));

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid numerical values for loan amount, down payment, and interest rate.");
        }
    }

    @FXML
    public void saveCurrentRate() {
        try {
            Loan loan = new Loan(
                    Double.parseDouble(loanAmountField.getText()),
                    Double.parseDouble(downPaymentField.getText()),
                    getSelectedInterestRate(),
                    (int)loanDurationSlider.getValue(),
                    getSelectedPaymentFrequency(),
                    new Customer(nameField.getText(), phoneField.getText(), cityField.getText(), provinceChoiceBox.getValue()),
                    new Vehicle(getSelectedVehicleType(), getSelectedVehicleAge(), Double.parseDouble(loanAmountField.getText()))
            );

            savedLoans.add(loan);
            showSuccessAlert("Loan saved successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error: Ensure all fields are filled correctly before saving.");
        }
    }
    public void openSavedRatesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/senecapolytechnic/loanapplication/saved-rates.fxml"));
            Parent root = loader.load();

            // Get the controller
            SavedRateController savedRateController = loader.getController();

            // Convert ArrayList<Loan> to ObservableList<String>
            ObservableList<String> savedRatesList = FXCollections.observableArrayList();
            for (Loan loan : savedLoans) {
                savedRatesList.add(formatLoan(loan)); // Convert Loan to a displayable string
            }

            // Pass the observable list and loans
            savedRateController.setSavedRates(savedRatesList, savedLoans, this::loadSelectedLoan);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Saved Rates");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String formatLoan(Loan loan) {
        return String.format("customer: %s, type: %s, rate: %.2f%%",
                loan.getCustomer().getName(), loan.getVehicle().getType(), loan.getInterestRate());
    }

    @FXML
    public void loadSelectedLoan(Loan loan) {
        loanAmountField.setText(String.valueOf(loan.getAmount()));
        downPaymentField.setText(String.valueOf(loan.getDownPayment()));
        loanDurationSlider.setValue(loan.getDuration());
        setInterestRadio(loan.getInterestRate());
        setPaymentFrequencyRadio(loan.getFrequency());

        nameField.setText(loan.getCustomer().getName());
        phoneField.setText(loan.getCustomer().getPhone());
        cityField.setText(loan.getCustomer().getCity());
        provinceChoiceBox.setValue(loan.getCustomer().getProvince());

        setVehicleAgeRadio(loan.getVehicle().getAge());
        setVehicleTypeRadio(loan.getVehicle().getType());

    }

    public void openAmortizationSchedule() {
        Loan loan = getLoanFromForm(); // Get loan data from form fields

        if (loan == null) {
            showAlert("Incomplete Data: Please fill in all required fields before viewing the amortization schedule.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/senecapolytechnic/loanapplication/amortization.fxml"));
            Parent root = loader.load();

            // Get the controller and pass amortization data
            AmortizationController amortizationController = loader.getController();
            List<LoanAmortization> amortizationSchedule = generateAmortizationSchedule(loan);
            amortizationController.setAmortizationData(amortizationSchedule);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Amortization Schedule");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Loan getLoanFromForm() {
        try {
            // Extract numeric values safely
            double amount = Double.parseDouble(loanAmountField.getText());
            double downPayment = Double.parseDouble(downPaymentField.getText());
            double interestRate = getSelectedInterestRate();
            int duration = (int) loanDurationSlider.getValue();
            String frequency = getSelectedPaymentFrequency();

            // Create Customer object
            String customerName = nameField.getText();
            String customerPhone = phoneField.getText();
            String customerCity = cityField.getText();
            String customerProvince = provinceChoiceBox.getValue();
            Customer customer = new Customer(customerName, customerPhone, customerCity, customerProvince);

            // Create Vehicle object
            String vehicleType = getSelectedVehicleType();
            String vehicleAge = getSelectedVehicleAge();

            Vehicle vehicle = new Vehicle(vehicleType, vehicleAge, amount);

            // Create and return Loan object
            return new Loan(amount, downPayment, interestRate, duration, frequency, customer, vehicle);
        } catch (NumberFormatException e) {
            return null; // Return null if any parsing fails (handles empty/missing fields)
        }
    }
    // Helper function to generate the amortization schedule
    private List<LoanAmortization> generateAmortizationSchedule(Loan loan) {
        LoanCalculation calculator = new FixedRateLoan();
        return calculator.generateAmortizationSchedule(loan);
    }
}
