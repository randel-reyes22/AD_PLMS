package sample.Classes.TableClasses;

public class TableReceipt {

    private int id;
    private String prod_name;
    private double prod_price;
    private int qty;
    private double total;

    public TableReceipt(){
        //empty constructor
    }

    public TableReceipt(int id, String prod_name, double prod_price, int qty, double total) {
        this.id = id;
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.qty = qty;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public double getProd_price() {
        return prod_price;
    }

    public int getQty() {
        return qty;
    }

    public double getTotal() {
        return total;
    }

}
