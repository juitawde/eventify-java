/** Represents an event available for ticket booking. */
public class Event {
    int id;
    String name, category, date;
    Venue venue;
    double regular, premium;
    int booked;

    public Event(int id, String name, String category, String date,
                 Venue venue, double regular, double premium) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.venue = venue;
        this.regular = regular;
        this.premium = premium;
    }

    int available() {
        return venue.capacity - booked;
    }

    @Override
    public String toString() {
        return name + "  •  " + date;
    }
}
