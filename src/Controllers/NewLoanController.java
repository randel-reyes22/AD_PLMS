package Controllers;

import javafx.scene.Node;
import sample.WindowState.Close;
import sample.WindowState.Open;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Classes.Entities.Customer;
import sample.Classes.Entities.Product;
import sample.Classes.Loan;
import sample.Classes.TableClasses.TableReceipt;
import sample.Classes.Utility.LoanUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewLoanController extends CustomersController implements Initializable {

    //table
    @FXML private TableView<TableReceipt> ReceiptTable;

    @FXML private TableColumn<TableReceipt, Integer> col_id;

    @FXML private TableColumn<TableReceipt, String> col_name;

    @FXML private TableColumn<TableReceipt, Double> col_price;

    @FXML private TableColumn<TableReceipt, Integer> col_qty;

    @FXML private TableColumn<TableReceipt, Double> col_total;

    @FXML private TableColumn<TableReceipt, Void> col_delete;

    //table for selection of product
    @FXML private TableView<Product> ProductChooseTable;

    @FXML private TableColumn<Product, Integer> col_select_id;

    @FXML private TableColumn<Product, String> col_select_name;

    @FXML private TableColumn<Product, Void> col_select;

    //other stuff
    @FXML private TextField tbProductname;

    @FXML private Spinner<Integer> prodQty;

    @FXML private TextField tbSearchProduct;

    @FXML private Label lbCustomerName;

    @FXML private Label lbTotalAmount;

    //Utilities
    private double product_price = 0.00; //price of the current selected product
    protected static double totalAmount = 0.00; //product the total amount gathered in the receipt table
    private final ObservableList<TableReceipt> tableReceipts = FXCollections.observableArrayList();
    private final Loan loan = new Loan();

    //Collections
    protected FilteredList<Product> filteredListProduct = new FilteredList<>(LoanUtils.ObProduct, p -> true);
    protected SortedList<Product> sortedListProduct = new SortedList<>(filteredListProduct);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //selection of product
        InitSpinner();
        InitSelectColumns();
        GetSelectionProduct();
        searchProductChoose();

        //table receipt
        InitReceiptColumns();

        //display the customer name
        for(Customer customer: LoanUtils.ObCustomer){
            if(customer.getCustomer_id() == LoanUtils.getCustomer_PK()){
                lbCustomerName.setText(customer.toString());
                return;
            }
        }
    }

    //add the chose product and qty in the table
    @FXML
    void btnAddLoanedProduct(ActionEvent event) {
        //total qty * the price of the product
        double total = prodQty.getValue() * product_price;
        //add the Table receipt to OB
        LoanUtils.ObTableReceipt.add(new TableReceipt(LoanUtils.getProduct_PK(), tbProductname.getText(),
                product_price, prodQty.getValue(), total));

        LoadDataInReceiptTable();
        DeleteButton(); //button
        //clear the text fields
        ResetTextFields();
    }

    private void LoadDataInReceiptTable(){
        ReceiptTable.setItems(LoanUtils.ObTableReceipt);
        lbTotalAmount.setText(String.valueOf(Math.round(ComputeTotalAmount())));
    }

    private double ComputeTotalAmount(){
        double total = 0.00;
        for(TableReceipt tr: LoanUtils.ObTableReceipt){
            total += tr.getTotal();
        }
        totalAmount = total;
        System.out.println("Total amount " + totalAmount);
        return total;
    }

    private void ResetTextFields(){
        tbProductname.clear();
    }

    private void InitSpinner(){
        // Value factory.
        final int initialValue = 1;
        final int ToStepBy = initialValue;

        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,initialValue, ToStepBy);

        prodQty.setValueFactory(valueFactory);
    }

    //for table receipt table
    private void InitReceiptColumns(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("prod_price"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    //selection of product
    private void InitSelectColumns(){
        col_select_id.setCellValueFactory(new PropertyValueFactory<>("prod_id"));
        col_select_name.setCellValueFactory(new PropertyValueFactory<>("prodName"));
    }

    //for selection of product
    protected void GetSelectionProduct() {
        //load the products to the observable list
        loan.GetProducts();
        //invoke the observable list of the Product
        ProductChooseTable.setItems(LoanUtils.ObProduct);
        SelectButton(); //button
    }

    //for selection of product
    private void SelectButton(){
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = (TableColumn<Product, Void> param) -> {
            TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                private final Button btnAction = new Button("Select");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Product product = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setProduct_PK(product.getProd_id());
                        product_price = product.getProdPrice();
                        System.out.println("Product price" + product_price);
                        /*Product details here*/
                        //action = "Update";
                        LoadProductSelected();
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

    //for deleting a product
    private void DeleteButton(){
        Callback<TableColumn<TableReceipt, Void>, TableCell<TableReceipt, Void>> cellFactory = (TableColumn<TableReceipt, Void> param) -> {
            TableCell<TableReceipt, Void> cell = new TableCell<TableReceipt, Void>() {

                private final Button btnAction = new Button("Delete");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        TableReceipt tr = getTableView().getItems().get(getIndex());
                        //get the customer id for further execution
                        LoanUtils.setProduct_PK(tr.getId());

                        int i = 0;
                        for(TableReceipt tra: LoanUtils.ObTableReceipt){
                            if(tra.getId() == tr.getId())
                                break;
                            i++;
                        }
                        //remove the index
                        LoanUtils.ObTableReceipt.remove(i);
                        //load again the new set
                        LoadDataInReceiptTable();
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

        col_delete.setCellFactory(cellFactory);
    }

    //for viewing the selected product
    private void LoadProductSelected(){
        //get the details using the product PK
        for(Product product: LoanUtils.ObProduct){
            if(product.getProd_id() == LoanUtils.getProduct_PK()){
                tbProductname.setText(product.getProdName());
                return;
            }
        }
    }

    @FXML
    void AddNewProduct(MouseEvent event) {
       /* change the state of
        this classifier to Active*/
        LoanUtils.Loan_to_product_detect = "Active";
        //invoke product window
        Open.Product();
        //load again the table
        //refresh the observable list
        GetSelectionProduct();
    }

    //search product to select
    protected void searchProductChoose() {

        tbSearchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListProduct.setPredicate(product -> {
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return product.getProdName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        sortedListProduct.comparatorProperty().bind(ProductChooseTable.comparatorProperty());
        ProductChooseTable.setItems(sortedListProduct);
    }

    //save all added loaned products to the customer assigned
    //and hit the next window Terms Win
    @FXML
    void btnNext(ActionEvent event) {
        //if table receipt is not empty
        if(!LoanUtils.ObTableReceipt.isEmpty()) {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            Open.TermCondition();
        }
    }

    @FXML
    void GoBack(MouseEvent event) {
        Open.Customers();
        Close.ThisWindow(event);
    }
}
