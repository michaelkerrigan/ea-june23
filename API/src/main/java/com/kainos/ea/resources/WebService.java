package com.kainos.ea.resources;

import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/api")
@Api("Engineering Academy Dropwizard API")

public class WebService {
//    @GET
//    @Path("/print/{msg}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getMsg(@PathParam("msg") String message) {
//        return "Hello from a RESTful Web service: " + message;
//    }

//    @POST
//    @Path("/print")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String sendMsg(Message message) {
//        return "Hello from a RESTful Web service: " + message.getText();
//    }

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees()throws SQLException {
        try {
            return EmployeesDB.getEmployees();
        } catch (SQLException e) {
            return null;
        }
    }

    @GET
    @Path("/sales-employees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SalesEmployee> getSalesEmployees()throws SQLException {
        try {
            return EmployeesDB.getSalesEmployees();
        } catch (SQLException e) {
            return null;
        }
    }

//    @GET
//    @Path("/get-pay")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List getPay()throws SQLException {
//        try {
//
//            return EmployeesDB.getPay();
//        } catch (SQLException e) {
//            return null;
//        }
//    }


    @GET
    @Path("/employees/{empid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeByID(@PathParam("empid") int id) throws SQLException {
        try {
            return EmployeesDB.getEmployeeByID(id);
        } catch (SQLException e) {
            return null;
        }
    }

    @POST
    @Path("/employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int addEmployee(Employee employee) throws SQLException {
        try {
            EmployeesDB eb = new EmployeesDB();
            int id = eb.insertEmployee(employee);

            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @POST
    @Path("/sales_employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int addSalesEmployee(SalesEmployee employee) throws SQLException {
        try {
            EmployeesDB eb = new EmployeesDB();
            int id = eb.insertSalesEmployee(employee);
            return id;
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.getMessage());
            return -1;
        }
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public int addUser(User user) throws SQLException {
        try {
            EmployeesDB eb = new EmployeesDB();
            int id = eb.insertUser(user);

            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers()throws SQLException {
        try {
            return EmployeesDB.getUsers();
        } catch (SQLException e) {
            return null;
        }
    }

    @POST
    @Path("/verify-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User verifyUser(User user) throws SQLException {
        try {
            EmployeesDB eb = new EmployeesDB();
            User result = eb.verifyUser(user);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
