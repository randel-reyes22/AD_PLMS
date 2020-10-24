package sample.Classes;

import sample.Classes.ConnectDB.Connect;
import sample.Classes.Entities.Customer;
import sample.Classes.Tools.MessageBox;
import sample.Classes.Interfaces.IPay;

import java.sql.*;

public class PayOut extends Loan implements IPay {

    @Override
    public void Makepayment(String dategiven, double amount) {
        //insert into  Collections
        String collect = "INSERT INTO main.Collections (CustomerFk, CollectionAmount, GivenDate) " +
                         "VALUES (?, ?, ?)";

        //update balance of the customer
        //deduct to the remaining balance of the customer
        String updateBalance = "UPDATE main.Customer SET Balance = Balance - ? " +
                                "WHERE CustomerId = ?";
        //open connection
        Connection conn = Connect.Link();
        
        try{
            //query 1
            PreparedStatement ps = conn.prepareStatement(collect);
            ps.setInt(1, getCustomer_PK());
            ps.setDouble(2, amount);
            ps.setString(3, dategiven);
            ps.executeUpdate();

            //query two
            PreparedStatement ps2 = conn.prepareStatement(updateBalance);
            ps2.setDouble(1, amount);
            ps2.setInt(2, getCustomer_PK());
            ps2.executeUpdate();

            UpdateQrCode(); //will update the latest remaining balance

            //if customer is successfully added
            MessageBox.ShowInformation("Payment has been deducted \n " +
                    "to the remaining balance");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("An error occurred");
        }
        finally {
            try {
                CheckStatus();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void CheckStatus() {

        String updateStatus = "UPDATE main.Loan SET Status = ? " +
                                "WHERE LoanId = ?";

        String loanId = "SELECT * FROM main.Loan WHERE CustomerFk = ? AND Status = ?";

        Connection conn;
        conn = Connect.Link();

        try{
            PreparedStatement ps1 = conn.prepareStatement(loanId);
            ps1.setInt(1, getCustomer_PK());
            ps1.setString(2, "UNPAID");
            ResultSet rs = ps1.executeQuery();

            LoanIds.clear();
            while (rs.next()){ //add all loan id
                LoanIds.add(rs.getInt("LoanId"));
            }

            //find the customer using the customer PK
            for (Customer customer : ObCustomer) {
                if (customer.getCustomer_id() == getCustomer_PK()) {
                    //if balance is equals to 0 mark inactive
                    PreparedStatement ps = conn.prepareStatement(updateStatus);
                    for(Integer loan_id: LoanIds) {
                        if (customer.getBalance() == 0.00 || customer.getBalance() == 0.0 || customer.getBalance() == 00.00) {
                            //query 1
                            ps.setString(1, PAID);
                            ps.setInt(2, loan_id);
                            ps.executeUpdate();

                            //if customer have 0 remaining balance
                            MessageBox.ShowInformation("Customer has 0 remaining balance");
                        }
                    }
                }
            }

        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            //if an exception occurs
            MessageBox.ShowError("Cannot update status");
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
