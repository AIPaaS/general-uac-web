# Pull base image  
FROM 10.19.13.18:5000/tomcat:7 
MAINTAINER gucl<gucl@asiainfo.com>  

# Install tomcat7 
RUN rm -fr ${CATALINA_HOME}/webapps/*

ADD ./build/libs/uac.war ${CATALINA_HOME}/webapps
RUN mv ${CATALINA_HOME}/webapps/uac ${CATALINA_HOME}/webapps/ROOT

ADD ./script/general-uac-web.sh /general-uac-web.sh
RUN chmod 755 /*.sh  
  
# Define default command.  
CMD ["/general-uac-web.sh"]