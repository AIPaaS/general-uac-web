# Pull base image  
FROM 10.19.13.18:5000/tomcat:7 
MAINTAINER gucl<gucl@asiainfo.com>  

RUN yum install -y unzip

# Install tomcat7 
RUN rm -fr /opt/apache-tomcat-7.0.72/webapps/*
COPY ./build/libs/uac.war /opt/apache-tomcat-7.0.72/webapps
RUN cd /opt/apache-tomcat-7.0.72/webapps && unzip -oq uac.war -d ROOT
RUN ls /opt/apache-tomcat-7.0.72/webapps/uac
RUN mv /opt/apache-tomcat-7.0.72/webapps/uac /opt/apache-tomcat-7.0.72/webapps/ROOT
ADD ./script/general-uac-web.sh /general-uac-web.sh
RUN chmod 755 /*.sh  
  
# Define default command.  
CMD ["/general-uac-web.sh"]