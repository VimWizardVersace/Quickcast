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
	Live.findOne({}, {csgogames : 1, lolgames : 1, dotagames : 1, _id : 0}, function(err, live){
		if(err) return next(err);
		res.json(live);
	});
});
module.exports = router;
