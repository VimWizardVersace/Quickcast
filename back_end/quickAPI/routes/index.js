var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');

var Team = require('../models/Team.js');
var Live = require('../models/Live.js');
var Player = require('../models/Player.js');


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/live', function(req, res, next){
	//Note: There should only ever be one livegame object. Select only csgogame, lolgames, and dotagames.
	Live.findOne({}, {csgogames : 1, lolgames : 1, dotagames : 1, _id : 0}, function(err, livegames){
		if(err) return next(err);
		var simpleresponse = [];
		//This is how we should traverse
		for(var i=0; i<livegames.dotagames.length; i++){
			var currentgame = livegames.dotagames[i];
			if(currentgame.scoreboard != null && currentgame.dire_team != null && currentgame.radiant_team != null){
				var totalgames;
				//Series type 0 = best of 1, and so on.
				if(currentgame.series_type = 0){
					totalgames = 1;
				} else if (currentgame.series_type = 1){
					totalgames = 3;
				} else if (currentgame.series_type = 2){
					totalgames = 5;
				}
				//See the help document on my blog for demystifying the dota 2 web api.
				simpleresponse.push({
					"matchid" : currentgame.match_id, 
					"sport" : "DOTA2",
					"series" : [currentgame.dire_series_wins, currentgame.radiant_series_wins,totalgames],
					"score" : [currentgame.scoreboard.dire.score, currentgame.scoreboard.radiant.score],
					"teams" : [currentgame.dire_team.team_name, currentgame.radiant_team.team_name]
				});
			}
		}

		res.json(simpleresponse);
	});
});
module.exports = router;
