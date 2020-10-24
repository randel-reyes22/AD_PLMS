package sample.Classes.Entities;

import javafx.scene.image.Image;

public class Dealer extends Name{

    private int dealer_id;
    private String mobile;
    private String address;
    private double balance;
    private String bday;
    private Image qrcode;

    public Dealer(){
        //empty constructor
    }


    //for creation and updating the dealer details
    public Dealer(String firstname, String lastname, String mobile, String address, String bday) {
        super(firstname, lastname);
        this.mobile = mobile;
        this.address = address;
        this.bday = bday;
    }

    //for creation and updating the dealer details
    public Dealer(String firstname, String lastname, String mobile, String bday ,String address, Image qrcode) {
        super(firstname, lastname);
        this.mobile = mobile;
        this.bday = bday;
        this.address = address;
        this.qrcode = qrcode;
    }

    //with dealer id
    public Dealer(int dealer_id, String firstname, String lastname, String address ,String mobileNumber, String birthday, double balance, Image qrCode) {
        super(firstname, lastname);
        this.dealer_id = dealer_id;
        this.mobile = mobileNumber;
        this.address = address;
        this.bday = birthday;
        this.balance = balance;
        this.qrcode = qrCode;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
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

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Image getQrcode() {
        return qrcode;
    }

    public void setQrcode(Image qrcode) {
        this.qrcode = qrcode;
    }
}
