The backend API is built using MongoDB, ExpressJS (A nodeJS framework). All requests to the quickAPI can be made by querying the host and a route. That is:

localhost:3000/live
will return an array for each of the supported esports in a JSON Object, like so:

{"csgogames":[],"lolgames":[],"dotagames":[]}
If you call

localhost:3000/dota/live
you will get only the live games for DotA.

Installation

Dependencies:

* node.js
* expressjs (npm install express)

$ npm install 
will get you started with all the right dependencies.

STARTING A MONGODB INSTANCE ON YOUR MACHINE:

Note: this can get complicated, and IDK how it works on mac/linux. HERE GOES!!!

Install MongoDB. Duh.
Go to your MongoDB installation.
Navigate to the bin folder in your MongoDB install path (On my machine, this is C:\Program Files\MongoDB\Server\3.0\bin)
Type the following

$ mongod --dbpath /path/to/back_end/quickAPI/data

e.g.:

$ mongod --dbpath B:\Documents\RCOS\back_end\quickAPI\data

In a different session, open up the MongoDB shell by typing:

$ mongo

you should see:

MongoDB shell version: 2.4.5
connecting to: test
simply type

use quickDB (The database can really be called whatever you want)
in the mongoDB shell.

NOW YOU'RE UP AND RUNNING

Running a local server

$ npm start
Will start the webAPI. Go to localhost:3000 and you're gucci mayne :)

This is all well and good, but it won't let us populate the database! Because we want to be able to run the webAPI and get data concurrently, we should be running a separate server on the same machine that will be populating our database. This file can be run by typing

$ node dotaserve.js
in your console.
