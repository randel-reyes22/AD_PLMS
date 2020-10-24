package sample.WindowState;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Open {

//  open dashboard window
    public static void Dashboard(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Dashboard.fxml"));
            Parent dashboard = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(dashboard));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//    open the new loan window
    public static void NewLoan(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/NewLoan.fxml"));
            Parent newLoan = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(newLoan));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //open add account window
    public static void AddAccount(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/AddAccount.fxml"));
            Parent account = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(account));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//  open customers window
    public static void Customers(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Customers.fxml"));
            Parent customer = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(customer));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //  open customers window
    public static void Dealers(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Dealers.fxml"));
            Parent customer = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(customer));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//  open add customer window
    public static void AddCustomer(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/AddCustomer.fxml"));
            Parent addCustomer = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(addCustomer));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //  open add customer window
    public static void AddDealer(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/AddDealer.fxml"));
            Parent addCustomer = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(addCustomer));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//  open payment window
    public static void Payment(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Repayment.fxml"));
            Parent payment = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(payment));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // open product window
    public static void Product(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Product.fxml"));
            Parent product = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(product));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //  open reports window
    public static void Reports(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Reports.fxml"));
            Parent report = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(report));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // open login window
    public static void Login(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/Login.fxml"));
            Parent login = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(login));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //open term condition window
    public static void TermCondition(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/TermCondition.fxml"));
            Parent term_condition = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(term_condition));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //open MakePayment window
    public static void MakePayment(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/MakePayment.fxml"));
            Parent makePayment = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(makePayment));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //open PaymentHistory window
    public static void PaymentHistory(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/PaymentHistory.fxml"));
            Parent paymentHistory = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(paymentHistory));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //open ViewLoanedProducts window
    public static void LoanedProducts(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Open.class.getResource("../../FXMLFiles/ViewLoanedProducts.fxml"));
            Parent loanedProducts = fxmlLoader.load();
            Stage window = new Stage();
            window.setScene(new Scene(loanedProducts));
            window.setResizable(false);
            window.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
