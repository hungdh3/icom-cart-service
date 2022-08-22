#!/usr/bin/env bash
echo "===== Starting Service ====="
java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${APPLICATION_PROFILE:-default} /home/jboss/service.jar