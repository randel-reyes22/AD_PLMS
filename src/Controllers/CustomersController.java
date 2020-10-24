package Controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Classes.PayOut;
import sample.WindowState.Close;
import sample.WindowState.Open;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Classes.Entities.Customer;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML private TextField tbSearchCutomer;

    @FXML private Label lbCaption;

    @FXML private TableView<Customer> CustomerTable;

    @FXML private TableColumn<Customer, Integer> col_id;

    @FXML private TableColumn<Customer, String> col_firstname;

    @FXML private TableColumn<Customer, String> col_lastname;

    @FXML private TableColumn<Customer, String> col_mobile;

    @FXML private TableColumn<Customer, Double> col_balance;

    @FXML private TableColumn<Customer, Void> col_update;

    //for
    private final Loan loan = new Loan();
    protected FilteredList<Customer> filteredListCustomer = new FilteredList<>(LoanUtils.ObCustomer, p -> true);
    protected SortedList<Customer> sortedListCustomer = new SortedList<>(filteredListCustomer);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LoanUtils.Action_classifier.equals("Select")){
            lbCaption.setText("Select customer");
        }

        InitColumns();
        GetCustomerData();
        searchCustomer();
    }

    private void InitColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        col_mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    protected void GetCustomerData(){
        try {
            //load the customers in the observable list
            loan.GetCustomers();
            CustomerTable.setItems(LoanUtils.ObCustomer);
            ActionButton(); //button
        }
        catch (NullPointerException ignored){}
    }

    private void ActionButton(){
        String btnText = null;
        if(LoanUtils.Action_classifier.equals("Select"))
            btnText = "Select";
        else
            btnText = "Update";

        String finalBtnText = btnText;

        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = (TableColumn<Customer, Void> param) -> {
            TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                private final Button btnAction = new Button(finalBtnText);
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Customer customer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setCustomer_PK(customer.getCustomer_id());

                        switch (finalBtnText)
                        {
                            case "Update":
                                try {
                                    //open add customer window to update
                                   Open.AddCustomer();
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "Select":
                                try {
                                    //open new loan window to add loan
                                    Open.NewLoan();
                                    //hide this window
                                    ((Node)(event.getSource())).getScene().getWindow().hide();
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
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

        col_update.setCellFactory(cellFactory);
    }

    private void searchCustomer() {

        tbSearchCutomer.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListCustomer.setPredicate(customer -> {
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getMobile().contains(lowerCaseFilter)) {
                    return true;
                }else if(customer.getAddress().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else return String.valueOf(customer.getBalance()).contains(lowerCaseFilter);
            });
        });

        sortedListCustomer.comparatorProperty().bind(CustomerTable.comparatorProperty());
        CustomerTable.setItems(sortedListCustomer);
    }

    @FXML
    void OpenAddCustomer(MouseEvent event) {
        LoanUtils.Action_classifier = "Add";
        Open.AddCustomer();
    }

    @FXML
    void GoBack(MouseEvent event) {
        Open.Dashboard();
        Close.ThisWindow(event);
    }

}
