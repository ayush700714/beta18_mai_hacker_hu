const express = require("express");
const http = require('http');
const request = require('request')
const MessagingResponse = require('twilio').twiml.MessagingResponse;
var call_loc = require('./reverseGeocoding')
const bodyParser = require('body-parser')

const app = express();
app.use(bodyParser.urlencoded({ extended: true }))

// parse application/json
app.use(bodyParser.json())
var url = "https://router.project-osrm.org/route/v1/driving/77.41667,23.25;75.84722,22.72056?geometries=geojson&alternatives=true&steps=true&generate_hints=false"


var data = ""

var loc = ""
let cnt = 0

app.get('/sms', (req, res) => {
    var accountSid = "ACfc56f917172b053823a34ee3d29eb5f3";
    var authToken = 'd404ff0c033cd04f92823483a0689048';

    var twilio = require("twilio");
    var client = new twilio(accountSid, authToken);
    request({ url: url }, (error, response = {}) => {
        if (error) {
            console.log("Map Error")
        } else {
            data = response.body
            var obj = JSON.parse(data)
            console.log(typeof(obj));
            var y = obj.routes[0].legs[0];
            var len = Object.keys(y['steps']).length;
            // print(y)
            y['steps'].forEach(async(dat) => {
                // console.log(dat)
                var sed_res = "";
                sed_res += "{\n" + '"Duration" : ' + '"' + dat.duration + '"' + " ,\n" + '"Distance" : ' + '"' + dat.distance + '"' + " ,\n" + '"Mode" :  ' + '"' + dat.mode + '"' + " ,\n" + '"Turn" : ' + '"' + dat['maneuver'].modifier + '"' + ' ,\n'
                var longitute = dat['maneuver']['location'][0]
                var latitude = dat['maneuver']['location'][1]
                var url1 = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + longitute + "," + latitude + ".json?access_token=pk.eyJ1IjoiYWJoaXNoZWs5OTkiLCJhIjoiY2s3Z200dDhsMDEyZTNrcWRrZHpyanlrbCJ9.VP-Hevh7jsp0apFn3Y8KDA"
                request({ url: url1 }, (err, resp = {}) => {
                    if (err) {
                        console.log("Map Error")
                    } else {
                        data1 = resp.body
                        var ob = JSON.parse(data1)
                            // console.log(ob)
                        var place_name = ob.features[0].place_name
                        var text = ob.features[0].text
                    }
                    sed_res += '"Place_name" : ' + '"' + place_name + '"' + " ,\n" + '"Title" : ' + '"' + text + '"' + "\n}" + "\n"
                    console.log(sed_res)
                        // sed_res = String(sed_res)
                        // var send_data = JSON.parse(sed_res)
                    cnt++;

                    client.messages
                        .create({
                            body: sed_res,
                            to: "+918864084790",
                            from: "+15856721022",
                        }).then((message) => {
                            console.log("Message sent successfully!!")
                        })

                })



            })







        }


    })

    res.send("All the message was send successfully\n")



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