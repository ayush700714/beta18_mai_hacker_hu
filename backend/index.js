const express = require("express");
const http = require('http');

const MessagingResponse = require('twilio').twiml.MessagingResponse;


const app = express();





app.post('/sms', (req, res) => {
    var accountSid = "AC989390046e3a31f6507e999541cd9cbf";
    var authToken = '8631af2e0369db126db5c950133dda00';

    var twilio = require("twilio");
    var client = new twilio(accountSid, authToken);

    client.messages
        .create({
            body: "Hello from Node",
            to: "+916200207948",
            from: "+13344241589",
        })
        .then((message) => res.send(`The message with id: ${message} was sent!`));
});

app.get('/sms', (req, res) => {
    res.send("Port exposed successfully");
    console.log("successfull");
});

http.createServer(app).listen(5000, () => {
    console.log('Express server listening on port 5000');
});