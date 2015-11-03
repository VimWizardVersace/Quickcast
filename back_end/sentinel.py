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


def ingest_recent_matches(json_obj):
	# make it a dictionary first.  dictionaries are easily converted to json objects
	recent_matches = dict()

	# make an array of internal dictionaries, each representing a match
	recent_matches['matches'] = []
	for index, match in enumerate(json_obj['result']['games']):
		
		match_id = match['match_id']
		recent_matches[match_id] = {}
		# add the following attributes to the match object
		# NOTE: start_time will be negative if the match has not started
		recent_matches[match_id]["start_time"] = time.time() - match['scoreboard']['duration']
		recent_matches[match_id]["sport"] = "Dota2"
		recent_matches[match_id]['teams'] = []

		# check if the API call returned the names of the radiant team, dire team, and their scores,
		# if not, leave them as unknowns
		try:
			recent_matches[match_id]['teams'].append(match['radiant_team']['team_name'])
		except KeyError:
			recent_matches[match_id]['teams'].append("Unknown radiant team")
		try:
			recent_matches[match_id]['teams'].append(match['dire_team']['team_name'])
		except KeyError:
			recent_matches[match_id]['teams'].append("Unknown dire team")


		# incomplete
		recent_matches[match_id]['score'] = []		

		recent_matches[match_id]['series'] = []
		try:
			recent_matches[match_id]['score'].append(match['scoreboard']['radiant']['score'])
			recent_matches[match_id]['score'].append(match['scoreboard']['dire']['score'])
		except KeyError:
			recent_matches[match_id]['score'] = [-1,-1]

		try:
			recent_matches[match_id]["series"].append(match['radiant_series_wins'])
			recent_matches[match_id]["series"].append(match['dire_series_wins'])
			recent_matches[match_id]["series"].append(sum(recent_matches[match_id]["series"]))
		except KeyError:
			recent_matches[match_id]["series"] = [-1, -1, -1]

	# convert the dictionary to a json and write it to scoreboard.json
	with open("scoreboard.json", 'w') as scoreboard:
		s = json.dumps(recent_matches, sort_keys=True, indent=4, separators=(',', ": "))
		scoreboard.write(s)


# wildly incomplete. so far this function only updates scoreboard.json
def ingest_json_object(json_obj):
	ingest_recent_matches(json_obj)

# main is used for testing
if __name__ == "__main__":
	flask_thread = Thread(target=worker_api.run)
	flask_thread.start()
	while(1):
		current_games_json = make_dota2_api_call()
		if current_games_json:
			ingest_json_object(current_games_json)
		time.sleep(5)