package sample.Classes.Interfaces;

public interface IPay {
    /*This will update the remaining balance of the customer
    * deduct the given amount to the total balance of
    * the customer*/
    void Makepayment(String dategiven, double amount);

    /*will update the active status of the customer
    * if the customer have 0 balance mark the status as
    * non-active else active*/
    void CheckStatus();
}
