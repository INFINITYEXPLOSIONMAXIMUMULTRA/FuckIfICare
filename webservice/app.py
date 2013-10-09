from flask import Flask,jsonify
from flask.ext.sqlalchemy import SQLAlchemy
from sqlalchemy.orm import sessionmaker
from sqlalchemy import *

app = Flask(__name__)
#app.config.from_object('config')
db = SQLAlchemy

engine = create_engine("mysql+mysqldb://projectuser:projectmysqlUser@localhost:3306/project")
Session = sessionmaker(bind = engine)
session = Session()


@app.route('/api/v1.0/location/nametocoordinates/<string:name>', methods=["GET"])
def getCoordinates(name):
	results = engine.execute("select * from location where name = %s",[name])
	returns = ''
	for i in results:
		returns += str(i)
	return returns

@app.route('/api/v1.0/location/getevents/latitude/<string:lat>/longitude/<string:lon>', methods=['GET'])
def geteventswithinhour(lat, lon):
	"""
		Returns a list of events that are occurring close to the user's current location
		within 3 hours.

		@param lat A string that is 8/9 digits long representing the user's current latitude
		@param lon A string that is 8/9 digits long representing the user's current longitude
	"""
	return_structure = {}
	if(lat[:1] == "-"):
		lat = lat[:-8]
	lat_degrees = lat[:2]
	lat_minutes = lat[2:4]
	lat_seconds = int(lat[4:6])
	lat_micro_seconds = int(lat[6:8])

	if(lon[:1] == "-"):
		lon = lon[:-8]
	lon_degrees = lon[:2]
	lon_degrees = lon[2:4]
	lon_seconds = lon[4:6]
	lon_micro_seconds = lon[6:8]


	events = session.query("l.id,l.name,e.event_name").from_statement("select l.id,l.name,e.event_name from location l \
							inner join event e on l.id = e.lid and e.date = curdate() where lat_positive = true and \
							(lat_sec + 5) >= :latsec and (lat_sec - 5) <= 74 and \
							(lon_sec + 5) > 78 and (lon_sec - 5) < 78 and \
							lon_micro + 10 > 100 and lon_micro - 10 < 100").\
							params(latsec = lat_seconds).all()

	return str(events)


if __name__ == "__main__":
	app.run(debug=True)