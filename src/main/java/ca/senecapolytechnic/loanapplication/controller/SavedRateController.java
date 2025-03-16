package ca.senecapolytechnic.loanapplication.controller;

import ca.senecapolytechnic.loanapplication.models.Loan;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class SavedRateController {
    @FXML
    private ListView<String> savedRatesListView;

    private List<Loan> loans;
    private Consumer<Loan> onLoanSelected;

    public void setSavedRates(ObservableList<String> savedRates, List<Loan> loans, Consumer<Loan> onLoanSelected) {
        this.loans = loans;
        this.onLoanSelected = onLoanSelected;
        savedRatesListView.setItems(savedRates);
        savedRatesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleDoubleClick();
            }
        });
    }
    @FXML
    public void handleDoubleClick() {
        String selectedRate = savedRatesListView.getSelectionModel().getSelectedItem();
        if (selectedRate != null) {
            int index = savedRatesListView.getSelectionModel().getSelectedIndex();
            Loan selectedLoan = loans.get(index);
            onLoanSelected.accept(selectedLoan);
        }
    }
    @FXML
    public void handleClose() {
        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) savedRatesListView.getScene().getWindow();
        stage.close();
    }

}
