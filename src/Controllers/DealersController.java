package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Classes.Dealers_loan;
import sample.Classes.Entities.Dealer;
import sample.Classes.Utility.LoanUtils;
import sample.WindowState.Open;

import java.net.URL;
import java.util.ResourceBundle;

public class DealersController implements Initializable {

    @FXML private TextField SearchDealer;

    @FXML private TableView<Dealer> DealersTable;

    @FXML private TableColumn<Dealer, Integer> col_dealer_id;

    @FXML private TableColumn<Dealer, String> col_fname;

    @FXML private TableColumn<Dealer, String> col_lname;

    @FXML private TableColumn<Dealer, Double> col_balance;

    @FXML private TableColumn<Dealer, Void> col_update;

    @FXML private TableColumn<Dealer, Void> col_select;

    private final Dealers_loan dealers_loan = new Dealers_loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        InitCols();
        GetDealersData();
    }

    private void InitCols(){
        col_dealer_id.setCellValueFactory(new PropertyValueFactory<>("dealer_id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    protected void GetDealersData(){
        try {
            //load the customers in the observable list
            dealers_loan.GetDealers();
            DealersTable.setItems(LoanUtils.ObDealer);
            UpdateButton(); //update button
            SelectButton();//select button
        }
        catch (NullPointerException ignored){}
    }

    private void UpdateButton() {
        Callback<TableColumn<Dealer, Void>, TableCell<Dealer, Void>> cellFactory = (TableColumn<Dealer, Void> param) -> {
            TableCell<Dealer, Void> cell = new TableCell<Dealer, Void>() {

                private final Button btnAction = new Button("Update");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Dealer dealer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setDealer_PK(dealer.getDealer_id());
                        try {
                            //open add customer window to update
                            LoanUtils.Action_classifier = "Update";
                            Open.AddDealer();
                        } catch(Exception e) {
                            e.printStackTrace();
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

    private void SelectButton() {
        Callback<TableColumn<Dealer, Void>, TableCell<Dealer, Void>> cellFactory = (TableColumn<Dealer, Void> param) -> {
            TableCell<Dealer, Void> cell = new TableCell<Dealer, Void>() {

                private final Button btnAction = new Button("Select");
                {
                    btnAction.setOnAction((ActionEvent event) -> {
                        Dealer dealer = getTableView().getItems().get(getIndex());

                        //get the customer id for further execution
                        LoanUtils.setDealer_PK(dealer.getDealer_id());

                        /*
                        Do the selection of products here and
                        adding the loan

                        try {
                            //open add customer window to update
                            Open.AddDealer();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }*/
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

    @FXML
    void AddDealer(MouseEvent event) {
        Open.AddDealer();
        //close this window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void GoBack(MouseEvent event) {
        Open.Dashboard();
        //close this window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
