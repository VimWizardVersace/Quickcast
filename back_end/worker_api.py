# the bulk of the RESTful API's functionality is in this file,
import flask
import sentinel

app = flask.Flask(__name__)
app.config['UPLOAD_FOLDER'] = '~/Quickcast/back_end'

@app.route('/boot', methods=['GET'])
def booted():
	return 'True'

@app.route('/recent_matches', methods=['POST', 'GET'])
def live_games():
	if flask.request.method == 'POST':
		print "Accessed POST method on /live_games"
		r = flask.request.params()
	else:
		print "Accessed GET method on /live_games"
		return flask.jsonify(sentinel.print_all_matches())

def run():
	app.run(host='0.0.0.0', port=5000, debug=False)