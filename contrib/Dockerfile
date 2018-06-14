# Cid docker image
#
# to use:
#
# 1. install docker, see docker.com
# 2. clone the git repo including this Dockerfile
# 3. build the container with ```docker build -t cid .```
# 4. run the created cid container with ```docker run -d -p 127.0.0.1:6789:7876 -p 6868:7874 cid```
# 5. inspect with docker logs (image hash, find out with docker ps, or assign a name)

FROM phusion/baseimage
# start off with standard ubuntu images

# Set local and enable UTF-8
ENV LANG C.UTF-8
ENV LANGUAGE C
ENV LC_ALL C.UTF-8

#java8
RUN sed 's/main$/main universe/' -i /etc/apt/sources.list
RUN apt-get update && apt-get install -y software-properties-common python-software-properties
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN apt-get install -y wget unzip
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer

# run and compile cid
RUN mkdir /cid
ADD . /cid
# repo has
ADD contrib/docker_start.sh /docker_start.sh
# set cid to listen on all interfaces
RUN echo 'cid.allowedBotHosts=*' >> /cid/conf/cid.properties
RUN echo 'cid.apiServerHost=0.0.0.0' >> /cid/conf/cid.properties
RUN chmod +x /docker_start.sh

RUN cd /cid; ./compile.sh
# both Cid ports get exposed
EXPOSE 7874 7876
CMD ["/docker_start.sh"]
