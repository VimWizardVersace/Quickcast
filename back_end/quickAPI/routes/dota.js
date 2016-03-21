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
		res.json(livegames[0].dotagames);
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
