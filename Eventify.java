import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class Eventify extends JFrame {

    // ==================== COLORS / FONTS ====================

    static final Color GREEN = new Color(38,91,76);
    static final Color CREAM = new Color(248,245,238);
    static final Color WHITE = Color.WHITE;
    static final Color TEXT = new Color(45,45,45);
    static final Color MUTED = new Color(105,105,105);
    static final Color ACCENT = new Color(173,92,55);

    static final Font TITLE = new Font("SansSerif",Font.BOLD,30);
    static final Font HEAD = new Font("SansSerif",Font.BOLD,22);
    static final Font LABEL = new Font("SansSerif",Font.BOLD,14);
    static final Font NORMAL = new Font("SansSerif",Font.PLAIN,14);

    // ==================== UI VARIABLES ====================

    JPanel content, headerPanel;
    CardLayout cards;

    JButton adminButton;
    boolean adminLoggedIn = false;

    JComboBox<Event> eventBox;
    JComboBox<String> ticketBox;
    JSpinner quantity; //A JSpinner is the little control where the user can increase/decrease a number.

    JTextField nameField, emailField, phoneField;
    JTextField searchField, bookingIdField;

    JLabel availability, total;

    JTable eventTable, bookingTable, adminTable;
    DefaultTableModel eventModel, bookingModel, adminModel;

    // ==================== CONSTRUCTOR ====================

    public Eventify() {
        setTitle("EVENTIFY - Event Ticket Booking & Management System");
        setSize(1180,760);
        setMinimumSize(new Dimension(1050,680));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if(DataStore.events.isEmpty()) DataStore.seedData();

        buildUI();
        showCard("events");
    }

    // ==================== MAIN UI ====================

    void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(CREAM);

        headerPanel = header();
        root.add(headerPanel,BorderLayout.NORTH);

        cards = new CardLayout();
        content = new JPanel(cards);
        content.setBackground(CREAM);
        content.setBorder(new EmptyBorder(24,34,28,34));

        content.add(eventsPage(),"events");
        content.add(bookPage(),"book");
        content.add(myBookingsPage(),"mine");
        content.add(adminPage(),"admin");
        content.add(addEventPage(),"add");

        root.add(content,BorderLayout.CENTER);
        setContentPane(root);
    }

    // ==================== HEADER ====================

    JPanel header() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(GREEN);
        h.setBorder(new EmptyBorder(22,32,18,32));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("EVENTIFY");
        title.setForeground(WHITE);
        title.setFont(TITLE);

        JLabel sub = new JLabel("Event Ticket Booking & Management System");
        sub.setForeground(new Color(225,235,230));
        sub.setFont(NORMAL);

        brand.add(title);
        brand.add(Box.createVerticalStrut(4));
        brand.add(sub);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,8));
        right.setOpaque(false);

        if(adminLoggedIn) {
            right.add(navButton("ADMIN REPORT",e -> showCard("admin"),150));
            right.add(navButton("ADD EVENT",e -> showCard("add"),125));
        } else {
            right.add(navButton("EVENTS",e -> showCard("events"),110));
            right.add(navButton("BOOK TICKETS",e -> showCard("book"),145));
            right.add(navButton("MY BOOKINGS",e -> showCard("mine"),145));
        }

        JLabel adminLabel = new JLabel("Admin:");
        adminLabel.setFont(LABEL);
        adminLabel.setForeground(WHITE);
        right.add(adminLabel);

        adminButton = button(adminLoggedIn ? "LOGOUT" : "LOGIN",CREAM,GREEN);
        adminButton.setPreferredSize(new Dimension(90,40));
        adminButton.addActionListener(e -> adminAction());
        right.add(adminButton);

        h.add(brand,BorderLayout.WEST);
        h.add(right,BorderLayout.EAST);

        return h;
    }

    JButton navButton(String text,ActionListener action,int width) {
        JButton b = button(text,CREAM,GREEN);
        b.setPreferredSize(new Dimension(width,40));
        b.addActionListener(action);
        return b;
    }

    JButton button(String text,Color bg,Color fg) {
        JButton b = new JButton(text);
        b.setFont(LABEL);
        b.setForeground(fg);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(new CompoundBorder(
                new LineBorder(bg.darker(),1,true),
                new EmptyBorder(8,14,8,14)
        ));

        int width = Math.max(120,text.length()*9+35);
        //Never allow the button to be smaller than 120 pixels.
        b.setPreferredSize(new Dimension(width,40));
        b.setMinimumSize(new Dimension(width,40));
        //Makes sure the button isn't shrunk below that size.
        return b;
    }

    // ==================== HEADER REBUILD ====================

    void rebuildHeader() {
        Container root = getContentPane();

        if(headerPanel != null) root.remove(headerPanel);

        headerPanel = header();
        root.add(headerPanel,BorderLayout.NORTH);

        root.revalidate();
        root.repaint();
    }

    // ==================== EVENTS PAGE ====================

    JPanel eventsPage() {
        JPanel p = page();
        p.add(heading("Discover Events"),BorderLayout.NORTH);

        JPanel top = new JPanel(new BorderLayout(10,10));
        top.setOpaque(false);

        JLabel hint = new JLabel("Search by event name, category or location");
        hint.setFont(NORMAL);
        hint.setForeground(MUTED);

        searchField = new JTextField();
        searchField.setFont(NORMAL);
        searchField.setPreferredSize(new Dimension(100,40));
        searchField.addActionListener(e -> searchEvents());

        JPanel searchArea = new JPanel(new BorderLayout(0,7));
        searchArea.setOpaque(false);
        searchArea.add(hint,BorderLayout.NORTH);
        searchArea.add(searchField,BorderLayout.CENTER);

        JButton search = button("SEARCH EVENTS",GREEN,WHITE);
        JButton all = button("SHOW ALL EVENTS",GREEN,WHITE);

        search.addActionListener(e -> searchEvents());
        all.addActionListener(e -> {
            searchField.setText("");
            loadEvents(DataStore.events);
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        actions.setOpaque(false);
        actions.add(search);
        actions.add(all);

        top.add(searchArea,BorderLayout.CENTER);
        top.add(actions,BorderLayout.SOUTH);

        eventModel = model("ID","EVENT","CATEGORY","DATE & TIME","VENUE","AVAILABLE","REGULAR ₹");
        eventTable = new JTable(eventModel);
        styleTable(eventTable);
        loadEvents(DataStore.events);

        JPanel center = new JPanel(new BorderLayout(0,14));
        center.setOpaque(false);
        center.add(top,BorderLayout.NORTH);
        center.add(new JScrollPane(eventTable),BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);

        JLabel sortLabel = label("Sort events by:");
        JComboBox<String> sortBox = new JComboBox<>(new String[]{"Date","Name","Ticket Price"});
        sortBox.setFont(NORMAL);
        sortBox.addActionListener(e -> sortEvents(String.valueOf(sortBox.getSelectedItem())));
        // gets whatever they selected.
        // For example:
        // Name

        bottom.add(sortLabel);
        bottom.add(sortBox);
        center.add(bottom,BorderLayout.SOUTH);

        p.add(center,BorderLayout.CENTER);
        return p;
    }

    void searchEvents() {
        String q = searchField.getText().trim().toLowerCase();
        ArrayList<Event> result = new ArrayList<>();

        for(Event e : DataStore.events) {
            if(e.name.toLowerCase().contains(q)
                    || e.category.toLowerCase().contains(q)
                    || e.venue.name.toLowerCase().contains(q))
                result.add(e);
        }

        loadEvents(result);
    }

    void sortEvents(String by) {
        //This method receives a String telling it what to sort by.
        ArrayList<Event> copy = new ArrayList<>(DataStore.events);

        if("Name".equals(by))
            copy.sort(Comparator.comparing(e -> e.name));
            //A Comparator is basically a rule for comparing objects.
            //Create a sorting rule based on some property of the object.
            //For every Event e, use its name when deciding the order.
        else if("Ticket Price".equals(by))
            copy.sort(Comparator.comparingDouble(e -> e.regular));
        else
            copy.sort(Comparator.comparing(e -> e.date));

        loadEvents(copy);
    }

    void loadEvents(java.util.List<Event> list) {
        //It accepts a list of Event objects.
        if(eventModel == null) return;

        eventModel.setRowCount(0);
        //This removes all existing rows from the table model.

        for(Event e : list) {
            eventModel.addRow(new Object[]{
                    e.id,e.name,e.category,e.date,e.venue.name,
                    e.available()+"/"+e.venue.capacity,money(e.regular)
            });
        }
    }

    // ==================== BOOKING PAGE ====================

    JPanel bookPage() {
        JPanel p = page();
        p.add(heading("Book Your Tickets"),BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,215,205)),
                new EmptyBorder(22,28,22,28)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,8,8,8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.weightx = 1;

        int r = 0;

        addLabel(form,g,"TICKET SELECTION",0,r++,2);

        eventBox = new JComboBox<>(DataStore.events.toArray(new Event[0]));
        ticketBox = new JComboBox<>();
        quantity = new JSpinner(new SpinnerNumberModel(1,1,20,1));
        quantity.setFont(NORMAL);

        addRow(form,g,"Event",eventBox,r++);
        addRow(form,g,"Ticket Category",ticketBox,r++);
        addRow(form,g,"Quantity",quantity,r++);

        addLabel(form,g,"CUSTOMER REGISTRATION",0,r++,2);

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        addRow(form,g,"Full Name",nameField,r++);
        addRow(form,g,"Email Address",emailField,r++);
        addRow(form,g,"Phone Number",phoneField,r++);

        availability = valueLabel("");
        total = valueLabel("");

        addRow(form,g,"Availability",availability,r++);
        addRow(form,g,"Estimated Total",total,r++);

        JButton book = button("BOOK TICKET",ACCENT,WHITE);
        book.setPreferredSize(new Dimension(155,42));
        book.addActionListener(e -> bookTicket());

        JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,10));
        action.setOpaque(false);
        action.add(book);

        g.gridx=0;
        g.gridy=r;
        g.gridwidth=2;
        form.add(action,g);

        eventBox.addActionListener(e -> updateTicketChoices());
        ticketBox.addActionListener(e -> updatePrice());
        quantity.addChangeListener(e -> updatePrice());

        updateTicketChoices();

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(4,120,4,120));
        wrap.add(form,BorderLayout.CENTER);

        p.add(wrap,BorderLayout.CENTER);
        return p;
    }

    void updateTicketChoices() {
        ticketBox.removeAllItems();

        if(eventBox.getSelectedItem() != null) {
            ticketBox.addItem("Regular");
            ticketBox.addItem("Premium");
        }

        updatePrice();
    }

    void updatePrice() {
        Event e = (Event)eventBox.getSelectedItem();
        if(e == null) return;

        int q = (Integer)quantity.getValue();
        String cat = String.valueOf(ticketBox.getSelectedItem());

        double price = cat.startsWith("Premium") ? e.premium : e.regular;

        availability.setText(e.available()+" seats available at "+e.venue.name);
        total.setText(money(price*q));
    }

    void bookTicket() {
        Event e = (Event)eventBox.getSelectedItem();

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        int q = (Integer)quantity.getValue();

        if(e == null || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            error("Please complete all customer details.");
            return;
        }

        if(!email.contains("@") || phone.length() < 10) {
            error("Enter a valid email and phone number.");
            return;
        }

        if(q > e.available()) {
            error("Tickets unavailable. Only "+e.available()+" seats remain.");
            return;
        }

        Customer c = new Customer(name,email,phone);
        DataStore.customers.add(c);

        String cat = String.valueOf(ticketBox.getSelectedItem());
        boolean premium = cat.startsWith("Premium");

        Ticket t = new Ticket(
                premium ? "Premium" : "Regular",
                premium ? e.premium : e.regular
        );

        int id = nextBookingId();
        Booking b = new Booking(id,c,e,t,q);

        DataStore.bookings.put(id,b);
        DataStore.history.add(b);
        e.booked += q;

        // Immediately update My Bookings after successful booking.
        refreshMyBookings();

        JOptionPane.showMessageDialog(
                this,
                "Booking confirmed!\nBooking ID: "+id+
                "\nAmount: "+money(b.amount()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        clearBooking();
    }

    int nextBookingId() {
        int id = 1001;
        while(DataStore.bookings.containsKey(id)) id++;
        return id;
    }

    void clearBooking() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        quantity.setValue(1);
        updatePrice();
        loadEvents(DataStore.events);
    }

    // ==================== MY BOOKINGS ====================

    JPanel myBookingsPage() {
        JPanel p = page();
        p.add(heading("My Bookings"),BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        top.setOpaque(false);

        top.add(label("Booking ID"));

        bookingIdField = new JTextField(12);
        bookingIdField.setFont(NORMAL);

        JButton load = button("LOAD BOOKING",GREEN,WHITE);
        JButton cancel = button("CANCEL BOOKING",ACCENT,WHITE);
        JButton invoice = button("VIEW INVOICE",GREEN,WHITE);

        load.addActionListener(e -> loadMyBooking());
        cancel.addActionListener(e -> cancelBooking());
        invoice.addActionListener(e -> invoice());

        top.add(bookingIdField);
        top.add(load);
        top.add(cancel);
        top.add(invoice);

        bookingModel = model("BOOKING ID","EVENT","TICKET","QTY","AMOUNT","STATUS");
        bookingTable = new JTable(bookingModel);
        styleTable(bookingTable);

        JPanel center = new JPanel(new BorderLayout(0,15));
        center.setOpaque(false);
        center.add(top,BorderLayout.NORTH);
        center.add(new JScrollPane(bookingTable),BorderLayout.CENTER);

        p.add(center,BorderLayout.CENTER);
        return p;
    }

    void loadMyBooking() {
        bookingModel.setRowCount(0);

        String s = bookingIdField.getText().trim();

        // Empty search = show every active booking.
        if(s.isEmpty()) {
            refreshMyBookings();

            if(bookingModel.getRowCount() == 0)
                error("No active bookings found.");

            return;
        }

        try {
            int id = Integer.parseInt(s);
            Booking b = DataStore.bookings.get(id);

            if(b == null || b.cancelled) {
                error("Booking not found.");
                return;
            }

            bookingModel.addRow(new Object[]{
                    b.id,b.event.name,b.ticket.category,
                    b.quantity,money(b.amount()),"CONFIRMED"
            });

        } catch(NumberFormatException ex) {
            error("Enter the numeric Booking ID only.");
        }
    }

    // Automatically fills the My Bookings table.
    void refreshMyBookings() {
        if(bookingModel == null) return;

        bookingModel.setRowCount(0);

        for(Booking b : DataStore.bookings.values()) {
            if(!b.cancelled) {
                bookingModel.addRow(new Object[]{
                        b.id,b.event.name,b.ticket.category,
                        b.quantity,money(b.amount()),"CONFIRMED"
                });
            }
        }
    }

    void cancelBooking() {
        try {
            int id = Integer.parseInt(bookingIdField.getText().trim());
            Booking b = DataStore.bookings.get(id);

            if(b == null || b.cancelled) {
                error("Active booking not found.");
                return;
            }

            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Cancel booking "+id+"?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if(ok != JOptionPane.YES_OPTION) return;

            b.cancelled = true;
            b.event.booked -= b.quantity;
            DataStore.bookings.remove(id);

            refreshMyBookings();
            loadEvents(DataStore.events);

            JOptionPane.showMessageDialog(this,"Booking cancelled successfully.");

        } catch(NumberFormatException ex) {
            error("Enter the numeric Booking ID only.");
        }
    }

    void invoice() {
        try {
            int id = Integer.parseInt(bookingIdField.getText().trim());
            Booking b = DataStore.bookings.get(id);

            if(b == null || b.cancelled) {
                error("Active booking not found.");
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "EVENTIFY INVOICE\n\n"+
                    "Booking ID: "+b.id+
                    "\nEvent: "+b.event.name+
                    "\nTicket: "+b.ticket.category+
                    "\nQuantity: "+b.quantity+
                    "\nTotal: "+money(b.amount()),
                    "Invoice",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch(Exception ex) {
            error("Enter a valid Booking ID.");
        }
    }

    // ==================== ADMIN DASHBOARD ====================

    JPanel adminPage() {
        JPanel p = page();
        p.add(heading("Admin Dashboard"),BorderLayout.NORTH);


        adminModel = model("BOOKING ID","EVENT","TICKET","QTY","AMOUNT","STATUS");
        adminTable = new JTable(adminModel);
        styleTable(adminTable);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        top.setOpaque(false);

        top.add(label("Booking ID"));

        JTextField id = new JTextField(12);
        id.setFont(NORMAL);

        JButton view = button("VIEW INVOICE",GREEN,WHITE);
        JButton cancel = button("CANCEL BOOKING",ACCENT,WHITE);
        JButton refresh = button("LOAD BOOKINGS",GREEN,WHITE);

        view.addActionListener(e -> adminInvoice(id.getText()));
        cancel.addActionListener(e -> adminCancel(id.getText()));
        refresh.addActionListener(e -> refreshAdmin());

        top.add(id);
        top.add(view);
        top.add(cancel);
        top.add(refresh);

        JPanel tableArea = new JPanel(new BorderLayout());
        tableArea.setOpaque(false);
        tableArea.add(new JScrollPane(adminTable),BorderLayout.CENTER);

        JPanel vertical = new JPanel();
        vertical.setOpaque(false);
        vertical.setLayout(new BoxLayout(vertical,BoxLayout.Y_AXIS));

        top.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        vertical.add(top);
        vertical.add(Box.createVerticalStrut(12));
        vertical.add(tableArea);
        vertical.add(Box.createVerticalStrut(12));
        vertical.add(tableArea);

        p.add(vertical,BorderLayout.CENTER);

        refreshAdmin();
        return p;
    }

    JPanel statCard(String title,String value) {
        JPanel p = new JPanel(new BorderLayout(0,5));
        p.setBackground(WHITE);
        p.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,215,205)),
                new EmptyBorder(15,18,15,18)
        ));

        JLabel t = label(title);
        t.setForeground(MUTED);

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif",Font.BOLD,22));
        v.setForeground(GREEN);

        p.add(t,BorderLayout.NORTH);
        p.add(v,BorderLayout.CENTER);
        return p;
    }

    void refreshAdmin() {
        if(adminModel == null) return;

        adminModel.setRowCount(0);

        for(Booking b : DataStore.history) {
            if(!b.cancelled) {
                adminModel.addRow(new Object[]{
                        b.id,b.event.name,b.ticket.category,
                        b.quantity,money(b.amount()),"CONFIRMED"
                });
            }
        }
    }

    int cancelledCount() {
        int count = 0;

        for(Booking b : DataStore.history)
            if(b.cancelled) count++;

        return count;
    }

    double revenue() {
        double total = 0;

        for(Booking b : DataStore.history)
            if(!b.cancelled) total += b.amount();

        return total;
    }

    void adminCancel(String s) {
        try {
            int id = Integer.parseInt(s.trim());
            Booking b = DataStore.bookings.get(id);

            if(b == null) {
                error("Active booking not found.");
                return;
            }

            int ok = JOptionPane.showConfirmDialog(
                    this,
                    "Cancel booking "+id+"?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if(ok != JOptionPane.YES_OPTION) return;

            b.cancelled = true;
            b.event.booked -= b.quantity;
            DataStore.bookings.remove(id);

            refreshAdmin();
            loadEvents(DataStore.events);
            refreshMyBookings();

            JOptionPane.showMessageDialog(this,"Booking cancelled.");

        } catch(Exception e) {
            error("Enter a valid numeric Booking ID.");
        }
    }

    void adminInvoice(String s) {
        try {
            int id = Integer.parseInt(s.trim());
            Booking b = DataStore.bookings.get(id);

            if(b == null || b.cancelled) {
                error("Active booking not found.");
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Booking ID: "+b.id+
                    "\nEvent: "+b.event.name+
                    "\nTicket: "+b.ticket.category+
                    "\nQuantity: "+b.quantity+
                    "\nTotal: "+money(b.amount()),
                    "Invoice",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch(Exception e) {
            error("Enter a valid numeric Booking ID.");
        }
    }

    // ==================== ADD EVENT PAGE ====================

    JPanel addEventPage() {
        JPanel p = page();
        p.add(heading("Add New Event"),BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,215,205)),
                new EmptyBorder(25,35,25,35)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,8,8,8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JTextField name = new JTextField();
        JComboBox<String> category = new JComboBox<>(
                new String[]{
                        "Concert","Conference","Workshop","Comedy",
                        "Festival","Business","Sports","Other"
                }
        );

        JTextField date = new JTextField();
        JTextField venue = new JTextField();
        JTextField capacity = new JTextField();
        JTextField regular = new JTextField();
        JTextField premium = new JTextField();

        int r = 0;

        addRow(form,g,"Event Name",name,r++);
        addRow(form,g,"Category",category,r++);
        addRow(form,g,"Date & Time",date,r++);
        addRow(form,g,"Venue",venue,r++);
        addRow(form,g,"Venue Capacity",capacity,r++);
        addRow(form,g,"Regular Price",regular,r++);
        addRow(form,g,"Premium Price",premium,r++);

        JButton clear = button("CLEAR",CREAM,GREEN);
        JButton add = button("ADD EVENT",ACCENT,WHITE);

        clear.addActionListener(e -> {
            name.setText("");
            date.setText("");
            venue.setText("");
            capacity.setText("");
            regular.setText("");
            premium.setText("");
            category.setSelectedIndex(0);
        });

        add.addActionListener(e -> {
            try {
                String n = name.getText().trim();
                String d = date.getText().trim();
                String v = venue.getText().trim();

                if(n.isEmpty() || d.isEmpty() || v.isEmpty())
                    throw new Exception("Please fill all event details.");

                int seats = Integer.parseInt(capacity.getText().trim());
                double rp = Double.parseDouble(regular.getText().trim());
                double pp = Double.parseDouble(premium.getText().trim());

                if(seats <= 0 || rp < 0 || pp < 0)
                    throw new Exception("Enter valid capacity and prices.");

                int id = nextEventId();
                Venue newVenue = new Venue(v,seats);

                Event event = new Event(
                        id,n,String.valueOf(category.getSelectedItem()),
                        d,newVenue,rp,pp
                );

                DataStore.addEvent(event);
                refreshEventBox();
                loadEvents(DataStore.events);

                JOptionPane.showMessageDialog(
                        this,
                        "Event added successfully.\nEvent ID: "+id,
                        "Event Added",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clear.doClick();

            } catch(NumberFormatException ex) {
                error("Capacity and prices must be numeric.");
            } catch(Exception ex) {
                error(ex.getMessage());
            }
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT,18,10));
        buttons.setOpaque(false);
        buttons.add(clear);
        buttons.add(add);

        g.gridx = 0;
        g.gridy = r;
        g.gridwidth = 2;
        form.add(buttons,g);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(new EmptyBorder(10,180,10,180));
        wrap.add(form,BorderLayout.CENTER);

        p.add(wrap,BorderLayout.CENTER);
        return p;
    }

    int nextEventId() {
        int id = 1;

        for(Event e : DataStore.events)
            if(e.id >= id) id = e.id + 1;

        return id;
    }

    void refreshEventBox() {
        if(eventBox == null) return;

        Event selected = (Event)eventBox.getSelectedItem();

        eventBox.removeAllItems();

        for(Event e : DataStore.events)
            eventBox.addItem(e);

        if(selected != null) {
            for(int i=0;i<eventBox.getItemCount();i++) {
                if(eventBox.getItemAt(i).id == selected.id) {
                    eventBox.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    // ==================== ADMIN LOGIN / LOGOUT ====================

    void adminAction() {
        if(adminLoggedIn) {
            adminLoggedIn = false;

            rebuildHeader();
            showCard("events");
            loadEvents(DataStore.events);

        } else {
            adminLogin();
        }
    }

    void adminLogin() {
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        user.setFont(NORMAL);
        pass.setFont(NORMAL);

        JPanel form = new JPanel(new GridLayout(2,2,12,14));
        form.setBorder(new EmptyBorder(12,15,12,15));
        form.setPreferredSize(new Dimension(380,145));

        form.add(label("Username"));
        form.add(user);
        form.add(label("Password"));
        form.add(pass);

        int ok = JOptionPane.showConfirmDialog(
                this,
                form,
                "Admin Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(ok == JOptionPane.OK_OPTION
                && user.getText().trim().equals("admin")
                && new String(pass.getPassword()).equals("admin123")) {

            adminLoggedIn = true;

            rebuildHeader();
            showCard("admin");

        } else if(ok == JOptionPane.OK_OPTION) {
            error("Invalid admin credentials.");
        }
    }

    // ==================== HELPERS ====================

    JPanel page() {
        JPanel p = new JPanel(new BorderLayout(0,18));
        p.setBackground(CREAM);
        return p;
    }

    JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(HEAD);
        l.setForeground(GREEN);
        return l;
    }

    JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL);
        l.setForeground(TEXT);
        return l;
    }

    JLabel valueLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL);
        l.setForeground(GREEN);
        return l;
    }

    void addLabel(JPanel p,GridBagConstraints g,String text,int x,int y,int span) {
        g.gridx=x;
        g.gridy=y;
        g.gridwidth=span;

        JLabel l = label(text);
        l.setForeground(GREEN);
        p.add(l,g);
    }

    void addRow(JPanel p,GridBagConstraints g,String name,Component c,int y) {
        g.gridwidth=1;
        g.gridy=y;
        g.gridx=0;
        g.weightx=.25;
        p.add(label(name),g);

        g.gridx=1;
        g.weightx=.75;

        if(c instanceof JComponent)
            ((JComponent)c).setFont(NORMAL);

        p.add(c,g);
    }

    DefaultTableModel model(String... cols) {
        return new DefaultTableModel(cols,0) {
            public boolean isCellEditable(int r,int c) {
                return false;
            }
        };
    }

    void styleTable(JTable t) {
        t.setFont(NORMAL);
        t.setRowHeight(32);
        t.setAutoCreateRowSorter(true);
        t.setSelectionBackground(new Color(214,228,221));
        t.setSelectionForeground(TEXT);

        t.getTableHeader().setFont(LABEL);
        t.getTableHeader().setBackground(new Color(226,234,229));
    }

    void showCard(String name) {
        if("admin".equals(name) && !adminLoggedIn) return;

        cards.show(content,name);

        if("admin".equals(name))
            refreshAdmin();
        else if("mine".equals(name))
            refreshMyBookings();
    }

    void error(String text) {
        JOptionPane.showMessageDialog(
                this,
                text,
                "Eventify",
                JOptionPane.WARNING_MESSAGE
        );
    }

    String money(double x) {
        return String.format(Locale.US,"₹%.2f",x);
    }

    // ==================== START ====================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Eventify().setVisible(true));
    }
}