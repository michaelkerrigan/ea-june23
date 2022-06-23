const axios = require('axios');

getUsers = async () => {
    let users = []
    try {
        const userResponse = await axios.get('http://localhost:8080/api/users')
        //console.log(userResponse.data);
        for (const data of userResponse.data) {
            let buff = new Buffer.from(data.password);
            let pass = buff.toString('ascii');
            data.password = pass;
            users.push(data)
        }
    } catch (e) {
        console.log(e);
        return new Error('Could not get users')
    }
    //console.log(users)
    return users;
}

exports.addUser = async (newUser) => {
    let buff = new Buffer.from(newUser.password);
    newUser.password = buff.toString('base64');
    //console.log(newUser)
    let results = await axios.post('http://localhost:8080/api/user', newUser)
    //console.log(results.data);
    return results.data;
}

exports.verifyUser = async (user) => {
    let buff = new Buffer.from(user.password);
    user.password = buff.toString('base64');
    let result = await axios.post('http://localhost:8080/api/verify-user', user)
    return result.data;
}
