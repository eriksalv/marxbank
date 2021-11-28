#! /bin/bash

cd marxbank;
mvn install -DskipTests;
mvn spring-boot:run -pl backend &
cd marxbankvue && npm run serve;