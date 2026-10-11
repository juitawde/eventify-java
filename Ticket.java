/** Represents a ticket category and its price. */
public class Ticket {
    String category;
    double price;

    public Ticket(String category, double price) {
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return category + " - ₹" + String.format("%.2f", price);
    }
}
