**README FOR URL PROCESSOR SERVICE**

URL Processor service is responsible for processing the Long URL and decoding them to a Unique
Pattern, and that pattern can then can be utilized to access the Long URL.

Use Swagger or Postman to make REST calls e.g. http://localhost:8080/'name of the specific endpoint'

**BASIC GUIDE TO THE SERVICE**
1.) Use the Endpoint (Create Short URL) http://localhost:8080/api/createShortUrl for generating a 
short unique pattern for Long Url.
How to Use: Pass the Long URL which you want to be shortened 
(for instance: https://www.playstation.com/en-ie/accessories/dualsense-wireless-controller/#charging-station)
in the body of http://localhost:8080/api/createShortUrl ; this will return the shortUrl Pattern in Response 
for the long url passed in the body.
This basically; returns a unique pattern for every long URL passed. Something like ('g2').

2.) After Hitting the first endpoint mentioned above, use the short pattern retrieved from above
endpoint to hit the second endpoint (Redirect Short URL)  : http://localhost:8080/api/g2. Pass the
retrieved unique identier as the path vairable of the http://localhost:8080/api/g2; in this case
g2 is reteived unique pattern.
Once you hit this, it will redirect you to the orginal Long URL for which the short URL was generated. 

3.) There is third endpoint (Get the API's Statistics) : http://localhost:8080/statistics/api_metrics.
This Endpoint gives the  details about the all the endpoints available in the service.
For instance: It gives statistics of the Response Codes for each endpoint , along the number of times
that particular code was retrieved for each endpoint.

**Swagger Docs**

http://localhost:8080/swagger-ui.html#/

**Endpoint**

1.)Create Short URL (Creating a short unique pattern for Long URL)  - POST REQUEST
ENDPOINT NAME - **/api/createShortUrl****
Postman URL - http://localhost:8080/api/createShortUrl - In Body : Original URL wanted to be shorten

2.)Redirect Short URL to Original Long URL  - GET REQUEST (REDIRECTED)
ENDPOINT NAME - **/api/{unique_pattern_retrieved}****
Postman URL - http://localhost:8080/api/g2

3.)Get the API's Statistics - GET REQUEST
ENDPOINT NAME - **statistics/api_metrics****
Postman URL - http://localhost:8080/statistics/api_metrics

