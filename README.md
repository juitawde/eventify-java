# 🎟️ Eventify — Event Ticket Booking and Management System

> A Java Swing desktop application for discovering events, booking tickets, managing reservations, and handling event administration.

---

## 📌 Overview

**Eventify** is a Java-based Event Ticket Booking and Management System developed using **Java Swing**. It provides a graphical interface where users can explore available events, search and sort event listings, book Regular or Premium tickets, view booking details, generate invoices, and cancel reservations.

The application also includes an administrative interface for viewing active bookings, managing reservations, and adding new events.

This project demonstrates the practical use of **Object-Oriented Programming (OOP), Java Swing, event handling, input validation, searching, sorting, and the Java Collection Framework**.

---

## ✨ Features

### 👤 User Features

- **Browse Events** — View available events and their details.
- **Search Events** — Search by event name, category, or venue.
- **Sort Events** — Sort events by date, name, or regular ticket price.
- **Ticket Selection** — Choose between Regular and Premium ticket categories.
- **Booking Form** — Enter customer details and select the required ticket quantity.
- **Input Validation** — Validate required fields, basic email format, phone number length, and seat availability.
- **Booking Confirmation** — Receive confirmation after a successful booking.
- **My Bookings** — Retrieve active booking details using a booking ID.
- **Invoice Generation** — Display booking details and the total ticket amount.
- **Booking Cancellation** — Cancel an active booking and release the associated seats.

### 🔐 Admin Features

- **Admin Login** — Access the administrative interface.
- **Booking Management** — View active bookings in a structured table.
- **Invoice Access** — View invoice details for a booking.
- **Booking Cancellation** — Cancel active reservations.
- **Add New Events** — Create events by entering event details, venue information, capacity, and ticket prices.
- **Event Listing Updates** — Make newly added events available in the event list while the application is running.

---

## 🛠️ Technologies Used

| Technology               | Purpose                                       |
| ------------------------ | --------------------------------------------- |
| Java                     | Core programming language                     |
| Java Swing               | Graphical user interface                      |
| JFrame and JPanel        | Main window and interface layout              |
| CardLayout               | Navigation between application pages          |
| JTable                   | Displaying events and bookings                |
| JTextField and JComboBox | User input and selection controls             |
| JOptionPane              | Dialogs, confirmations, and messages          |
| ArrayList                | Storing events, venues, and customers         |
| LinkedList               | Maintaining booking history                   |
| HashMap                  | Mapping active booking IDs to booking objects |
| TreeMap                  | Maintaining events using date-and-ID keys     |
| Comparator               | Sorting events                                |
| ActionListener           | Handling button actions                       |

---

## 🏗️ Project Structure

The project is organized into separate Java files, with the main application, data management class, and model classes handling different responsibilities.

```text
Eventify/
│
├── Eventify.java       # Main application, GUI, and operations
├── DataStore.java      # Shared collections and sample data
├── Event.java          # Event model
├── Venue.java          # Venue model
├── Customer.java       # Customer model
├── Ticket.java         # Ticket model
├── Booking.java        # Booking model
└── README.md           # Project documentation
```

---

## 🧠 Core Concepts Implemented

### 1. Object-Oriented Programming

The application uses separate classes to represent events, venues, customers, tickets, and bookings. This separates the responsibilities of the main entities and makes the code easier to organize and maintain.

### 2. Java Collection Framework

Eventify uses different collections according to their intended purpose:

- **ArrayList:** Stores events, venues, and customers.
- **LinkedList:** Maintains booking history, including cancelled booking records during the current execution.
- **HashMap:** Stores active bookings using booking IDs as keys.
- **TreeMap:** Stores events using composite date-and-ID keys in sorted key order.

### 3. Searching and Sorting

The search functionality checks event names, categories, and venue names against the user's search term.

The sorting functionality uses Java's `Comparator` utilities to arrange events by date, name, or regular ticket price.

### 4. Booking Validation

Before a booking is created, the application checks the required customer fields, performs basic email and phone checks, and verifies that the requested quantity does not exceed available seats.

### 5. Booking and Cancellation Management

After validation, the application creates the required customer, ticket, and booking objects. It stores the booking in the active booking map and booking history, then updates the event's booked-seat count.

When a booking is cancelled, its status is updated, it is removed from the active booking map, and the associated seats are released.

## 🔄 Application Workflow

```text
Start Application
       |
       v
Initialize Sample Data
       |
       v
Display Events Page
       |
       v
Search / Sort Events
       |
       v
Select Event and Ticket Category
       |
       v
Enter Customer Details
       |
       v
Validate Booking Information
       |
       v
Check Seat Availability
       |
       v
Create Booking
       |
       v
Update Collections and Seat Count
       |
       v
Display Booking Confirmation
       |
       v
View Invoice / Manage Booking
       |
       v
Cancel Booking if Required
```

