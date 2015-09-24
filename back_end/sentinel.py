import requests
import json
import worker_api
import time
from threading import Thread

# invoke the GetLiveLeagueGames webAPI call, and retrieve the new json object
def make_dota2_api_call():
	print "Making GetLiveLeagueGames call..."
	r = requests.get('https://api.steampowered.com/IDOTA2Match_570/GetLiveLeagueGames/v0001/?key=4D993671C8B8DA5C9A9F6D4377D6167C')
	
	# their server broke
	if (r.status_code != 200):
		return None

	# convert json object to dictionary and return
	return json.loads(r.text)

# flask=False means that it isn't being returned to a Flask object.
# flask=True means that the return value will be live.
def print_all_matches(flask=True):
	with open("scoreboard.json", 'r') as scoreboard:
		if flask:
			return json.loads(scoreboard.read())
		else:
			for line in scoreboard.read():
				print line


def update_scoreboard(json_obj):
	# make it a dictionary first.  dictionaries are easily converted to json objects
	scoreboard_json = dict()

	# make an array of internal objects, each representing a match
	scoreboard_json['matches'] = []
	for index, match in enumerate(json_obj['result']['games']):

		scoreboard_json['matches'].append(dict())
		scoreboard_json['matches'][index]['match_id'] = match['match_id']

		scoreboard_json['matches'][index]['tournament'] = None

		scoreboard_json['matches'][index]['teams'] = []

		# check if the API call returned the names of the radiant team, dire team, and their scores,
		# if not, leave them as unknowns
		try:
			scoreboard_json['matches'][index]['teams'].append(match['radiant_team']['team_name'])
		except KeyError:
			scoreboard_json['matches'][index]['teams'].append("Unknown team")
		try:
			scoreboard_json['matches'][index]['teams'].append(match['dire_team']['team_name'])
		except KeyError:
			scoreboard_json['matches'][index]['teams'].append("Unknown team")


		# incomplete
		scoreboard_json['matches'][index]['match_scores'] = []		

		scoreboard_json['matches'][index]['game_scores'] = []
		try:
			scoreboard_json['matches'][index]['game_scores'].append(match['scoreboard']['dire']['score'])
			scoreboard_json['matches'][index]['game_scores'].append(match['scoreboard']['radiant']['score'])
		except KeyError:
			scoreboard_json['matches'][index]['game_scores'] = [0,0]

	# convert the dictionary to a json and write it to scoreboard.json
	with open("scoreboard.json", 'w') as scoreboard:
		s = json.dumps(scoreboard_json, sort_keys=True, indent=4, separators=(',', ": "))
		scoreboard.write(s)


# wildly incomplete. so far this function only updates scoreboard.json
def ingest_json_object(json_obj):
	update_scoreboard(json_obj)

# main is used for testing
if __name__ == "__main__":
	flask_thread = Thread(target=worker_api.run)
	flask_thread.start()
	while(1):
		current_games_json = make_dota2_api_call()
		ingest_json_object(current_games_json)
		print print_all_matches(True)
		time.sleep(5)