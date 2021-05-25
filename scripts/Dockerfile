FROM ubuntu:20.04
ENV TZ=America/Los_Angeles
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get update && apt-get install -y build-essential
RUN apt-get install -y openjdk-11-jdk 
RUN apt-get install -y git 
RUN apt-get install -y maven
RUN apt-get install -y curl
WORKDIR /root/project
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64/
COPY run.sh .
