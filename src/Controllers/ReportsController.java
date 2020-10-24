package Controllers;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Classes.ConnectDB.Connect;
import sample.Classes.Entities.Customer;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Loan;
import sample.Classes.TableClasses.HistoryPayment;
import sample.Classes.TableClasses.LoanedProducts;
import sample.Classes.Utility.LoanUtils;
import sample.Classes.Utility.WeekDates;
import sample.WindowState.Close;
import sample.WindowState.Open;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    //Revenue summary tab
    @FXML private AreaChart<String, Double> RevenueSummary;

    @FXML private DatePicker StartDate;

    @FXML private DatePicker EndDate;

    //history tab
    @FXML private TableView<Customer> Table_Customer;

    @FXML private TableColumn<Customer, Integer> col_id;

    @FXML private TableColumn<Customer, String> col_fname;

    @FXML private TableColumn<Customer, String> col_lname;

    @FXML private TableColumn<Customer, Void> col_select;

    @FXML private TextField search_customer;

    private FilteredList<Customer> filteredListCustomer = new FilteredList<>(LoanUtils.ObCustomer, p -> true);
    private SortedList<Customer> sortedListCustomer = new SortedList<>(filteredListCustomer);
    private final Loan loan = new Loan();

    //history part
    @FXML private Label lbTotal;

    @FXML private Label customer_name_selected;

    //payment history
    @FXML private TableView<HistoryPayment> table_payment_history;

    @FXML private TableColumn<HistoryPayment, Integer> col_loan_id;

    @FXML private TableColumn<HistoryPayment, Double> col_amount;

    @FXML private TableColumn<HistoryPayment, String> col_given_date;

    @FXML private TableView<LoanedProducts> LoanedProductsTable;

    @FXML private TableColumn<LoanedProducts, String> col_prod_name;

    @FXML private TableColumn<LoanedProducts, Double> col_price;

    @FXML private TableColumn<LoanedProducts, Integer> col_qty;

    @FXML private TableColumn<LoanedProducts, String> col_payment_mode;

    @FXML private TableColumn<LoanedProducts, String> col_due_date;

    @FXML private TableColumn<LoanedProducts, String> col_term;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //history tab
        InitColumnsHistory();
        GetData();
    }

    //revenue tab
    @FXML
    void View(ActionEvent event) throws ParseException {
        if(EndDate.getValue() != null && StartDate.getValue() != null)
            RevenueSummary();
    }

    private void RevenueSummary() throws ParseException {
        String revenueWeek = "SELECT GivenDate ,sum(CollectionAmount) FROM main.Collections " +
                "WHERE GivenDate  = ?";

        //invoke method to gather all dates
        WeekDates.GetAllDays(String.valueOf(StartDate.getValue()),
                String.valueOf(EndDate.getValue()));

        //coordinates
        XYChart.Series seriesRevenue= new XYChart.Series();
        seriesRevenue.setName("Revenue");

        for(String dates: WeekDates.dates){
            Connection conn = Connect.Link();
            try {
                PreparedStatement ps = conn.prepareStatement(revenueWeek);
                ps.setString(1, dates);
                ResultSet result = ps.executeQuery();
                result.next();

                seriesRevenue.getData().add(new XYChart.Data(dates,result.getDouble(2)));

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                //if an exception occurs
                MessageBox.ShowError("An error occurred");

            } finally {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        //after the loop add the data to the chart
        RevenueSummary.getData().addAll(seriesRevenue);
    }

    //history tab
    private void InitColumnsHistory(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        col_loan_id.setCellValueFactory(new PropertyValueFactory<>("load_id"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("collection_amount"));
        col_given_date.setCellValueFactory(new PropertyValueFactory<>("givenDate"));

        col_prod_name.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("prod_price"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("prod_qty"));
        col_payment_mode.setCellValueFactory(new PropertyValueFactory<>("prod_payment_mode"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due"));
        col_term.setCellValueFactory(new PropertyValueFactory<>("term"));
    }

    private void GetData(){
        loan.GetCustomers();
        Table_Customer.setItems(LoanUtils.ObCustomer);
        ViewHistoryButton(); //button
        searchCustomer(); //initialize search feature
    }

    private void searchCustomer() {
        search_customer.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedListCustomer.comparatorProperty().bind(Table_Customer.comparatorProperty());
        Table_Customer.setItems(sortedListCustomer);
    }

    private void ViewHistoryButton(){
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = (TableColumn<Customer, Void> param) -> {
            TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                private final Button btnAction = new Button("View");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Customer customer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setCustomer_PK(customer.getCustomer_id());

                        customer_name_selected.setText(customer.toString());//display the name
                        //payment history
                        loan.GetPaymentHistory("PAID"); //get the history
                        table_payment_history.setItems(Loan.ObHistoryPayments); //display the payment history
                        lbTotal.setText("Total : " + Math.round(TotalPaymentHistory()));
                        //product loaned history
                        loan.GetProductsLoaned("PAID");
                        LoanedProductsTable.setItems(LoanUtils.ObLoanedProducts);
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

        col_select.setCellFactory(cellFactory);
    }

    private double TotalPaymentHistory(){
        double total = 0;
        for(HistoryPayment past: LoanUtils.ObHistoryPayments){
              total += past.getCollection_amount();
        }
        return total;
    }

    @FXML
    void print_payment_history(MouseEvent event) {

    }

    @FXML
    void print_product_history(MouseEvent event) {

    }


    @FXML
    void GoBack(MouseEvent event) {
        Open.Dashboard();
        Close.ThisWindow(event);
    }

}
