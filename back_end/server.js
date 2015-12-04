var express = require('express');
var beautify = require('js-beautify').js_beautify;
var fs = require('fs');
var app = express();
// To increase scaleability, we're going to have an array of eSports that
// we will increase depending on parameters passed by api requests. For example, 
// if a dev wants to just get dota matches, then he/she will say /getLiveMatches?sport=dota
var dotaProvider = {};
// For the lulz. Won't be implemented this semester. Just showing that it COULD... happen.
var lolProvider = {};
// The array of Provider Objects..
var sportsProviders = [];

scoreboardjson = ""
fs.readFile('scoreboard.json', 'utf8', function (err,data) {
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

var server = app.listen(3000, function () {
	var host = server.address().address;
	var port = server.address().port;

	console.log('Example app listening at http://%s:%s', host, port);

	// Grab the liveleaguegames data from 
});


