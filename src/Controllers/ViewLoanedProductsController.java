package Controllers;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Classes.Entities.Customer;
import sample.Classes.Loan;
import sample.Classes.TableClasses.LoanedProducts;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewLoanedProductsController implements Initializable {

    @FXML private TableView<LoanedProducts> LoanedProductsTable;

    @FXML private TableColumn<LoanedProducts, String> col_id;

    @FXML private TableColumn<LoanedProducts, Double> col_price;

    @FXML private TableColumn<LoanedProducts, Integer> col_qty;

    @FXML private TableColumn<LoanedProducts, String> col_payment_mode;

    @FXML private TableColumn<LoanedProducts, Date> col_time_added;

    @FXML private TableColumn<LoanedProducts, String> col_due_date;

    @FXML private TableColumn<LoanedProducts, String> col_term;

    @FXML private Label lbCaption;

    //classes
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitColumns();
        GetLoanedProducts();
        for(Customer c: LoanUtils.ObCustomer){
            if(c.getCustomer_id() == LoanUtils.getCustomer_PK()){
                lbCaption.setText("Loaned Products of " + c.toString());
                return;
            }
        }
    }

    private void InitColumns(){
        ReportsController.LoanedProductsFunc(col_id, col_price, col_qty, col_payment_mode, col_time_added, col_due_date, col_term);
    }

    private void GetLoanedProducts(){
        loan.GetProductsLoaned("UNPAID");
        LoanedProductsTable.setItems(LoanUtils.ObLoanedProducts);
    }

    @FXML
    void CloseWindow(ActionEvent event) {
        //hide this window
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
