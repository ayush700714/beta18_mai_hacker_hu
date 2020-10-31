const http = require('http');
const express = require('express');
const { env } = require('process');
var nodemailer = require('nodemailer');
const MessagingResponse = require('twilio').twiml.MessagingResponse;
const request = require('request')
const geocode = require('./utlis/geocoding')

const app = express();

var accountSid = "AC76432d52b7c513e4b39dfc54eb93b355";
var authToken = '3be08579259e0698be536ec47db66fb2';

var twilio = require("twilio");
var client = new twilio(accountSid, authToken);





app.get('/sms', (req, res) => {

  console.log("recived");

  var sourceAddress = req.query.sourceAddress;
  var destinationAddress = req.query.destinationAddress;
  var toNumber = req.query.phoneNumber;
  console.log(sourceAddress , destinationAddress , toNumber);
  
  geocode(sourceAddress, (erro, { longitute : longituteS,  latitude : latitudeS } = {}) => {
    if (erro) {
      return res.send({
        error: 'Try another name pls!'
      })
    }
    else {
      geocode(destinationAddress, (erro, { longitute : longituteD, latitude : latitudeD } = {}) => {
        if (erro) {
          return res.send({
            error: 'Try another name pls!'
          })
        }
        else {

          var url = "https://router.project-osrm.org/route/v1/driving/" + longituteS + "," + latitudeS + ";" + longituteD + "," + latitudeD + "?geometries=geojson&alternatives=true&steps=true&generate_hints=false"
          console.log(url);
          request({ url: url, json: true }, (error, response = {}) => {
            if (error) {
              console.log("map error");
              req.send("404");

            }
            else {


              try {
                var data = response.body.code
                client.messages
                  .create({
                    body: data,
                    to:"+916265070160",
                    from: "+12562517792",
                  })
                  .then((message) => { console.log("sent"); res.send(response.body); });
              }
              catch (e) {
                console.log("twilio error" + e.message);
                req.send("404");
              }

              console.log(response.body);
            }
          });

        }

      });

    }

  });




});

http.createServer(app).listen(3000, () => {
  console.log('Express server listening on port 1337');
});
