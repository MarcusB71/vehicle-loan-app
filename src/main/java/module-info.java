module ca.senecapolytechnic.loanapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ca.senecapolytechnic.loanapplication.models to javafx.base;
    opens ca.senecapolytechnic.loanapplication.controller to javafx.fxml;
    exports ca.senecapolytechnic.loanapplication;
    exports ca.senecapolytechnic.loanapplication.controller;
}