#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/SunDialChat-1.0-SNAPSHOT.jar \
    kirill@192.168.64.128:/home/kirill/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa kirill@192.168.64.128 << EOF
pgrep java | xargs kill -9
nohup java -jar SunDialChat-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'