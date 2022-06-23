package com.kainos.ea.resources;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class EmployeesDB {
    private static Connection conn;

    private static Connection getConnection() {
        String user;
        String password;
        String host;

        if (conn != null) {
            return conn;
        }

        try {
            FileInputStream propsStream =
                    new FileInputStream("employeesdb.properties");

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
                    + host + "/Project_OrlaghM?allowPublicKeyRetrieval=true&useSSL=false", user, password);
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static List<Employee> getEmployees() throws SQLException {
//        Connection c = getConnection();
//
//        Statement st = c.createStatement();
//
//        ResultSet rs = st.executeQuery(
//                "SELECT * "
//                        + "FROM Sales_Employees;");
//
//        List<Employee> employees = new ArrayList<>();
//
//        while (rs.next()) {
//            Employee employee = new Employee(
//                    rs.getShort("EmployeeID"),
//                    rs.getString("Name"),
//                    rs.getInt("Salary")
//            );
//
//            employees.add(employee);
//        }
//        return employees;
//    }


//    public static List<Employee> getEmployees() throws SQLException {
//        Connection c = getConnection();
//
//        Statement st = c.createStatement();
//
//        ResultSet rs = st.executeQuery(
//                "SELECT * "
//                        + "FROM Employee order by Department;");
//        List<Employee> employees = new ArrayList<>();
//        while (rs.next()) {
//            Employee employee = new Employee(
//                    rs.getString("Name"),
//                    rs.getString("Department")
//            );
//
//            employees.add(employee);
//        }
//
//            //List.add(rs);
//
////  List.add(rs.getShort("EmployeeID"));
////            List.add(rs.getString("Name"));
////            List.add(rs.getString("Department"));
//
//
//
//        return employees;
//    }

    public static List<Employee> getEmployees() throws SQLException {
        Connection c = getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT * " + "FROM Employee order by Department;");

        List<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            Employee employee = new Employee(
           rs.getString("Name"),
                    rs.getString("Department")
//            );
            );

            employees.add(employee);
        }
        return employees;
    }

//    public static add addEmployee() throws SQLException{
//        Connection c = getConnection();
//
//        Statement st = c.createStatement();
//
//        ResultSet rs = st.executeQuery(
//                "INSERT INTO "
//                        + "FROM delivery_employee;");
//        ArrayList<Object> List = new ArrayList<>();
//        while (rs.next()) {
//            List.add(rs.getShort("EmployeeID"));
//            List.add(rs.getString("Name"));
//            List.add(rs.getInt("Salary"));
//
//        }
//
//        return List;
//
//    }

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
            throw new SQLException("Creating user failed, no rows affected.");
        }

        int empNo = 0;

        try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
            if (rs.next()) {
                empNo = rs.getInt(1);
            }
        }

        return empNo;
    }

//    public int insertSalesEmployee(Employee employee) throws SQLException {
//
//        int empid = insertEmployee(employee);
//
//        Connection c = getConnection();
//        String insertEmployeeQuery = "insert into Sales_Employee (SalesID, CommissionRate, SalesTotal)"
//                + " values (?, ?, ?)";
//
//        PreparedStatement preparedStmt = c.prepareStatement(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS);
//        preparedStmt.setInt(1, empid);
//        preparedStmt.setFloat(2, employee.getCommRate());
//        preparedStmt.setInt(3, employee.getSalesTotal());
//
//        int affectedRows = preparedStmt.executeUpdate();
//        if (affectedRows == 0) {
//            throw new SQLException("Creating user failed, no rows affected.");
//        }
//
//        int empNo = 0;
//        try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
//            if (rs.next()) {
//                empNo = rs.getInt(1);
//            }
//        }
//
//        return empNo;
//    }



}
