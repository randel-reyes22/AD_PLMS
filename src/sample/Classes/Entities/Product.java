package sample.Classes.Entities;

public class Product {

    private int prod_id;
    private String prodName;
    private Double prodPrice;
    private String prodUnit;

    public Product(){
        //empty constructor
    }

    //id and name only
    public Product(int prod_id, String prodName) {
        this.prod_id = prod_id;
        this.prodName = prodName;
    }

    //constructor with unit
    public Product(String prodName, Double prodPrice, String unit) {
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodUnit = unit;
    }

    //with id
    public Product(int id, String prodName, Double prodPrice){
        this.prod_id = id;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }

    //with unit
    public Product(int id, String prodName, Double prodPrice, String unit){
        this.prod_id = id;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodUnit = unit;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }
}
