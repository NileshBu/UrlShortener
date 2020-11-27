**README FOR URL PROCESSOR SERVICE**

URL Processor service is responsible for processing the Long URL and decoding them to a Unique
Pattern, and that pattern can then  be utilized to access the Long URL.

Use Swagger or Postman to make REST calls e.g. http://localhost:8080/'name of the specific endpoint'

**HOW TO USE**

1.) **WITH DOCKER**  : The image is available on Docker Hub. 
Pull Command  - ( docker pull bukane/urlshortner-api:final )
https://hub.docker.com/layers/bukane/urlshortner-api/final/images/sha256:8fb250cb1eb52c018c19f78ca73213a9ffdda473570e78347690369c58486725

2.) **WITH GIT** :
https://github.com/NileshBu/UrlShortener.git


**REQUIREMENTS**
Java 13 

**BASIC GUIDE TO THE SERVICE**
1.) Use the Endpoint (Create Short URL) http://localhost:8080/api/createShortUrl for generating a 
short unique pattern for Long Url.
**HOW TO USE**: Pass the Long URL which you want it to be shortened 
(for instance: https://www.playstation.com/en-ie/accessories/dualsense-wireless-controller/#charging-station)
in the body of http://localhost:8080/api/createShortUrl ; this will return the shortUrl Pattern in Response 
for the long url passed in the body.
This basically; returns a unique pattern for every long URL passed. Something like ('g2').

2.) After Hitting the first endpoint mentioned above, use the short pattern retrieved from above
endpoint to hit the second endpoint (Redirect Short URL)  : http://localhost:8080/api/g2. Pass the
retrieved unique identier as the path variable of the http://localhost:8080/api/g2; in this case
'g2' is the unique pattern generated.
Once you hit this, it will redirect you to the orginal Long URL for which the short URL was generated. 

3.) There is third endpoint (Get the API's Statistics) : http://localhost:8080/statistics/api_metrics.
This Endpoint gives the  details about all the endpoints available in this service.
For instance: It gives statistics of the Response Codes for each endpoint , along with the number of times
that particular Response code was returned for each specific endpoint.
