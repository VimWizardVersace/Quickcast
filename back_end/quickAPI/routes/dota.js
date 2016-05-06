var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');

var Team = require('../models/Team.js');
var Live = require('../models/Live.js');
var Player = require('../models/Player.js');

/* GET users listing. */
router.get('/', function(req, res, next) {
	res.send("This is the root. This does nothing. See documentation.");
});
router.get('/live', function(req, res, next){
	Live.find(function(err, livegames){
		if(err) return next(err);
		//There should ONLY ever be one live game object.
		var simpleresponse = [];
		for(var i=0; i<livegames[0].dotagames.length; i++){
			var currentgame = livegames[0].dotagames[i];
			if(currentgame.scoreboard != null && currentgame.dire_team != null && currentgame.radiant_team != null){
				var totalgames;

				if(currentgame.series_type = 0){
					totalgames = 1;
				} else if (currentgame.series_type = 1){
					totalgames = 3;
				} else if (currentgame.series_type = 2){
					totalgames = 5;
				}
				simpleresponse.push({
					"matchid" : currentgame.match_id, 
					"series" : [currentgame.dire_series_wins, currentgame.radiant_series_wins,totalgames],
					"score" : [currentgame.scoreboard.dire.score, currentgame.scoreboard.radiant.score],
					"teams" : [currentgame.dire_team.team_name, currentgame.dire_team.team_id, currentgame.radiant_team.team_name, currentgame.radiant_team.team_id],
					"duration" : currentgame.scoreboard.duration
				});
			}
		}

		res.json(simpleresponse);
	});
});
router.get('/live/:id', function(req, res, next){
	Live.find(function(err, livegames){
		if(err) return next(err);
		for(var i=0; i<livegames[0].dotagames.length; i++){
			if(livegames[0].dotagames[i].match_id==req.params.id){
				res.json(livegames[0].dotagames[i]);
			}
		}
	});
})
module.exports = router;
