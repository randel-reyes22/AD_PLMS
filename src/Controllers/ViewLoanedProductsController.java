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
import sample.Classes.Loan;
import sample.Classes.TableClasses.LoanedProducts;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewLoanedProductsController implements Initializable {

    @FXML private TableView<LoanedProducts> LoanedProductsTable;

    @FXML private TableColumn<LoanedProducts, String> col_id;

    @FXML private TableColumn<LoanedProducts, Double> col_price;

    @FXML private TableColumn<LoanedProducts, Integer> col_qty;

    @FXML private TableColumn<LoanedProducts, String> col_payment_mode;

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
        col_id.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("prod_price"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("prod_qty"));
        col_payment_mode.setCellValueFactory(new PropertyValueFactory<>("prod_payment_mode"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due"));
        col_term.setCellValueFactory(new PropertyValueFactory<>("term"));
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
