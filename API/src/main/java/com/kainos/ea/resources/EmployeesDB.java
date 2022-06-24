package com.kainos.ea.resources;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeesDB {

    // VARIABLES
    private static Connection conn;

    // CONNECTION
    private static Connection getConnection() {
        String user;
        String password;
        String host;

        if (conn != null) {
            return conn;
        }

        try {
            FileInputStream propsStream =
                    new FileInputStream("API/employeesdb.properties");

            Properties props = new Properties();
            props.load(propsStream);

            user            = props.getProperty("user");
            password        = props.getProperty("password");
            host            = props.getProperty("host");

            if (user == null || password == null || host == null)
                throw new IllegalArgumentException(
                        "Properties file must exist and must contain "
                                + "user, password, and host properties.");

            conn = DriverManager.getConnection("jdbc:mysql://"
                    + host + "/June23_ZuzannaM?allowPublicKeyRetrieval=true&useSSL=false", user, password);
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // GET METHODS
    public static List<Employee> getEmployees() throws SQLException {
        Connection c = getConnection();

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * " + "FROM Employee;");

        List<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            Employee employee = new Employee(
                    rs.getInt("EmployeeID"),
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("NIN"),
                    rs.getFloat("Salary"),
                    rs.getString("BankAccount"),
                    rs.getString("Department")
            );

            employees.add(employee);
        }
        return employees;
    }

    public static List<SalesEmployee> getSalesEmployees() throws SQLException {
        Connection c = getConnection();

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM Sales_Employee left join Employee on Employee.EmployeeID=Sales_Employee.SalesID;");

        List<SalesEmployee> employees = new ArrayList<>();

        while (rs.next()) {
            SalesEmployee employee = new SalesEmployee(
                    rs.getInt("EmployeeID"),
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("NIN"),
                    rs.getFloat("Salary"),
                    rs.getString("BankAccount"),
                    rs.getString("Department"),
                    rs.getFloat("CommissionRate"),
                    rs.getInt("SalesTotal")
            );

            employees.add(employee);
        }
        return employees;
    }

    public static Employee getEmployeeByID(int empid) throws SQLException {
        Connection c = getConnection();

        String query = "SELECT * FROM Employee WHERE EmployeeID = ?";
        PreparedStatement preparedStmt = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, empid);

        ResultSet rs = preparedStmt.executeQuery();

        List<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            Employee employee = new Employee(
                    rs.getString("Name"),
                    rs.getString("Address"),
                    rs.getString("NIN"),
                    rs.getFloat("Salary"),
                    rs.getString("BankAccount"),
                    rs.getString("Department")

            );

            employees.add(employee);

        }

        if (employees.isEmpty()){
            return null;
        } else {
            return employees.get(0);
        }

    }

    public static List<User> getUsers() throws SQLException {
        Connection c = getConnection();

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * " + "FROM User;");

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Role")
            );

            users.add(user);
        }
        return users;
    }

    // INSERT METHODS
    public int insertEmployee(Employee employee) throws SQLException {

        Connection c = getConnection();
        String insertEmployeeQuery = "insert into Employee (Name, Address, NIN, BankAccount, Salary)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = c.prepareStatement(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, employee.getName());
        preparedStmt.setString(2, employee.getAddress());
        preparedStmt.setString(3, employee.getNin());
        preparedStmt.setString(4, employee.getBank_num());
        preparedStmt.setFloat(5, employee.getSalary());

        int affectedRows = preparedStmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating employee failed, no rows affected.");
        }
        int empNo = 0;

        try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
            if (rs.next()) {
                empNo = rs.getInt(1);
            }
        }

        return empNo;
    }

    public int insertSalesEmployee(SalesEmployee employee) throws SQLException {

        int empid = insertEmployee(employee);

        // CREATE ROW IN SALES EMPLOYEE TABLE
        Connection c = getConnection();
        String insertEmployeeQuery = "insert into Sales_Employee (SalesID, CommissionRate, SalesTotal)"
                + " values (?, ?, ?)";

        PreparedStatement preparedStmt = c.prepareStatement(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, empid);
        preparedStmt.setFloat(2, employee.getCommRate());
        preparedStmt.setInt(3, employee.getSalesTotal());

        int affectedRows = preparedStmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        // UPDATE DEPARTMENT OF EMPLOYEE

        String updateQuery = "UPDATE Employee SET Department = ? WHERE EmployeeID = ?";
        PreparedStatement ps = c.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, "Sales");
        ps.setInt(2, empid);

        int affectedRows2 = ps.executeUpdate();
        if (affectedRows2 == 0) {
            throw new SQLException("Creating sales user failed, no rows affected.");
        }

        return empid;

    }

    public int insertUser(User user) throws SQLException {

        Connection c = getConnection();
        String insertUserQuery = "insert into User (Username, Password, Role)"
                + " values (?, ?, ?)";

        PreparedStatement preparedStmt = c.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, user.getUsername());
        preparedStmt.setString(2, user.getPassword());
        preparedStmt.setString(3, user.getRole());

        int affectedRows = preparedStmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        int userNo = 0;

        try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
            if (rs.next()) {
                userNo = rs.getInt(1);
            }
        }

        return userNo;
    }

    public User verifyUser(User loginUser) throws SQLException {

        Connection c = getConnection();
        String getUserQuery = "select * from User where Username = ?";

        PreparedStatement preparedStmt = c.prepareStatement(getUserQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, loginUser.getUsername());

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            if (loginUser.getPassword().equals(rs.getString("Password"))){
                User user = new User(
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Role")
                );
                return user;
            };
        };
        return null;
    }



}
