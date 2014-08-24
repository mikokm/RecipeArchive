#!/bin/bash

stop-tomcat

mvn package || exit 1

rm -r ~/tomcat/webapps/EeppinenDrinkkiarkisto
cp -f target/EeppinenDrinkkiarkisto-0.0.1-SNAPSHOT.war ~/tomcat/webapps/EeppinenDrinkkiarkisto.war

start-tomcat
