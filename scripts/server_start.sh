#!/usr/bin/env bash
cd /home/ec2-user/server
sudo nohup java -jar service-registry-0.0.1-SNAPSHOT.jar > service.out 2>&1 &
#sleep 10 
#sudo nohup java -jar api-gateway-0.0.1-SNAPSHOT.jar > gateway.out 2>&1 &
#sleep 10
#sudo nohup java -jar portal-service-0.0.1-SNAPSHOT.jar > portal.out 2>&1 &
#sleep 10
#sudo nohup java -jar listing-service-0.0.1-SNAPSHOT.jar> listing.out 2>&1 &
#sleep 10
#sudo nohup java -jar user-service-0.0.1-SNAPSHOT.jar > user.out 2>&1 &