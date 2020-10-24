package sample.Classes;

import com.google.zxing.WriterException;
import sample.Classes.ConnectDB.Connect;
import sample.Classes.Entities.Customer;
import sample.Classes.Entities.Dealer;
import sample.Classes.Interfaces.IDealer;
import sample.Classes.Interfaces.ILoan;
import sample.Classes.Interfaces.IPay;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Tools.QrCodeGen;
import sample.Classes.Utility.LoanUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dealers_loan extends LoanUtils implements IDealer, ILoan, IPay {

    /*this will provide the api of generating the qr code*/
    private QrCodeGen qrCodeGen = new QrCodeGen();

    //IDealer methods
    @Override
    public void GetDealers() {
        //changes the content of the table
        //from all customers view and repayment window
        String query = "SELECT * FROM main.Dealers";

        ObDealer.clear(); //clear the ob list
        Connection conn = Connect.Link();

        try{

            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()){
                ObDealer.add(new Dealer(rs.getInt("dealer_id"), rs.getString("firstname"),
                                        rs.getString("lastname"), rs.getString("Address"),
                                        rs.getString("mobileNumber"), rs.getString("birthday"),
                                        rs.getDouble("balance"),
                                        qrCodeGen.ConvertByteToImage(rs.getBytes("QrCode"))));
            }
        }
        catch (SQLException | IOException ex) {
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

    @Override
    public void addDealer() {
        /* get the first most added dealer in the linked list */
        Dealer dealer = LLDealer.getFirst();

        String insertCustomer = "INSERT INTO main.Dealers (firstname, lastname, Address, mobileNumber, birthday, QrCode)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = Connect.Link();
        try{
            PreparedStatement ps = conn.prepareStatement(insertCustomer);
            ps.setString(1, dealer.getFirstname());
            ps.setString(2, dealer.getLastname());
            ps.setString(3, dealer.getAddress());
            ps.setString(4, dealer.getMobile());
            ps.setString(5, dealer.getBday());
            ps.setBytes(6, qrCodeGen.GenerateQRCode(qrCodeGen.Build(dealer.getFirstname(), dealer.getLastname(), dealer.getMobile()
                    , dealer.getAddress(), dealer.getBalance())));
            ps.executeUpdate();

            //if customer is successfully added
            MessageBox.ShowInformation("Dealer has been added");
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
    public void updateDealer() {

    }

    //ILoan methods
    @Override
    public boolean AddLoan(double total, String modeOfPayment, String Term, String duedate) throws SQLException {
        return false;
    }

    @Override
    public void GetPaymentHistory(String status) {

    }

    @Override
    public void GetProductsLoaned(String status) {

    }

    @Override
    public void UpdateQrCode() {

    }

    //IPay methods
    @Override
    public void Makepayment(String dategiven, double amount) {

    }

    @Override
    public void CheckStatus() {

    }

}
