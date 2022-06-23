const express = require('express')
const router = express.Router()
const citydata = require('./citydata.js')
const employeeData = require('./employeeData.js')
const userData = require('./userData.js')
const cookieParser = require('cookie-parser')

router.use(cookieParser());

// Add your routes here - above the module.exports line

// Run this code when a form is submitted to 'juggling-balls-answer'
// router.post('/juggling-balls-answer', function (req, res) {

//   // Make a variable and give it the value from 'how-many-balls'
//   var howManyBalls = req.session.data['how-many-balls']

//   // Check whether the variable matches a condition
//   if (howManyBalls == "3 or more") {
//     // Send user to next page
//     res.redirect('/juggling-trick')
//   } else {
//     // Send user to ineligible page
//     res.redirect('/ineligible')
//   }

// })


// router.get('/list-cities', async (req, res) => {
//   res.render('list-cities', { cities: await citydata.getCities() })
// });

// router.get('/list-cities-containing/:substr', (req, res) => {
//   res.render('list-cities', { namefilter: req.params.substr.toLowerCase(), cities: citydata.getCities() });
// });

// router.get('/addcity', async (req, res) => {
//   res.render('newcityform');
// });

// router.post('/addcity', async (req, res) => {
//   var city = req.body
//   // validate here
//   var countrycode = req.body.countrycode;
//   if (!countrycode.search(/^(GBR|IRL)$/)) {
//     let insertedKey = await citydata.addCity(req.body)
//     res.render('list-cities', { cities: await citydata.getCities() })
//   } else {
//     res.locals.errormessage = "Country code must be GBR or IRL"
//     res.render('newcityform', req.body)
//   }
// });

router.get('/employees', async (req, res) => {
    if (req.cookies.Role === "HR"){
        res.render('employeesView', { employees: await employeeData.getEmployees() });
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
    console.log(e)
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

//router.get('/setcookie', (req, res) => {
//    res.cookie('Role', req.body);
//    res.send('Cookie saved');
//    });
//
//router.get('/getcookie', (req, res) => {
//    //show the saved cookies
//    console.log(req.cookies)
//    res.send(req.cookies);
//});

router.post('/login-user', async (req, res) => {
  try {
    result = await userData.verifyUser(req.body);
    //console.log("result: " + result);
    if ( result != "" ){

      // STORE COOKIE SESSION DATA HERE (about user role)
      //result.role
      res.cookie('Role', result.role)

      console.log("COOKIES:")
      console.log(req.cookies.Role)

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
