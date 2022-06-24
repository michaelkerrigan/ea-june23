package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    // CONSTRUCTORS
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
    public Employee(String name, String address, String nin, float salary, String bank_num, String dept) {
        this.name = name;
        this.address = address;
        this.nin = nin;
        this.salary = salary;
        this.bank_num = bank_num;
        this.dept = dept;
    }
    public Employee(int id, String name, String address, String nin, float salary, String bank_num, String dept) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nin = nin;
        this.salary = salary;
        this.bank_num = bank_num;
        this.dept = dept;
    }

    // VARIABLES
    private int id;
    private String name;
    private String address;
    private String nin;
    private float salary;
    private String bank_num;
    private String dept;

    // GETTER AND SETTERS
    public int getId() { return id; }
    public void setId(int newid){ this.id=newid; }

    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }

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

    // JSON OBJECT
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
    public float calcPay(){
        double pay = Math.round((salary/12) * 100.0) / 100.0;
        return (float)pay;
    }

    public String toString(){
        return "Employee: "
                + this.getName() + ", "
                + this.getAddress() + ", £"
                + this.getSalary() + ", "
                + this.getNin() + ", "
                + this.getBank_num();
    }
}
