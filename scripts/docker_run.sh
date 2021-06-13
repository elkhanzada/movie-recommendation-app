#!/bin/bash

docker build -t $1 .
docker run -d -p 8080:8080 $1
