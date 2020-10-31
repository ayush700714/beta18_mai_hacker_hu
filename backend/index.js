const http = require('http');
const express = require('express');
const { env } = require('process');
var nodemailer = require('nodemailer');
var timeout = require('connect-timeout')
const MessagingResponse = require('twilio').twiml.MessagingResponse;
const request = require('request')
const geocode = require('./utlis/geocoding')

const reverseGeoCoding = require('./utlis/reverseGeocoding')

const app = express();
app.use(express.json()); 
  
 
app.use(express.urlencoded({ extended: true }));
var accountSid = "AC76432d52b7c513e4b39dfc54eb93b355";
var authToken = '3be08579259e0698be536ec47db66fb2';

var twilio = require("twilio");
var client = new twilio(accountSid, authToken);





app.post('/sms',timeout('100s'), (req, res) => {

  console.log("recived");
  
  console.log(req.body)
  // var keyToIdentify = req.body.key;
  var longituteS = req.body.log;
  var latitudeS = req.body.lat;
  var destinationAddress = req.body.destination;
  var toNumber = req.body.phoneNumber;
  // console.log(destinationAddress , toNumber);
  
  // geocode(sourceAddress, (erro, { longitute : longituteS,  latitude : latitudeS } = {}) => {
  //   if (erro) {
  //     return res.send({
  //       error: 'Try another name pls!'
  //     })
  //   }
  //   else {
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


              // try {
              //   var data = response.body.code
              //   client.messages
              //     .create({
              //       body: data,
              //       to:"+916265070160",
              //       from: "+12562517792",
              //     })
              //     .then((message) => { console.log("sent"); res.send(response.body); });
              // }
              // catch (e) {
              //   console.log("twilio error" + e.message);
              //   req.send("404");
              // }

              console.log(response.body);
///////////////////////////////kunal code/////////////////////////////////////////////////////////////

            var sed_res = ""
            data = response.body
            // var obj = JSON.parse(data)
            var obj = data
            console.log(typeof(obj));
            var y = obj.routes[0].legs[0];
            var len = Object.keys(y['steps']).length;
            for (var i = 0; i < len; i++) {
                
                console.log(sed_res)
                console.log(i)
                if(i%3!=0)
                {
                  sed_res += "{\n" + "driving_side = " + y['steps'][i].driving_side + "\n" + "Duration = " + y['steps'][i].duration + "\n" + "Distance = " + y['steps'][i].distance + "\n" + "Mode = " + y['steps'][i].mode + "\n" + "Turn = " + y['steps'][i]['maneuver'].modifier + "\n}" + '\n'
               
                }else{
                  sed_res += "{\n" + "driving_side = " + y['steps'][i].driving_side + "\n" + "Duration = " + y['steps'][i].duration + "\n" + "Distance = " + y['steps'][i].distance + "\n" + "Mode = " + y['steps'][i].mode + "\n" + "Turn = " + y['steps'][i]['maneuver'].modifier + "\n}" + '\n'
                    client.messages
                    .create({
                        body: sed_res,
                        to: "+916265070160",
                        from: "+12562517792",
                    }).then((message) => {
                        console.log("Message sent successfully!!")
                    })
                  sed_res = "";
                }


            }








            }
          });

        }

    //   });

    // }

  });




});

http.createServer(app).listen(3000, () => {
  console.log('Express server listening on port 1337');
});