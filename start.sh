mvn clean install && mvn verify && cd view && mvn appengine:devserver -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y && cd ..
