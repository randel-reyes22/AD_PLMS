package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Classes.ConnectDB.Connect;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Loan;
import sample.Classes.Utility.LoanUtils;
import sample.Classes.Utility.WeekDates;
import sample.WindowState.Close;
import sample.WindowState.Open;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private AreaChart<String, Double> RevenueChart;

    @FXML private Label LoanOut;

    @FXML private Label Revenue;

    @FXML private ImageView Logout;

    @FXML private Label CurrentUser;

    @FXML private ToggleButton Toggle;

    //classes
    private final Loan loan = new Loan();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets current user name in tuhe label form
        CurrentUser.setText(LoanUtils.sess_firstname + " " + LoanUtils.sess_lastname);
        //default selected mode is today
        //Today.setSelected(true);
        AreaWeekRevenueChart();
        /*get the revenue
                default is today*/
        Revenue.setText(Format(Math.round(loan.TotalRevenueToday())));
    }

    private void AreaWeekRevenueChart(){
        String revenueWeek = "SELECT  GivenDate ,sum(CollectionAmount) " +
                                "FROM main.Collections " +
                                "WHERE GivenDate  = ?";
        //invoke method
        WeekDates.GetAllWeekDates();
        //coordinates
        XYChart.Series seriesRevenue= new XYChart.Series();

        for(String dates: WeekDates.dates){
            Connection conn = Connect.Link();
            try {
                PreparedStatement ps = conn.prepareStatement(revenueWeek);
                ps.setString(1, dates);
                ResultSet result = ps.executeQuery();
                result.next();

                seriesRevenue.setName("Revenue");

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
        RevenueChart.getData().addAll(seriesRevenue);
    }

    @FXML
    void ToggleMode(ActionEvent event) {
        if(Toggle.isSelected()) {
            Toggle.setText("Today");
            Revenue.setText(Format(Math.round(loan.TotalRevenueToday())));
        } else {
            Toggle.setText("Week");
            Revenue.setText(Format(Math.round(loan.TotalRevenueThisWeek())));
        }
    }

    private String Format(double amount){
        return String.format("%8.2f", amount);
    }

    @FXML
    void OpenProducts(MouseEvent event) {
        Open.Product();
        Close.ThisWindow(event);
    }

    @FXML
    void OpenNewLoan(MouseEvent event) {
        //change the state of the action classifier
        LoanUtils.Action_classifier = "Select";

        Open.Customers(); /*open customer window to select a customer to begin with a new loan
                            then after selecting open the new loan window*/
        Close.ThisWindow(event);
    }

    @FXML
    void OpenPayment(MouseEvent event) {
        Open.Payment();
        Close.ThisWindow(event);
    }

    @FXML
    void OpenReports(MouseEvent event) {
        Open.Reports();
        Close.ThisWindow(event);
    }

    @FXML
    void OpenCustomers(MouseEvent event) {
        //change the state of the action classifier
        LoanUtils.Action_classifier = "Update";
        Open.Customers();
        Close.ThisWindow(event);
    }

    @FXML
    void OpenDealers(MouseEvent event) {
        Open.Dealers();
        Close.ThisWindow(event);
    }

    @FXML
    void AddAccount(MouseEvent event) {
        Open.AddAccount();
        Close.ThisWindow(event);
    }

    @FXML
    void Logout(MouseEvent event) {
        Open.Login();
        Close.ThisWindow(event);
    }
}