Administrators can separately access the Admin Dashboard to view active bookings, manage reservations, and add new events.

---

## 💰 Booking Amount Calculation

The booking amount is calculated using the selected ticket category and ticket quantity.

**Formula:**

```text
Total Booking Amount = Price per Ticket × Quantity
```

The application uses the Regular or Premium ticket price associated with the selected event.

---

## 📸 Screenshots

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 16 29 PM" src="https://github.com/user-attachments/assets/06e539e6-970d-4f53-9400-7bdc1733391a" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 17 45 PM" src="https://github.com/user-attachments/assets/77bac905-fc68-444b-b20a-72e341bd3f07" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 18 17 PM" src="https://github.com/user-attachments/assets/19ef772a-1765-4c9c-b95a-3bd3c533868f" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 18 40 PM" src="https://github.com/user-attachments/assets/d329657f-3d6f-4e2c-bc7d-7ddfde77ca9f" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 20 00 PM" src="https://github.com/user-attachments/assets/195a4710-6b16-4ce5-b8df-e54c334709e2" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 21 45 PM" src="https://github.com/user-attachments/assets/ee782e47-d0f8-49e8-bd8b-4a8694eaf3d4" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 23 34 PM" src="https://github.com/user-attachments/assets/2b7b8da4-cec9-4c1e-acb5-bdcf777836c6" />

<img width="1470" height="956" alt="Screenshot 2026-10-11 at 8 24 01 PM" src="https://github.com/user-attachments/assets/5b3fa3a9-3e83-4732-88c7-2195937eb443" />

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- Java Development Kit (JDK)
- A terminal or command prompt
- A Java-compatible IDE or code editor (optional)

Verify that Java is installed:

```bash
java -version
javac -version
```

### Installation and Execution

**1. Clone the repository**

Replace the URL below with your actual GitHub repository URL.

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

**2. Navigate to the project directory**

```bash
cd Eventify
```

Make sure all the required `.java` files are in the current directory, or adjust the command to match your project structure.

**3. Compile the Java files**

```bash
javac *.java
```

**4. Run the application**

```bash
java Eventify
```

The Eventify desktop application should open in a new window.

### Running Through an IDE

1. Open the project in your Java IDE.
2. Ensure all Java source files are included in the same project.
3. Locate `Eventify.java`.
4. Run the class containing the `main()` method.

## 🧪 Testing Checklist

The following checks can be performed to verify the application's functionality.

- [ ] Verify that the Events page displays available events.
- [ ] Search for an event by name, category, or venue.
- [ ] Sort events by date, name, and ticket price.
- [ ] Attempt a booking with missing customer information.
- [ ] Verify that a booking exceeding available seats is rejected.
- [ ] Complete a valid booking and note the booking ID.
- [ ] Retrieve the booking from My Bookings.
- [ ] Verify the invoice amount for the selected ticket category and quantity.
- [ ] Cancel an active booking and verify that the seats are released.
- [ ] Access the Admin Dashboard and view active bookings.
- [ ] Add a new event and verify that it appears in the Events page.

---

## ⚠️ Limitations

- **In-memory storage:** Application data is maintained using Java collections and is not automatically saved to a permanent database.
- **No integrated payment gateway:** The application calculates and displays ticket amounts but does not process real online payments.
- **Basic input validation:** Email and phone checks are basic and do not verify the authenticity of contact details.
- **Prototype-level authentication:** The administrative login is intended for an academic project and is not production-grade authentication.
- **Single application instance:** The project does not provide centralized multi-user booking management or synchronization across separate application instances.

Because data is stored in memory, bookings and newly added events are not automatically retained after the application closes.

---

## 🔮 Future Enhancements

Possible future improvements include:

- Integrating MySQL or PostgreSQL for permanent data storage.
- Implementing secure authentication and role-based access control.
- Adding an online payment gateway.
- Improving input validation.
- Adding detailed booking and event reports.
- Maintaining permanent booking and cancellation history.
- Supporting multiple users through a client-server architecture.
- Improving event filters and the overall user experience.

---

## 🎯 Learning Outcomes

Through this project, the following concepts are demonstrated:

- Java programming and Object-Oriented Programming.
- GUI development using Java Swing.
- Event-driven programming and user interaction.
- Data management using the Java Collection Framework.
- Searching and sorting using Java utilities.
- Input validation and conditional logic.
- Modular application design.
- Implementation of booking, cancellation, and administrative workflows.

---

## 👩‍💻 Author

**Jui Tawde**

---

## 📄 License

This project was developed for academic and learning purposes. Add a license file if you intend to distribute the project under a specific open-source license.

---

⭐ *Eventify demonstrates how Java Swing and object-oriented programming can be used to build a desktop-based event ticket booking and management system.*
