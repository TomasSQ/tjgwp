mvn clean install -o && mvn verify -o && cd view && mvn appengine:devserver $1 && cd ..
