#!/bin/bash

response=$(curl --write-out %{http_code} --silent localhost:8080/userapp/users/1)
status_code=$(echo "$response" | grep -oP '(200)')
case "$status_code" in
	200) echo 'curl success!'
		 ;;
	  *) echo 'curl failed!' \
	  	 && service webserver stop \
	  	 && rm /home/csse/YoloSwagSWS/bin/plugin/UsersPlugin*.jar \
	  	 && service webserver start \
	  	 && echo 'removed the new plugin jar and restarted the service'
	  	 exit 1
	  	 ;;
esac
