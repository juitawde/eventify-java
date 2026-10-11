EVENTIFY - Event Ticket Booking & Management System
====================================================

Files
-----
Eventify.java   -> Main Swing GUI and application logic (483 lines)
Event.java      -> Event model
Venue.java      -> Venue model
Customer.java   -> Customer model
Ticket.java     -> Ticket model
Booking.java    -> Booking model
DataStore.java  -> ArrayList, LinkedList, HashMap, TreeMap and 8 sample events

How to run
----------
1. Open this folder in IntelliJ IDEA, Eclipse, NetBeans, or VS Code.
2. Make sure all 7 .java files are in the same source folder.
3. Compile all files.
4. Run Eventify.java.

Command line:
    javac *.java
    java Eventify

Admin login
-----------
Username: admin
Password: admin123

The Admin Report page is accessible only after successful admin login.

Main features
-------------
- 8 sample events
- Event search and Show All Events
- Event sorting by date, name and ticket price
- Customer registration directly on the booking page
- Regular and Premium tickets
- Ticket availability checking
- Booking confirmation and invoice
- Numeric booking IDs
- Customer-side My Bookings page
- Customer-side cancellation
- Cancelled bookings removed from active booking tables
- Admin booking report
- Admin invoice and cancellation controls
- Input validation and exception handling
- Required Java collections demonstrated in DataStore.java
