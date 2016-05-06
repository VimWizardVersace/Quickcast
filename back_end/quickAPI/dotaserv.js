var mongo = require('mongodb');
var monk = require('monk');
var db = monk('localhost/quickDB');
var http = require('http');

var result = {};

//Interval of request. Please keep this large until we're in production <3
//Value is in ms
var interval = 80000;


var options = {
	host: 'https://api.steampowered.com',
	path: '/IDOTA2Match_570/GetLiveLeagueGames/v0001/?key=4D993671C8B8DA5C9A9F6D4377D6167C'
}

var options = {
	host: 'api.steampowered.com',
	path: '/IDOTA2Match_570/GetLiveLeagueGames/v0001/?key=4D993671C8B8DA5C9A9F6D4377D6167C'
}

updateDB = function(response) {
	var string = '';
	//The chunk of data that is being read is the only function being passed in this callback.
	//NOTE: THIS IS AN ASYNCHRONOUS METHOD!!! chunk is NOT all of the data! kthxbye.
	response.on('data', function(chunk){
		string+=chunk;
		// console.log(chunk);
	});

	//Now we can send the data to the database. Good stuff, man!
	response.on('end', function(){
		data = JSON.parse(string);
		games = data.result.games;
		// Here we will ignore all games that are not of league_tier 3.
		for(var i = 0; i < games.length; i++){
			// console.log(games[i]);
			if(games[i].league_tier < 1){
				//Remove 1 element from index i
				games.splice(i, 1);
				//Standard iteration procedure :) Because we deleted, we need to backtrack a little.
				i--;
			}
		}
		// Great. Now games should contain only teams that we actually care about! What a concept.
		// Now we go and update the livegame object.
		var collection = db.get('Live');
		/*
			First parameter: Objects to update (There should only ever be one Live object)
			Second: Set the dotagames object equal to what we found!
		*/
		collection.update({}, {$set : {dotagames : games}}, function(err, doc){
			if(err){
				console.log("Woah there! error!");
			} else {
				console.log("Updated succesfully");
			}
		});
		var lastqueried = Date.now();


	});
}
function getData(){
	http.request(options, updateDB).end();

}


//Initial request for testing purposes.
getData();
setInterval(function(){
	getData();

}, interval);
