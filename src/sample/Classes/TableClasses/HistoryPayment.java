package sample.Classes.TableClasses;

public class HistoryPayment {

    private int load_id;
    private double collection_amount;
    private String givenDate;

    public HistoryPayment(int load_id, double collection_amount, String givenDate) {
        this.load_id = load_id;
        this.collection_amount = collection_amount;
        this.givenDate = givenDate;
    }

    public int getLoad_id() {
        return load_id;
    }

    public void setLoad_id(int load_id) {
        this.load_id = load_id;
    }

    public double getCollection_amount() {
        return collection_amount;
    }

    public void setCollection_amount(double collection_amount) {
        this.collection_amount = collection_amount;
    }

    public String getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(String givenDate) {
        this.givenDate = givenDate;
    }
}
