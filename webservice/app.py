from flask import Flask,jsonify
from flask.ext.sqlalchemy import SQLAlchemy
from sqlalchemy.orm import sessionmaker
from sqlalchemy import *
import json

app = Flask(__name__)
db = SQLAlchemy

engine = create_engine("mysql+mysqldb://projectuser:projectmysqlUser@localhost:3306/project")
Session = sessionmaker(bind = engine)
session = Session()


@app.route('/api/v1.0/location/nametocoordinates/<string:name>', methods=["GET"])
def get_coordinates(name):
	"""
		Gives the user the latitude and longitude coordinates for the name of a building
		
		Returns as a json blob consisting of the following fields:
			name: The name that the user supplied
			latitude: the latitude coordinates the user supplied
			longitude: the longitude coordinates the user supplied
	"""
	res = session.query("name","lat_positive","lat_deg", \
						"lat_min","lat_sec","lat_micro", \
						"lon_positive","lon_deg","lon_min", \
						"lon_sec","lon_micro").\
			from_statement("select name,lat_positive,lat_deg,lat_min, \
							lat_sec,lat_micro,lon_positive,lon_deg,lon_min, \
							lon_sec,lon_micro from location where name = :named").\
		params(named = name).all()
	ret_dict = {}
	latitude = ''
	longitude = ''
	if(res[0][1] == False):#checking for negative latitude
		latitude += '-'
	latitude += str(res[0][2])#degrees of latitude
	latitude += "."
	latitude += str(res[0][3])#minutes of latitude
	latitude += str(res[0][4])#seconds of latitude
	latitude += str(res[0][5])#microseconds of latitude

	if(res[0][6] == False):#checking for negative longitutde
		longitude += '-'

	longitude += str(res[0][7])#degrees of longitude
	longitude += "."
	longitude += str(res[0][8])#minutes of longitude
	longitude += str(res[0][9])#seconds of longitude
	longitude += str(res[0][10])#microseconds on longitude
	ret_dict['name'] = res[0][0]
	ret_dict['latitude'] = latitude
	ret_dict['longitude'] = longitude
	return jsonify(ret_dict)

@app.route('/api/v1.0/location/getevents/latitude/<string:lat>/longitude/<string:lon>', methods=['GET'])
def get_events_within_hour(lat, lon):
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

	results = session.query("id","name","event_name","start_time").from_statement("select l.id,l.name,e.event_name,e.start_time \
						from location l \
						inner join event e on l.id = e.lid and e.date = curdate() where lat_positive = true and \
						(lat_sec+5) >= :latsec and (lat_sec-5) <= :latsec2 and \
						(lon_sec+5)> :lonsec and (lon_sec-5)< :lonsec and \
						(lon_micro+10)> :lonmicro and (lon_micro-10)< :lonmicro").\
		params(latsec = lat_seconds,latsec2 = lat_seconds,lonsec = lon_seconds,lonmicro = lon_micro_seconds).all()

	ret_dict = {}
	events = []
	for event_tuple in results:
		temp = {}
		temp['building_name'] = event_tuple[1]
		temp['event_name'] = event_tuple[2]
		temp['start_time'] = str(event_tuple[3])
		events.append(temp)
	ret_dict['events'] = events
	return jsonify(ret_dict)

@app.route('/api/v1.0/location/geteventsfornexthours/<int:hours>', methods=['GET'])
def get_events_for_next_hours(hours):
	"""
		Returns all of the events for the next <hours> number of hours

		@Param hours: the number of hours into the future to search for events for
		@return json blob with following fields:
			events: array of json objects consisting of
				event_name: the name of the event
				start_date: the date of the event
				start_time: the time when the event starts
	"""
	results = session.query("event_name","date","start_time").\
	from_statement("select event_name,date,start_time \
			from event where date = curdate() and timediff(start_time,now())< :numhours").\
	params(numhours = int(hours)).all()
	#return str(results)
	ret_dict = {}
	events = []
	for event_tuple in results:
		temp = {}
		temp['event_name'] = event_tuple[0]
		temp['start_date'] = str(event_tuple[1])
		temp['start_time'] = str(event_tuple[2])
		events.append(temp)
	ret_dict['events'] = events
	return jsonify(ret_dict)


if __name__ == "__main__":
	app.run(debug=True)