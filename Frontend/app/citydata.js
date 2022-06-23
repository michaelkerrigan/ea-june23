const mysql = require('mysql');
const dbconfig = require('./dbconfig.json');
const util = require('util');
const db = wrapDB(dbconfig);
const axios = require('axios');

function wrapDB(dbconfig) {
    const pool = mysql.createPool(dbconfig)
    return {
        query(sql, args) {
            console.log("in query in wrapper")
            return util.promisify(pool.query)
                .call(pool, sql, args)
        },
        release() {
            return util.promisify(pool.releaseConnection)
                .call(pool)
        }
    }
}

// exports.getCitiesInCountry = async (countrycode) => { 
//     let cities = []
//     try {  
//         const cityResponse = await axios.post('https://countriesnow.space/api/v0.1/countries/population/cities', {"country": countrycode})
//         for (let data of cityResponse.data.data) {
//             cities.push(data)
//         }
//       } catch (e) {
//          return new Error('Could not get cities')
//       }
//       return cities;
//   }

// exports.getCities = async () => { 
//     let cities = []  
//       try {  
//         const cityResponse = await axios.get('https://countriesnow.space/api/v0.1/countries/population/cities')
//         for (let data of cityResponse.data.data) {
//             cities.push(data)
//         }
//       } catch (e) {
//         console.log(e);
//          return new Error('Could not get cities')
//       }

//       return cities;
//   }

getCitiesInCountry = async (countrycode) => {
    return await db.query(
        "SELECT id, name, countrycode, district, population"
        + " FROM city WHERE countrycode = ?",
        [countrycode])
}

exports.getCities = async () => {
    return await getCitiesInCountry('GBR');
}


exports.addCity = async (newCity) => {
    let results = await db.query('INSERT INTO city SET ?', newCity)
    return results.insertId;
}

