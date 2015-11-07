var mongoose = require('mongoose'), 
	Schema   = mongoose.Schema;
//TODO: Past teams.
var PlayerSchema = new Schema({
	firstname: String,
	lastname: String,
	//Alias = nickname, username, tag, whatever. 
	alias: String,
	//Total # of games
	games: Number,
	//Total # of series
	series: Number,
	team: {type: Schema.Types.ObjectId, ref: 'Team'}
});

module.exports = mongoose.model('Player', PlayerSchema);