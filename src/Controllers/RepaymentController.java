package Controllers;

import sample.WindowState.Close;
import sample.WindowState.Open;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Classes.Entities.Customer;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RepaymentController extends CustomersController implements Initializable {

    @FXML private TableView<Customer> RepaymentTable;

    @FXML private TableColumn<Customer, Integer> col_id;

    @FXML private TableColumn<Customer, String> col_firstname;

    @FXML private TableColumn<Customer, String> col_lastname;

    @FXML private TableColumn<Customer, Double> col_balance;

    @FXML private TableColumn<Customer, Void> col_payment;

    @FXML private TableColumn<Customer, Void> col_payment_history;

    @FXML private TableColumn<Customer, Void> col_loaned_products;

    @FXML private TextField tbSearchCustomerPayment;

    //class loan
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoanUtils.ChangeView = "RepaymentView";
        //init the columns
        InitPaymentColumns();
        //Load the customer data to the table
        GetCustomerPaymentData();
        //Init search functionality
        searchCustomer();
    }

    private void InitPaymentColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    protected void GetCustomerPaymentData(){
        //load the customers in the observable list
        loan.GetCustomers();
        RepaymentTable.setItems(LoanUtils.ObCustomer);
        MakePayment(); //make payment button
        PaymentHistory(); //payment history button
        ViewLoanedProducts(); //view loaned products button
    }

    private void MakePayment(){
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = (TableColumn<Customer, Void> param) -> {
            TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                private final Button btnAction = new Button("Make Payment");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Customer customer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setCustomer_PK(customer.getCustomer_id());

                        /*open window here to make a payment
                        *   Open.MakePayment*/
                        Open.MakePayment();
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnAction);
                    }
                }
            };
            return cell;
        };

        col_payment.setCellFactory(cellFactory);
    }

    private void PaymentHistory(){
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = (TableColumn<Customer, Void> param) -> {
            TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                private final Button btnAction = new Button("Payment History");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Customer customer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setCustomer_PK(customer.getCustomer_id());

                        /*open window here to make a payment
                         *   Open.PaymentHistory*/
                        Open.PaymentHistory();
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnAction);
                    }
                }
            };
            return cell;
        };

        col_payment_history.setCellFactory(cellFactory);
    }

    private void ViewLoanedProducts(){
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = (TableColumn<Customer, Void> param) -> {
            TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                private final Button btnAction = new Button("Loaned Products");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Customer customer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setCustomer_PK(customer.getCustomer_id());

                        /*open window here to make a payment
                         *   Open.Loaned Products*/
                        Open.LoanedProducts();
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnAction);
                    }
                }
            };
            return cell;
        };

        col_loaned_products.setCellFactory(cellFactory);
    }

    //for searching
    private void searchCustomer() {

        tbSearchCustomerPayment.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListCustomer.setPredicate(customer -> {
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return String.valueOf( customer.getBalance() ).contains( lowerCaseFilter );
            });
        });

        sortedListCustomer.comparatorProperty().bind(RepaymentTable.comparatorProperty());
        RepaymentTable.setItems(sortedListCustomer);
    }

    @FXML
    void GoBack(MouseEvent event) {
        Open.Dashboard();
        Close.ThisWindow(event);
    }
}
