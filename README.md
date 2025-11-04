# 🏨 VuHotel — Hotel Management System (OOP1 Project)

A hotel management system built in **Java 17** with a **Swing GUI**, created as part of the _Object-Oriented Programming 1_ university course.  
The project demonstrates core OOP principles, layered architecture, and role-based operations for hotel management.

---

## 📝 About

VuHotel simulates the operations of a hotel, supporting different user roles that perform tasks such as managing rooms, reservations, pricing, guests, cleaning status, and employee administration.

---

## ✨ Features

- Multi-role authentication system
- Room and reservation management
- Check-in / check-out workflow
- Employee and guest management
- Dynamic pricing and additional hotel services
- Cleaning and maintenance scheduling
- Reporting & data visualization (XChart)
- Data persistence stored in CSV files
- GUI built with Swing for cross-platform compatibility

---

## 👥 User Roles

| Role                | Capabilities                                                        |
| ------------------- | ------------------------------------------------------------------- |
| **🔑 Admin**        | Manage employees, rooms, pricing, system configuration, and reports |
| **🏨 Receptionist** | Handle reservations, check-ins, check-outs, assign rooms            |
| **👤 Guest**        | View personal reservations and request services                     |
| **🧹 Maid**         | View assigned rooms and update cleaning / maintenance status        |

---

## 🏗️ System Architecture

```
src/
├── entitiesClasses/      # Business entities (Room, Guest, Employee, Reservation, Service...)
├── entitiesEnums/        # Type definitions (RoomType, ReservationStatus, UserRole, etc.)
├── entitiesManager/      # Manager singletons for business logic + CSV persistence
├── viewBase/             # Shared GUI utilities & components
├── viewAdmin/            # UI for Admin role
├── viewReceptionist/     # UI for Receptionist role
├── viewGuest/            # UI for Guest role
├── viewMaid/             # UI for Maid role
├── reports/              # Report/analytics generation
├── main/                 # Application entry point (Main.java)
├── UnitTests/            # JUnit test suite
assets/                   # UI images and icons
storage/                  # CSV data files
```

---

## 🛠️ Tech Stack

- **Java 17** — Core implementation language
- **Swing** — Graphical user interface
- **Maven** — Build + dependency management
- **JUnit 4** — Unit testing framework
- **JCalendar** — Date picker components
- **XChart** — Chart visualization library
- **CSV Files** — Lightweight data persistence

---

## 🚀 Setup and Running

### Prerequisites

- Java 17+
- Maven (or use the included Maven Wrapper)
- Any IDE (IntelliJ / Eclipse / VS Code) or Terminal

### Clone the Project

```bash
git clone https://github.com/Vukotije/vu-hotel.git
cd vu-hotel
```

### Run Using Maven (Recommended)

```bash
mvn clean compile
mvn test
mvn exec:java -Dexec.mainClass="main.Main"
```

### Or Run the Packaged JAR

```bash
mvn package
java -jar target/hotel-management-1.0.0-jar-with-dependencies.jar
```

---

## 🧪 Testing

Run the unit test suite:

```bash
mvn test
```

---

## 🎓 Learning Objectives Demonstrated

- **Encapsulation**, **Inheritance**, **Polymorphism**, **Abstraction**
- Layered system design and **MVC organization**
- Event-driven GUI programming in Swing
- **Singleton pattern** for data managers
- CSV-based persistence and structured file I/O
- Unit testing and verification of business logic

---

## 🤷 Known Limitations

_It's a learning project._

- CSV storage instead of a relational database
- GUI is functional but not modernized
- Some classes could be further refactored
- Limited error & concurrency handling
