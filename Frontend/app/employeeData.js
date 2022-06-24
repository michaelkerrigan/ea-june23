
const axios = require('axios');

exports.getEmployees = async () => {
    let employees = []
    try {
        const empResponse = await axios.get('http://localhost:8080/api/employees')
        for (const data of empResponse.data) {
            employees.push(data)
        }
    } catch (e) {
        return new Error('Could not get employees')
    }

    return employees;
}

exports.getSalesEmployees = async () => {
    let employees = []
    try {
        const empResponse = await axios.get('http://localhost:8080/api/sales-employees')
        for (const data of empResponse.data) {
            employees.push(data)
        }
    } catch (e) {
        return new Error('Could not get employees')
    }

    return employees;
}


exports.addEmployee = async (newEmployee) => {
    let results = await axios.post('http://localhost:8080/api/employee', newEmployee)
    return results.data;
}

exports.addSalesEmployee = async (newEmployee) => {
    let results = await axios.post('http://127.0.0.1:8080/api/sales_employee', newEmployee)
    return results.data;
}