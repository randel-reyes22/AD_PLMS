package sample.Classes.Interfaces;

import java.util.Date;


public interface ILoan {

    /*this is used to describe if the product is paid*/
    String PAID = "PAID";
    /*this is used to describe if the product is not yet paid*/
    String UNPAID = "UNPAID";

    /*This will add loan to a particular customer
    * then insert it to the loan table*/
    boolean AddLoan(double total, String modeOfPayment, String Term, String duedate, Date DateAdded);

    /*This will get the unpaid payment history of a particular customer*/
    void GetPaymentHistory(String status);

    /*This will get the products loaned of a particular customer*/
    void GetProductsLoaned(String status);

    /*this will update the qr code of an specific customer*/
    void UpdateQrCode();
}
