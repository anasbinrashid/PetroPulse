# **PetroPulse: Petrol Station Management System**

## **Project Overview**
**PetroPulse** is a comprehensive Petrol Station Management System designed to streamline and automate petrol station operations. The system supports features such as managing stations, workers, schedules, customers, fuel inventory, transactions, supplier relations, and more. It offers seamless data persistence through MySQL and advanced functionalities implemented using Java and JavaFX.

This project demonstrates object-oriented programming principles, layered architecture, and database management while providing an intuitive user interface for ease of use.

---

## **Features**
- **Owner Management**:
  - View owner-specific station details.
  
- **Station Management**:
  - Manage petrol stations, including location, capacity, earnings, and associated workers and inventory.

- **Worker Management**:
  - Add, update, and remove workers.
  - Assign schedules and associate workers with specific stations.

- **Fuel Inventory**:
  - Manage fuel types, prices, and stock levels.
  - Track fuel stand capacity and assign workers to fuel stands.

- **Transaction Management**:
  - Handle customer orders and fuel transactions.

- **Schedule Management**:
  - Assign and view schedules for workers and stations.
  
- **Maintenance & Order Tracking**:
  - Track maintenance activities and fuel orders.
  - View detailed order history and maintenance records.

- **Customer & Loyalty Points**:
  - Track customer information and manage loyalty points.

---

## **Use Cases**
- **Manage Sales**: Process customer transactions, calculate total fuel cost, and update inventory.
- **View Loyalty Points**: Allow customers to get loyalty points during transactions.
- **Manage Stations**: Add, update, and remove station details.
- **Manage Workers**: Hire new workers, assign roles, and schedule shifts.
- **Track Fuel Orders**: Place orders for fuel and manage delivery schedules.
- **Inventory Management**: Update fuel prices, capacity, and inventory levels.
- **Maintenance Requests**: Log and track maintenance activities for equipment.
- **Reporting**: Generate detailed reports for transactions, inventory, and revenue.

---

## **System Architecture**
The project follows a **layered architecture**:
1. **Presentation Layer**: Built using JavaFX for an intuitive graphical user interface.
2. **Business Logic Layer**: Implements core functionalities and use case operations.
3. **Data Access Layer**: Manages persistence using MySQL and handles database interactions.

---

## **Database Schema**
The system uses the **PetroPulse** database schema:

### Tables:
1. **owner**: Stores details of station owners.
2. **station**: Tracks station information (location, capacity, earnings).
3. **worker**: Maintains worker details and associations with stations.
4. **schedule**: Tracks worker schedules and station timings.
5. **fuelstand**: Stores fuel inventory details, prices, and associated workers.
6. **supplier**: Tracks supplier details for fuel and maintenance orders.
7. **fuelorder**: Manages fuel orders placed with suppliers.
8. **maintenance**: Logs maintenance activities for equipment.
9. **orderdetails**: Tracks transactions between customers and stations.

---

## **Technologies Used**
- **Programming Language**: Java (JDK 17)
- **Framework**: JavaFX for UI development
- **Database**: MySQL Workbench

---

## **Run Configuration**
### Prerequisites
1. **Java Development Kit (JDK)**: Ensure JDK 17 is installed.
2. **MySQL Database**: Install MySQL and set up the database using the provided schema.
3. **IDE**: Use Eclipse or IntelliJ IDEA for running the project.
4. **JavaFX SDK**: Download and configure JavaFX.

### Setting Up the Project
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-name>
   ```
2. **Import the Project**:
   - Open Eclipse or IntelliJ IDEA.
   - Import the project.

3. **Configure JavaFX**:
   - Add the JavaFX SDK to your IDE’s library settings.
   - Set VM arguments for running JavaFX:
     ```bash
     --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```

4. **Set Up the Database**:
   - Open MySQL Workbench.
   - Execute the SQL script in `database/petropulse_schema.sql` to create the database schema.
   - Update database connection details in the `DatabaseHandler` class:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/PetroPulse";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```

5. **Run the Project**:
   - Use the `Main.java` file to launch the application.
   - Ensure that the database server is running before starting the application.

---

## **Project Documentation**
### GitHub URLs:
- **Documentation**: [Project Documentation](https://github.com/anasbinrashid/PetroPulse/tree/main/Documentation)
- **Presentation**: [Project Presentation](https://github.com/anasbinrashid/PetroPulse/tree/main/Presentation_Slides)
---

## **How to Contribute**
1. Fork the repository and create a new branch for your feature or fix:
   ```bash
   git checkout -b feature-name
   ```
2. Commit your changes:
   ```bash
   git commit -m "Add your message here"
   ```
3. Push to your fork:
   ```bash
   git push origin feature-name
   ```
4. Open a pull request.

---

## **Acknowledgments**
- Special thanks to the team for contributions.
- This project was made possible with tools like JavaFX, MySQL, and GitHub.
