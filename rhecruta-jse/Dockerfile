FROM ubuntu:14.04

RUN apt-get update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install oracle-java8-installer -y
RUN apt-get install oracle-java8-set-default
#COPY target/rhecruta-jse-jar-with-dependencies.jar /usr/local/rhecruta-jse.jar
#RUN ["java", "-jar", "/user/local/rhecruta-jse.jar"]
ADD target/rhecruta-jse-jar-with-dependencies.jar /usr/local
#ENTRYPOINT ["java", "-jar", "/usr/local/rhecruta-jse-jar-with-dependencies.jar"]
#CMD [""]
#CMD ["/bin/bash"]


#java -jar /usr/local/rhecruta-jse-jar-with-dependencies.jar