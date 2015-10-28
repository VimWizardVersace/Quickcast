var express = require('express');
var app = express();
// To increase scaleability, we're going to have an array of eSports that
// we will increase depending on parameters passed by api requests. For example, 
// if a dev wants to just get dota matches, then he/she will say /getLiveMatches?sport=dota
var dotaProvider = {"match1" : 10};
// For the lulz. Won't be implemented this semester. Just showing that it COULD... happen.
var lolProvider = {};
// The array of Provider Objects..
var sportsProviders = {"dota" : dotaProvider, "lol" : lolProvider};
app.param('sport', function(req, res, next, sport){
	next();
});
//'/' is the root directory. 
app.get('/', function (req, res) {
	res.send('Hello World!');
});

app.get('/getLiveMatches/dota', function(req, res){
	res.send(sportsProviders["dota"]);
});
function getDotaMatchData(){
	console.log("getMatchData called. Time to do an http request.");
}
var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;
	var date = new Date();
	console.log(start_time);
	console.log('Example app listening at http://%s:%s', host, port);
	// Grab the liveleaguegames data from the dota 2 web api every 30 seconds or so.

	
});
// 	var start_time = date.getTime();

// while(true){
// 	date = new Date();
// 	if(date.getTime()-start_time >= 10000){
// 		console.log("Getting a dota 2 webAPI request...");
// 		getDotaMatchData();
// 		start_time = date.getTime();
// 	}
// }