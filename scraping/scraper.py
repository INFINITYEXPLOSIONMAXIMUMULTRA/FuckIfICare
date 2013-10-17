import urllib
import urllib2
import string
import sys
import re
from BeautifulSoup import BeautifulSoup

user_agent = "Mozilla/5 (Solaris 10) Gecko"
headers = {"user-Agent:":user_agent}#setting user agent in request

#opening the request with the specified headers
request = urllib2.Request("http://www.gatech.edu/calendar/upcoming",headers = headers)
response = urllib2.urlopen(request)

#reading the response and setting up BeautifulSoup
the_page = response.read()
soup = 	BeautifulSoup(the_page)

results = soup.findAll("div",{'class':re.compile('views-row')})
for result in results:
	#result encapsulates a div of all of the attributes we need 
	#to create a database entry for that particular event
	
	#the name that is required is in a link tag inside of h4 with no 
	#id nor class so more processing has to be done and this was easiest
	#way to guarantee accuracy
	nameformat = result.find('h4')
	event_name = nameformat.find('a').string

	#going to need to do some processing to extract the name from the inner tag
	time_object = result.find('span',{'class':'date-display-single'})
	#print dir(time_object)
	date = ''
	try:
		#going to extract the text from the time object and do some processing
		date = time_object.getText()
		date = str(date)#unicode to string
		date = date[5:]#getting rid of the day, and a space
		date = date.replace('/','-')#formatting date for mysql insertion
		times = date[date.find(' '):]#start and end times are after the date
		date = date[:date.find(' ')]

	except AttributeError:
		pass#I really don't want to do anything here

	location = result.findAll('p',{'class':'event-location'})
	print date

