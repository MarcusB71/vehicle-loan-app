package ca.senecapolytechnic.loanapplication.controller;

import ca.senecapolytechnic.loanapplication.models.LoanAmortization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class AmortizationController {
    @FXML
    private TableView<LoanAmortization> amortizationTable;
    @FXML
    private TableColumn<LoanAmortization, Integer> paymentNoColumn;
    @FXML
    private TableColumn<LoanAmortization, Integer> paymentDateColumn;
    @FXML
    private TableColumn<LoanAmortization, Double> paymentAmountColumn, interestPaymentColumn, principalAmountColumn, remainingBalanceColumn;

    public void setAmortizationData(List<LoanAmortization> data) {
        ObservableList<LoanAmortization> amortizationList = FXCollections.observableArrayList(data);
        amortizationTable.setItems(amortizationList);
    }
    @FXML
    public void initialize() {
        paymentNoColumn.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        interestPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        principalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("principalAmount"));
        remainingBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));
    }
    @FXML
    public void handleClose() {
        Stage stage = (Stage) amortizationTable.getScene().getWindow();
        stage.close();
    }
}
