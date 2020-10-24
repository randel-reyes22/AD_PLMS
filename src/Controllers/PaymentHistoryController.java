package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Classes.Entities.Customer;
import sample.Classes.TableClasses.HistoryPayment;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentHistoryController implements Initializable {

    @FXML private TableView<HistoryPayment> PaymentHistoryTable;

    @FXML private TableColumn<HistoryPayment, Integer> col_loan_id;

    @FXML private TableColumn<HistoryPayment, Double> col_amount;

    @FXML private TableColumn<HistoryPayment, String> col_given_date;

    @FXML private Label lbCaption;
    //classes
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitColumns();
        GetHistory();
        for(Customer c: LoanUtils.ObCustomer){
            if(c.getCustomer_id() == LoanUtils.getCustomer_PK()){
                lbCaption.setText("Payment History of " + c.toString());
                return;
            }
        }
    }

    private void InitColumns(){
        col_loan_id.setCellValueFactory(new PropertyValueFactory<>("load_id"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("collection_amount"));
        col_given_date.setCellValueFactory(new PropertyValueFactory<>("givenDate"));
    }

    private void GetHistory(){
        //invoke loan get history method
        loan.GetPaymentHistory("UNPAID");
        PaymentHistoryTable.setItems(Loan.ObHistoryPayments);
    }

    @FXML
    void CloseHistory(ActionEvent event) {
        //close this window
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
