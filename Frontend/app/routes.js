const express = require('express')
const router = express.Router()
const citydata = require('./citydata.js')
const employeeData = require('./employeeData.js')
const userData = require('./userData.js')
const cookieParser = require('cookie-parser')

router.use(cookieParser());

router.get('/employees', async (req, res) => {
    if (req.cookies.Role === "HR"){
        res.render('employeesView', { employees: await employeeData.getEmployees() });
    } else {
        res.locals.errormessage = "Please sign in to a HR account";
        res.render("login");
    }
});

router.get('/sales-employees', async (req, res) => {
    if (req.cookies.Role === "HR"){
        res.render('salesEmployeeView', { employees: await employeeData.getSalesEmployees() });
    } else {
        res.locals.errormessage = "Please sign in to a HR account";
        res.render("login");
    }
});

router.get('/create-employee', function (req, res) {
     if (req.cookies.Role === "HR"){
    res.render('newEmployeeForm');
    } else {
        res.locals.errormessage = "Please sign in to a HR account";
        res.render("login");
    }
});

router.post('/create-employee', async (req, res) => {
    try {
        await employeeData.addEmployee(req.body);
        res.redirect('employees');
      } catch (e) {
        res.locals.errormessage = "Could not create employee";
        res.render('newEmployeeForm', req.body)
      }

});

router.get('/create-user', function (req, res) {
  res.render('newUserForm'); 
});


router.get('/add-sales-employee', function (req, res) {
    if (req.cookies.Role === "HR"){
        res.render('newSalesEmployeeForm');
    } else {
        res.locals.errormessage = "Please sign in to a HR account";
        res.render("login");
    }
});

router.post('/add-sales-employee', async (req, res) => {
  try {
    await employeeData.addSalesEmployee(req.body);
    res.redirect("employees");
  } catch (e) {
    res.locals.errormessage = "Could not create user";
    res.render('newSalesEmployeeForm', req.body)
  }
});

router.post('/create-user', async (req, res) => {
  try {
    await userData.addUser(req.body);
    res.render('newUserForm', req.body)
  } catch (e) {
    console.log(e)
    res.locals.errormessage = "Could not create user";
    res.render('newUserForm', req.body)
  }
});

router.get('/login-user', function (req, res) {
  res.render('login'); 
});

router.get('/home', function (req, res) {
  res.render('home');
});

router.post('/login-user', async (req, res) => {
  try {
    result = await userData.verifyUser(req.body);
    if ( result != "" ){
      res.cookie('Role', result.role)
      res.render('home', { employees: await employeeData.getEmployees() });
    } else {
      res.locals.errormessage = "Incorrect username or password";
      res.render('login', req.body)
    }
    
  } catch (e) {
    console.log(e)
    res.locals.errormessage = "Could not login user";
    res.render('login', req.body)
  }
});

module.exports = router
