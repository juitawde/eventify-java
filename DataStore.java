import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Central in-memory data store.
 * The collections below directly demonstrate the structures required
 * in the Eventify case study.
 */
public class DataStore {
    public static final ArrayList<Event> events = new ArrayList<>();
    public static final ArrayList<Venue> venues = new ArrayList<>();
    public static final ArrayList<Customer> customers = new ArrayList<>();
    public static final LinkedList<Booking> history = new LinkedList<>();
    public static final HashMap<Integer, Booking> bookings = new HashMap<>();
    public static final TreeMap<String, Event> eventsByDate = new TreeMap<>();

    private DataStore() { }

    /** Adds the sample events used when the application starts. */
    public static void seedData() {
        Venue v1 = new Venue("Grand Arena", 200);
        Venue v2 = new Venue("City Convention Hall", 120);
        Venue v3 = new Venue("Creative Studio", 60);
        Venue v4 = new Venue("Skyline Auditorium", 150);

        venues.add(v1);
        venues.add(v2);
        venues.add(v3);
        venues.add(v4);

        addEvent(new Event(1, "Neon Nights Concert", "Concert",
                "15 Nov 2026, 07:30 PM", v1, 999, 1499));

        addEvent(new Event(2, "FutureTech Conference", "Conference",
                "05 Dec 2026, 10:00 AM", v2, 799, 1199));

        addEvent(new Event(3, "Creative Coding Workshop", "Workshop",
                "25 Oct 2026, 02:00 PM", v3, 499, 799));

        addEvent(new Event(4, "Comedy Night Live", "Comedy",
                "01 Nov 2026, 08:00 PM", v4, 899, 1299));

        addEvent(new Event(5, "Indie Music Festival", "Festival",
                "20 Dec 2026, 05:00 PM", v1, 1199, 1799));

        addEvent(new Event(6, "Design Thinking Summit", "Conference",
                "12 Jan 2027, 11:00 AM", v2, 699, 999));

        addEvent(new Event(7, "Photography Masterclass", "Workshop",
                "18 Jan 2027, 03:00 PM", v3, 599, 899));

        addEvent(new Event(8, "Startup Founders Meetup", "Business",
                "30 Jan 2027, 06:00 PM", v4, 399, 699));
    }

    /** Stores a new event and also keeps the TreeMap updated. */
    public static void addEvent(Event event) {
        //Event → the class/type defined in Event.java
        //event → a reference variable that points to a particular Event object
        events.add(event);
        eventsByDate.put(event.date + "-" + event.id, event);
    }
}