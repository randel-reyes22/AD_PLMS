package sample.Classes.Entities;

import javafx.scene.image.Image;

public class Customer extends Name {

    private int customer_id;
    private String mobile;
    private String address;
    private double balance;
    private Image qrcode;

    public Customer(){
        //empty constructor
    }

    //for creation and updating the customer details
    public Customer(String firstname, String lastname, String mobile, String address) {
        super(firstname, lastname);
        this.mobile = mobile;
        this.address = address;
    }

    //with balance
    public Customer(String firstname, String lastname, String mobile, String address,Double balance){
        super(firstname, lastname);
        this.mobile = mobile;
        this.address = address;
        this.balance = balance;
    }

    //with customer id
    public Customer(int id ,String firstname, String lastname, String mobile,
                    String address,Double balance, Image qrcode){
        super(firstname, lastname);
        this.customer_id = id;
        this.mobile = mobile;
        this.address = address;
        this.balance = balance;
        this.qrcode = qrcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Image getQrcode() {
        return qrcode;
    }

    public void setQrcode(Image qrcode) {
        this.qrcode = qrcode;
    }



}
