var express = require('express');
var beautify = require('js-beautify').js_beautify;
var fs = require('fs');
var app = express();
// To increase scaleability, we're going to have an array of eSports that
// we will increase depending on parameters passed by api requests. For example, 
// if a dev wants to just get dota matches, then he/she will say /getLiveMatches?sport=dota
var dotaProvider = {"match1" : 10};
// For the lulz. Won't be implemented this semester. Just showing that it COULD... happen.
var lolProvider = {};
// The array of Provider Objects..


scoreboardjson = ""
fs.readFile('resources/test/sampleDotaRequest.json', 'utf8', function (err,data) {
  if (err) {
    return console.log(err);
  }
  console.log("grabbed \"scoreboard.json\"")
  LiveMatchesJson = data;
});

//'/' is the root directory. 
app.get('/', function (req, res) {
	res.send('Hello World!');
});


app.get('/getLiveMatches/', function(req, res){
	res.send(beautify(LiveMatchesJson, {indent_size: 2}));
});
function getDotaMatchData(){
	console.log("getMatchData called. Time to do an http request.");
}
var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;
	// console.log(start_time);
	console.log('Example app listening at http://%s:%s', host, port);

	
});

// var date = new Date();
// last_called = date.getTime();
// // Grab the liveleaguegames data from the dota 2 web api every 30 seconds or so.
// while(true){

// 	if(Date.now() - last_called >= 5000){
// 		last_called = Date.now();
// 		console.log("Getting Live league games...");
// 	}
// }