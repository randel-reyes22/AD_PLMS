package sample.Classes.Interfaces;

public interface IAccount {
    /*This will authenticate the login credentials*/
    String Login(String username, String password);

    /*This will add an user account*/
    void AddUserAccount();

    /*This will add an customer account*/
    void AddCustomerAccount();

    void UpdateCustomerAccount();

    void GetCustomers();
}
