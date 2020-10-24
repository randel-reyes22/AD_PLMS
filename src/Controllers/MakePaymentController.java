package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.Classes.Entities.Customer;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;
import sample.Classes.PayOut;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class MakePaymentController implements Initializable {

    @FXML private TextField tbCustomerName;

    @FXML private TextField tbAmountPay;

    @FXML private DatePicker DateGiven;

    @FXML private TextField tbBalance;


    //classes
    private final PayOut payOut = new PayOut();
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //display the name of the customer
        for(Customer c: LoanUtils.ObCustomer){
            if(c.getCustomer_id() == LoanUtils.getCustomer_PK()){
                tbCustomerName.setText(c.toString()); //name
                tbBalance.setText(String.valueOf(c.getBalance())); //remaining balance
                return;
            }
        }
    }

    private double IsAmount(){
        try{
            return Double.parseDouble(tbAmountPay.getText());
        }
        catch (InputMismatchException | NumberFormatException ex){
            return -1;
        }
    }

    private boolean CheckAmount(double amount){
        for(Customer c: LoanUtils.ObCustomer){
            if(c.getCustomer_id() == LoanUtils.getCustomer_PK()){
                if(amount > c.getBalance()) //if amount entered is
                    return true;            //greater than the remaining balance -> cancel
            }
        }
        return false; //else if less than or equal
    }

    @FXML
    void MakePay(ActionEvent event) {
        double amount = IsAmount();

        if(DateGiven.getValue() != null) {
            if (!CheckAmount(amount)) { //if amount entered is <= to the remaining balance
                if (amount > 0) {
                    //invoke the make payment method
                    payOut.Makepayment(String.valueOf(DateGiven.getValue()), amount);

                    //invoke ob to refresh the customer data
                    LoanUtils.ChangeView = "RepaymentView";
                    loan.GetCustomers();

                    //check status
                    payOut.CheckStatus();

                    //close this window
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            } else {
                MessageBox.ShowWarning("Amount entered is greater \n than the remaining balance.");
            }
        }else {
            MessageBox.ShowInformation("Please input a valid amount or date.");
        }
    }
}
