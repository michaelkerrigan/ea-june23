package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesEmployee extends Employee {
    private float CommRate;
    private int SalesTotal;

    public SalesEmployee(int employeeID, String name, String address, String nin, float salary, String bankAccount, String department, float commissionRate, int salesTotal) {
        super(employeeID,name, address, nin, salary, bankAccount, department);
        this.CommRate = commissionRate;
        this.SalesTotal = salesTotal;
    }

    public float getCommRate() {
        return CommRate;
    }
    public void setCommRate(float commRate) {
        CommRate = commRate;
    }

    public int getSalesTotal() {
        return SalesTotal;
    }
    public void setSalesTotal(int salesTotal) {
        SalesTotal = salesTotal;
    }


    @JsonCreator
    public SalesEmployee(@JsonProperty("name") String name,
                         @JsonProperty("address") String address,
                         @JsonProperty("salary") float salary,
                         @JsonProperty("nin") String nin,
                         @JsonProperty("bank_num") String bank_num,
                         @JsonProperty("commrate") float commrate,
                         @JsonProperty("salestotal") int salestotal)
    {
        super(name, address, salary, nin, bank_num);
        this.setCommRate(commrate);
        this.setSalesTotal(salestotal);
    }

    public float calcPay(){
        return (super.calcPay() + (SalesTotal*CommRate));
    }

    public String toString(){
       return super.toString() + ", "
               + this.getCommRate() + ", "
               + this.getSalesTotal();
    }
}