// const mysql = require('mysql');
// const dbconfig = require('./dbconfig.json');
// const util = require('util');
const axios = require('axios');

exports.getEmployees = async () => {
    let employees = []
    try {
        const empResponse = await axios.get('http://localhost:8080/api/employees')
        //console.log(empResponse.data);
        for (const data of empResponse.data) {
            employees.push(data)
        }
    } catch (e) {
        console.log(e);
        return new Error('Could not get employees')
    }

    return employees;
}


exports.addEmployee = async (newEmployee) => {
    let results = await axios.post('http://localhost:8080/api/employee', newEmployee)
    //console.log(results.data);
    return results.data;
}

exports.addSalesEmployee = async (newEmployee) => {
    let results = await axios.post('http://127.0.0.1:8080/api/sales_employee', newEmployee)
    //console.log(results.data);
    return results.data;
}