FROM ubuntu:20.04
ENV TZ=America/Los_Angeles
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get update && apt-get install -y build-essential
RUN apt-get install -y openjdk-11-jdk git maven curl wget
RUN wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | apt-key add -
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-4.4.list
RUN apt-get update
RUN apt-get install -y mongodb-org
RUN wget http://mirror.23media.de/apache/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar xvfz tomcat.tar.gz
RUN mkdir /usr/local/tomcat
RUN cp -Rv /tmp/apache-tomcat-8.5.30/* /usr/local/tomcat/
COPY group9.war /usr/local/tomcat/webapps/
WORKDIR /root/project
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64/
COPY run.sh .
RUN mkdir -p /data/db
RUN chmod +x /usr/local/tomcat/bin/catalina.sh
RUN echo 'mongod --fork --dbpath=/data/db --logpath=/data/mongo.log &' >> /start.sh
RUN echo '/bin/bash /usr/local/tomcat/bin/catalina.sh run' >> /start.sh
RUN echo '/bin/bash' >> /start.sh
RUN chmod a+x /start.sh

EXPOSE 27017 8080

CMD ["/start.sh"]

ENTRYPOINT ["/bin/bash"]

