Info :
Docker hub link for the image : https://hub.docker.com/r/amansha28/crypto-recommendation.jar
docker pull command : docker pull amansha28/crypto-recommendation.jar

Assumptions :
All the dates provided will be correct and following format : YYY-MM-DD

Scenarios Not covered :
GET : /norm-range/{date} - When we don't have price data for given date, we should be providing with suitable error
message to requester.