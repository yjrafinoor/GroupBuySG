#!/usr/bin/env bash
cd /home/ec2-user/server
sudo java -jar service-registry-0.0.1-SNAPSHOT.jar &
sudo java -jar api-gateway-0.0.1-SNAPSHOT.jar &
sudo java -jar portal-service-0.0.1-SNAPSHOT.jar &
sudo java -jar listing-service-0.0.1-SNAPSHOT.jar &
sudo java -jar user-service-0.0.1-SNAPSHOT.jar &