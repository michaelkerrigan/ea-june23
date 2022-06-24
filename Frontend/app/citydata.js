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

