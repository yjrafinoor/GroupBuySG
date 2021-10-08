#!/usr/bin/env bash
cd /home/ec2-user/server
sudo nohup java -jar service-registry-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
#sleep 10 
#sudo nohup java -jar api-gateway-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
#sleep 10
#sudo nohup java -jar portal-service-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
#sleep 10
#sudo nohup java -jar listing-service-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
#sleep 10
#sudo nohup java -jar user-service-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &