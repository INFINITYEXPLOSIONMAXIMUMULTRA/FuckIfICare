import urllib
import urllib2
import string
import sys
from BeautifulSoup import BeautifulSoup

user_agent = "Mozilla/5 (Solaris 10) Gecko"
headers = {"user-Agent:":user_agent}

request = urllib2.Request("http://www.gatech.edu/calendar",headers = headers)
response = urllib2.urlopen(request)

the_page = response.read()
print the_page