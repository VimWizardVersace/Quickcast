var mongoose = require('mongoose'), 
	Schema   = mongoose.Schema;
//The player Model (NOT A SCHEMA ITS NAHT A SCHEEEMA)
// var Player = require('./Player.js');
//Schema for the mongoose model.
var TeamSchema = new Schema({
	name: String,
	players: [{type: Schema.Types.ObjectId, ref: 'Player'}],
	serieswins: Number,
	serieslosses: Number,
	gamewins: Number,
	gamelosses: Number
	//TODO: Frequent Bans, Rival Teams.
});

module.exports = mongoose.model('Team', TeamSchema);