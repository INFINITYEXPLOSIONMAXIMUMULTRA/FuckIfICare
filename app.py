from flask import Flask,jsonify
from flask.ext.sqlalchemy import SQLAlchemy
from sqlalchemy import *

app = Flask(__name__)
#app.config.from_object('config')
db = SQLAlchemy

engine = create_engine("mysql+mysqldb://projectuser:projectmysqlUser@localhost:3306/project")
engine.execute("select * from location")

@app.route('/')
def index():
	return "Hello world"


@app.route('/api/v1.0/location/nametocoordinates/<string:name>', methods=["GET"])
def getCoordinates(name):
	results = engine.execute("select * from location where name = %s",[name])
	returns = ''
	for i in results:
		returns += str(i)
	return returns

@app.route('/api/v1.0/location/geteventswithinhour/<int:hour>', methods=['GET'])
def geteventswithinhour(hour):
	return str(hour)

#@app.route('/api/v1.0/location')

if __name__ == "__main__":
	app.run(debug=True)