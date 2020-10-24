package Controllers;

import javafx.scene.Node;
import sample.Classes.Tools.MessageBox;
import sample.Classes.PayOut;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Classes.Entities.Customer;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;
import sample.WindowState.Open;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TermConditionController extends NewLoanController implements Initializable {

    @FXML private TextField tbCustomerName;

    @FXML private TextField tbModeOfPayment;

    @FXML private TextField tbTerm;

    @FXML private DatePicker DueDate;

    //classes
    private final Loan loan = new Loan();
    private final double Total = totalAmount;
    private final PayOut payOut = new PayOut();
    private String term;
    private String due;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Term " + Total);
        //display the name of the customer
        for(Customer customer: LoanUtils.ObCustomer){
            if(customer.getCustomer_id() == LoanUtils.getCustomer_PK()){
                tbCustomerName.setText(customer.toString());
                return;
            }
        }
    }

    @FXML
    void btnSave(MouseEvent event) {

        if(!CheckEmptyFields()) {
            //stat means if loan is successfully added
            boolean stat = loan.AddLoan(Total ,tbModeOfPayment.getText().toUpperCase(), term, due/*final string date*/);

            if (stat) {
                LoanUtils.ObTableReceipt.clear(); //clear the table receipt

                /*refresh ob customer list
                * from the Customer controller*/
                loan.GetCustomers();

                /*after refreshing check remaining balance*/
                payOut.CheckStatus();

                //close window
                ((Node)(event.getSource())).getScene().getWindow().hide();
                Open.Customers();
            }
        }
    }

    private boolean CheckEmptyFields(){
        LinkedList<TextField> textFields = new LinkedList<>();
        textFields.add(tbCustomerName);
        textFields.add(tbModeOfPayment);
        //textFields.add(tbTerm);

        if(tbTerm.getText().isEmpty())
            term = "none";
        else
            term = tbTerm.getText();

        if(DueDate.getValue() == null) {
            due = "none";
        }else
            due = String.valueOf(DueDate.getValue());

        for (TextField t : textFields) {
            if (t.getText().isEmpty()) {
                MessageBox.ShowWarning("Supply all fields");
                return true;
            }
        }

        return false;
    }
}
