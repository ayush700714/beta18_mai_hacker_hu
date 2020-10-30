const express = require("express");
const http = require('http');

const MessagingResponse = require('twilio').twiml.MessagingResponse;


const app = express();





app.get('/sms', (req, res) => {
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

app.post('/sms', (req, res) => {
    const twiml = new MessagingResponse();

    twiml.message('The Robots are coming! Head for the hills!');
    console.log(req.body)
    res.writeHead(200, { 'Content-Type': 'text/xml' });
    res.end(twiml.toString());
});

http.createServer(app).listen(5000, () => {
    console.log('Express server listening on port 5000');
});