package sample.Classes.TableClasses;

public class LoanedProducts {

    private String prod_name;
    private double prod_price;
    private int prod_qty;
    private String prod_payment_mode;
    private String due;
    private String term;

    public LoanedProducts(String prod_name, double prod_price, int prod_qty,
                          String prod_payment_mode, String due, String term) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_qty = prod_qty;
        this.prod_payment_mode = prod_payment_mode;
        this.due = due;
        this.term = term;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_qty() {
        return prod_qty;
    }

    public void setProd_qty(int prod_qty) {
        this.prod_qty = prod_qty;
    }

    public String getProd_payment_mode() {
        return prod_payment_mode;
    }

    public void setProd_payment_mode(String prod_payment_mode) {
        this.prod_payment_mode = prod_payment_mode;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
