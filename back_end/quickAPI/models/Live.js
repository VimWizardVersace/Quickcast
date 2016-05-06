var mongoose = require('mongoose'), 
	Schema   = mongoose.Schema;

var LiveSchema = new Schema({
	dotagames : Array,
	lolgames: Array,
	csgogames: Array
});
//Third parameter is the collection name
module.exports = mongoose.model('Live', LiveSchema, 'Live');