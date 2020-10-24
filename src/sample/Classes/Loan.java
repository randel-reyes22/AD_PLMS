package sample.Classes;

import com.google.zxing.WriterException;
import sample.Classes.ConnectDB.Connect;
import sample.Classes.Entities.Account;
import sample.Classes.Entities.Customer;
import sample.Classes.Entities.Product;
import sample.Classes.Tools.Hash;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Interfaces.IAccount;
import sample.Classes.Interfaces.ILoan;
import sample.Classes.Interfaces.IProduct;
import sample.Classes.Interfaces.IWallet;
import sample.Classes.TableClasses.HistoryPayment;
import sample.Classes.TableClasses.LoanedProducts;
import sample.Classes.TableClasses.TableReceipt;
import sample.Classes.Tools.QrCodeGen;
import sample.Classes.Utility.LoanUtils;
import sample.Classes.Utility.WeekDates;

import java.io.IOException;
import java.sql.*;

public class Loan extends LoanUtils implements IAccount, IProduct, ILoan, IWallet {

    private QrCodeGen qrCodeGen = new QrCodeGen();

    @Override
    public String Login(String username, String password) {
        Connection conn = Connect.Link();
        final String Username = username;
        final String Password = password;

        String sql = "SELECT * FROM main.Account";  //query string

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                if(Username.equals(rs.getString("username")) &&
                        Password.equals( Hash.Decode(rs.getString( "password" )))){
                    //set the session values
                    LoanUtils.sess_firstname = rs.getString("Firstname");
                    LoanUtils.sess_lastname = rs.getString("Lastname");

                    //if login is a success
                    return "success";
                }
            }
            return "failed";
        }

        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return "error";
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //IAccount methods implementation
    @Override
    public void AddUserAccount() {
        /* get the first most added customer in the linked list */
        Account account = LLAccount.getFirst();

        String insertCustomer = "INSERT INTO Account (Firstname, Lastname, Username, Password)" +
                                "VALUES (?, ?, ?, ?)";
        Connection conn = Connect.Link();

        try{
            PreparedStatement ps = conn.prepareStatement(insertCustomer);
            ps.setString(1, account.getFirstname());
            ps.setString(2, account.getLastname());
            ps.setString(3, account.getUsername());
            ps.setString(4, account.getPassword());
            ps.executeUpdate();

            //if customer is successfully added
            MessageBox.ShowInformation("Account has been added");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void AddCustomerAccount() {
        /* get the first most added customer in the linked list */
        Customer customer = LLCustomer.getFirst();

        String insertCustomer = "INSERT INTO main.Customer (Firstname, Lastname, MobileNumber, Address, QrCode)" +
                                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = Connect.Link();
        try{
            PreparedStatement ps = conn.prepareStatement(insertCustomer);
            ps.setString(1, customer.getFirstname());
            ps.setString(2, customer.getLastname());
            ps.setString(3, customer.getMobile());
            ps.setString(4, customer.getAddress());
            ps.setBytes(5, qrCodeGen.GenerateQRCode(qrCodeGen.Build(customer.getFirstname(), customer.getLastname(), customer.getMobile()
                            , customer.getAddress(), customer.getBalance())));
            ps.executeUpdate();

            //if customer is successfully added
            MessageBox.ShowInformation("Customer has been added");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void UpdateCustomerAccount() {
        /* get the first most added customer in the linked list */
        Customer customer = LLCustomer.getFirst();

        String updateCustomer = "UPDATE main.Customer " +
                "SET Firstname = ?, Lastname = ?, MobileNumber = ?, Address = ?" +
                "WHERE CustomerId = ?";
        Connection conn = Connect.Link();
        try{
            PreparedStatement ps = conn.prepareStatement(updateCustomer);
            ps.setString(1, customer.getFirstname());
            ps.setString(2, customer.getLastname());
            ps.setString(3, customer.getMobile());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, LoanUtils.Customer_PK);
            ps.executeUpdate();

            UpdateQrCode();

            //if customer is successfully added
            MessageBox.ShowInformation("Customer has been updated");
        }
        catch (SQLException ex){
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

    @Override
    public void GetCustomers() {
        //changes the content of the table
        //from all customers view and repayment window
        String query = null;
        switch (LoanUtils.ChangeView){
            case "CustomerView":
                query = "SELECT * FROM main.Customer";
                break;
            case "RepaymentView":
                query = "SELECT * FROM main.Customer WHERE Balance > 0";
                break;
        }

        ObCustomer.clear(); //clear the ob list
        Connection conn = Connect.Link();

        try{

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()){
                ObCustomer.add(new Customer(rs.getInt("CustomerId"), rs.getString("Firstname"),
                        rs.getString("Lastname"), rs.getString("MobileNumber"), rs.getString("Address")
                        ,rs.getDouble("Balance"), qrCodeGen.ConvertByteToImage(rs.getBytes("QrCode"))));
            }
        }
        catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                LoanUtils.ChangeView = "CustomerView";
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //IProduct methods implementation
    @Override
    public void AddProduct() {
        /* get the first most added customer in the linked list */
        Product product = LLProduct.getFirst();

        Connection conn = Connect.Link();
        String insertCustomer = "INSERT INTO main.Product (ProdName, ProdPrice, ProdUnit)" +
                                "VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = conn.prepareStatement(insertCustomer);
            ps.setString(1, product.getProdName());
            ps.setDouble(2, product.getProdPrice());
            ps.setString(3, product.getProdUnit());
            ps.executeUpdate();

            //if customer is successfully added
            MessageBox.ShowInformation("Product has been added");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void UpdateProduct() {
        /* get the first most added customer in the linked list */
        Product product = LLProduct.getFirst();

        Connection conn = Connect.Link();
        String updateProduct = "UPDATE main.Product " +
                                "SET ProdName = ?, ProdPrice = ?, ProdUnit = ? " +
                                "WHERE ProductId = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(updateProduct);
            ps.setString(1, product.getProdName());
            ps.setDouble(2, product.getProdPrice());
            ps.setString(3, product.getProdUnit());
            ps.setInt(4, LoanUtils.getProduct_PK());
            ps.executeUpdate();

            UpdateQrCode();//update qr for the latest balance

            //if customer is successfully added
            MessageBox.ShowInformation("Product has been updated");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void GetProducts() {
        LoanUtils.ObProduct.clear(); //clear the ob list
        Connection conn = Connect.Link();
        try{
            String sql = "SELECT * FROM main.Product";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                LoanUtils.ObProduct.add(new Product(rs.getInt("ProductId"), rs.getString("ProdName"),
                        rs.getDouble("ProdPrice"), rs.getString("ProdUnit")));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //ILoan methods implementation
    @Override
    public boolean AddLoan(double total, String modeOfPayment, String Term, String duedate) {

        String addLoan = "INSERT INTO main.Loan " +
                "(CustomerFk, ProductFk, PaymentMode, Duedate, Term, Qty, Status)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String addBalance = "UPDATE main.Customer SET Balance = Balance + ? WHERE CustomerId = ?";

        Connection conn = Connect.Link();

        try {
            PreparedStatement ps = conn.prepareStatement(addLoan);

            for (TableReceipt tr : ObTableReceipt) {
                ps.setInt(1, LoanUtils.getCustomer_PK());
                ps.setInt(2, tr.getId());
                ps.setString(3, modeOfPayment);
                ps.setString(4, duedate);
                ps.setString(5, Term);
                ps.setInt(6, tr.getQty());
                ps.setString(7, UNPAID);
                ps.executeUpdate();
            }

            //add the total in the customer remaining balance
            PreparedStatement ps2 = conn.prepareStatement(addBalance);
            ps2.setDouble(1, total);
            ps2.setInt(2, getCustomer_PK());
            ps2.executeUpdate();

            UpdateQrCode(); //will update the qr code with the latest qr code

            //if customer is successfully added
            MessageBox.ShowInformation("Loan has been saved");
            return true;

        } catch (SQLException ex) {
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void GetPaymentHistory(String status) {
        LoanUtils.ObHistoryPayments.clear(); //clear the ob list
        Connection conn = Connect.Link();
        try{
            String sql = "SELECT CollectionId, CollectionAmount, GivenDate " +
                    "FROM Collections WHERE CustomerFk = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, getCustomer_PK());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                LoanUtils.ObHistoryPayments.add(new HistoryPayment(rs.getInt("CollectionId"),
                        rs.getDouble("CollectionAmount"),rs.getString("GivenDate")));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void GetProductsLoaned(String status) {
        LoanUtils.ObLoanedProducts.clear(); //clear the ob list
        Connection conn = Connect.Link();
        try{
            String sql = "SELECT [p].ProdName, [p].ProdPrice, [L].Qty, [L].PaymentMode, [L].Duedate, [L].Term " +
            "FROM Product AS p INNER JOIN Loan L on p.ProductId = [L].ProductFk " +
            "WHERE CustomerFk = ? AND [L].Status = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, getCustomer_PK());
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                LoanUtils.ObLoanedProducts.add(new LoanedProducts(rs.getString("ProdName"), rs.getDouble("ProdPrice"),
                                            rs.getInt("Qty"), rs.getString("PaymentMode"),
                                            rs.getString("Duedate"), rs.getString("Term")));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void UpdateQrCode() {

        GetCustomers();

        String updateQrCode = "UPDATE main.Customer SET QrCode = ? WHERE CustomerId = ?";

        Connection conn = Connect.Link();

        try {
            PreparedStatement ps = conn.prepareStatement(updateQrCode);

            //update the qr code with the latest balance
            for (Customer customer : ObCustomer) {
                if (customer.getCustomer_id() == getCustomer_PK()) {
                    ps.setBytes(1, qrCodeGen.GenerateQRCode(qrCodeGen.Build(customer.getFirstname(), customer.getLastname(), customer.getMobile()
                            , customer.getAddress(), customer.getBalance())));
                    ps.setInt(2, getCustomer_PK());
                    break; //if found
                }
            }
            ps.executeUpdate();

            //if customer is successfully added
            MessageBox.ShowInformation("Loan has been saved");

        } catch (SQLException ex) {
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //IWallet methods implementation
    @Override
    public double TotalRevenueToday() {

        String revenueToday = "SELECT sum(CollectionAmount) FROM main.Collections where GivenDate = ?";
        Connection conn = Connect.Link();
        try{
            PreparedStatement ps =  conn.prepareStatement(revenueToday);
            ps.setString(1, WeekDates.DateNow());
            ResultSet result = ps.executeQuery();
            result.next();

            return result.getDouble(1);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
            return 0.00;
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public double TotalRevenueThisWeek() {

        String revenueToday = "SELECT sum(CollectionAmount) FROM main.Collections " +
                                "WHERE GivenDate BETWEEN ? AND ?";
        Connection conn = Connect.Link();
        try{
            PreparedStatement ps =  conn.prepareStatement(revenueToday);
            ps.setString(1, WeekDates.getMonday());
            ps.setString(2, WeekDates.getSunday());
            ResultSet result = ps.executeQuery();
            result.next();

            return result.getDouble(1);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
            return 0.00;
        }
        finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
