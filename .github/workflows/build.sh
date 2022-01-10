#!/usr/bin/env bash
set -e 
cd $GITHUB_WORKSPACE 
echo "Starting build"
mkdir -p $HOME/.m2/
cp .ci.settings.xml $HOME/.m2/settings.xml
docker pull mongo:5.0.3
docker pull testcontainers/ryuk:0.3.0
docker pull alpine:3.5
mvn -e -f $GITHUB_WORKSPACE/pom.xml spring-javaformat:apply  verify   deploy
echo "Stopping build"