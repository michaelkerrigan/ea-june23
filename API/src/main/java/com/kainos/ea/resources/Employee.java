package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.GET;

public class Employee {

    public Employee(String name, String dept){
        this.name = name;
        this.dept = dept;
    };
    public Employee(String name, String address, String nin, float salary, String bank_num) {
        this.name = name;
        this.address = address;
        this.nin = nin;
        this.salary = salary;
        this.bank_num = bank_num;
    }

    private String name;
    private String address;
    private String nin;

//    public int getSalesID() {
//        return SalesID;
//    }
//
//    public void setSalesID(int salesID) {
//        SalesID = salesID;
//    }
//
//    private int SalesID;
//
//    private float CommRate;
//
//    public float getCommRate() {
//        return CommRate;
//    }
//
//    public void setCommRate(float commRate) {
//        CommRate = commRate;
//    }
//
//    private int SalesTotal;
//
//    public int getSalesTotal() {
//        return SalesTotal;
//    }
//
//    public void setSalesTotal(int salesTotal) {
//        SalesTotal = salesTotal;
//    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    private String dept;

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    private float salary;



    private String bank_num;

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @JsonCreator
    public Employee(@JsonProperty("name") String name,
                    @JsonProperty("address") String address,
                    @JsonProperty("salary") float salary,
                    @JsonProperty("nin") String nin,
                    @JsonProperty("bank_num") String bank_num
    )
    {
        this.setName(name);
        this.setAddress(address);
        this.setNin(nin);
        this.setBank_num(bank_num);
        this.setSalary(salary);
    }

//    @JsonCreator
//    public Employee(@JsonProperty("SalesID") int id,
//                    @JsonProperty("CommissionRate") float CommRate,
//                    @JsonProperty("SalesTotal") int total
//    )
//    {
//        this.setSalesID(id);
//        this.setCommRate(CommRate);
//        this.setSalesTotal(total);
//    }

    public String toString(){
        return this.name + ": " + this.dept;
    }





}
