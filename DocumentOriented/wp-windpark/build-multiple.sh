#!/bin/bash

rm -rf jar/ &>/dev/null
mkdir jar/

for i in `seq 1 5`;
do
    echo "server.port = 808$i" > src/main/resources/application.properties
    ./gradlew build
    ./gradlew jar

    mkdir "jar/app$i"
    cp build/libs/*.jar "jar/app$i/"
done
