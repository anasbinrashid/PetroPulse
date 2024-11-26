package customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MakePaymentController {
    public Stage stage;
    public Scene scene;
    public Parent root;

    Customer c = new Customer();

    @FXML
    private Label userNameLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private ToggleGroup Payment;

    @FXML
    private TextField amount;

    public void setval(Customer c) {
        this.c = c;
        userNameLabel.setText(this.c.name);
        int val = (int) this.c.duepayment;
        amountLabel.setText("Rs. " + val + ".0");
    }

    @FXML
    void enterPayment(ActionEvent event) throws IOException, SQLException {
        String enteredAmount = amount.getText().trim();
        RadioButton selectedPaymentType = (RadioButton) Payment.getSelectedToggle();

        if (enteredAmount.isEmpty() || selectedPaymentType == null) {
            showAlert("Error", "Please enter an amount and select a payment type!");
            return;
        }

    	String paymentType = selectedPaymentType.getText();

        this.c.makePayment(enteredAmount, paymentType);
        
        reloadScene(event);

    }

    private void reloadScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MakePayment.fxml"));
        root = loader.load();
        MakePaymentController hw = loader.getController();
        hw.setval(this.c);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeCustomer.fxml"));
        root = loader.load();
        HomeCustomerController hw = loader.getController();
        hw.setval(this.c);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
