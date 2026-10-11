/** Represents one ticket booking. */
public class Booking {
    int id, quantity;
    Customer customer;
    Event event;
    Ticket ticket;
    boolean cancelled;

    public Booking(int id, Customer customer, Event event,
                   Ticket ticket, int quantity) {
        this.id = id;
        this.customer = customer;
        this.event = event;
        this.ticket = ticket;
        this.quantity = quantity;
    }

    double amount() {
        return ticket.price * quantity;
    }
}

