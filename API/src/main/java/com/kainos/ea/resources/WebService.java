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
    @Path("/print/db")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEMP()throws SQLException {
        try {
            return "Here are the employees: " + EmployeesDB.getEmployees();
        } catch (SQLException e) {
            return "Error: " + e;
        }
    }
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addEMP(Employee employee)throws SQLException {
        try {
        EmployeesDB eb = new EmployeesDB();
        eb.insertEmployee(employee);

            return String.format("Here is the new employee: " + employee.getName()
                    + ' ' + employee.getAddress()
                    + ' ' + employee.getNin()
                    + ' ' + employee.getBank_num()
                    + ' ' + employee.getSalary());
        } catch (SQLException e) {
            return "Error: "+ e.getMessage();
        }
    }

//    @POST
//    @Path("/add_sales_emp")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String addSalesEMP(Employee employee)throws SQLException {
//        try {
//            EmployeesDB eb = new EmployeesDB();
//            eb.insertSalesEmployee(employee);
//
//            return String.format("Here is the new employee: " + employee.getName()
//                    + ' ' + employee.getAddress()
//                    + ' ' + employee.getNin()
//                    + ' ' + employee.getBank_num()
//                    + ' ' + employee.getSalary());
//        } catch (SQLException e) {
//            return "Error: "+ e.getMessage();
//        }
//    }


//    @GET
//    @Path("/employee_department")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getEMP_by_dep()throws SQLException{
//
//
//    }



}
