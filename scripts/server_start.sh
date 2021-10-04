#!/usr/bin/env bash
cd /home/ec2-user/server
sudo java -jar -Dserver.port=8761 service-registry-0.0.1-SNAPSHOT.jar
sudo java -jar -Dserver.port=9191 api-gateway-0.0.1-SNAPSHOT.jar
sudo java -jar -Dserver.port=4502 portal-service-0.0.1-SNAPSHOT.jar
sudo java -jar -Dserver.port=9001 listing-service-0.0.1-SNAPSHOT.jar
sudo java -jar -Dserver.port=9002 user-service-0.0.1-SNAPSHOT.jar