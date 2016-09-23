# Pull base image  
FROM 10.19.13.18:5000/tomcat:7 
MAINTAINER gucl<gucl@asiainfo.com>  

# Install tomcat7 
RUN rm -fr /opt/apache-tomcat-7.0.72/webapps/*

ADD ./build/libs/uac.war /opt/apache-tomcat-7.0.72/webapps
RUN mv /opt/apache-tomcat-7.0.72/webapps/uac /opt/apache-tomcat-7.0.72/webapps/ROOT

ADD ./script/general-uac-web.sh /general-uac-web.sh
RUN chmod 755 /*.sh  
  
# Define default command.  
CMD ["/general-uac-web.sh"]