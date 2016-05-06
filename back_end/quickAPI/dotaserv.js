var mongo = require('mongodb');
var monk = require('monk');
var db = monk('localhost/quickDB');
var http = require('http');

var result = {};

//Interval of request. Please keep this large until we're in production <3
//Value is in ms
var interval = 10000;

// TODO: Replace path with keys from quickdb.conf
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
		var games_json = {};

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

		// copy to a json obc with the keys being lobby ID
		for(var i = 0; i < games.length; i++){
			games_json[games[i].lobby_id] = games[i];
		}

		// Great. Now games should contain only teams that we actually care about! What a concept.
		// Now we go and update the livegame object.
		var collection = db.get('Live');
		/*
			First parameter: Objects to update (There should only ever be one Live object)
			Second: Set the dotagames object equal to what we found!
		*/

		/*instead of holding as an array, make an object whose properties are lobby_id */
		var update_with = {};
		
		/* iterate through the games json, basically restructuring it into update_with*/
		for (var lobby_id in games_json) {
			update_with[lobby_id] = games_json[lobby_id];
		}

		/* keep the dotagames array, allows us to have a lolgames and csgames array in the future */
		collection.update({}, {$set : {dotagames: update_with}}, function(err, doc){
			if(err){
				console.log("Woah there! error!");
			} else {
				console.log("Updated succesfully");
			}
		} );

		var lastqueried = Date.now();


	});
}
function getData(){
	http.request(options, updateDB).end();

}


//Initial request for testing purposes.
getData();
var collection = db.get('Live');
var history = db.get('History');
history.insert({});
collection.insert({});
setInterval(function(){
	getData();

}, interval);
