package sample.Classes.Entities;

public class Account extends Name {

    private String username;
    private String password;

    public Account(){
        //empty constructor
    }

    //for creation of user account
    public Account(String firstname, String lastname, String username, String password ) {
        super(firstname, lastname);
        this.username = username;
        this.password = password;
    }

    //for updating the username and password
    public Account(String username, String password){
        this.username =  username;
        this.password = password;
    }

    //accessors and mutators
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
