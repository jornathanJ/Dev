#!/usr/bin/ksh
export CLASSPATH=.
export CLASSPATH=%CLASSPATH%;./commons-codec-1.6.jar
export CLASSPATH=%CLASSPATH%;./commons-logging-1.1.1.jar
export CLASSPATH=%CLASSPATH%;./httpclient-4.2.5.jar
export CLASSPATH=%CLASSPATH%;./httpcore-4.2.4.jar
export CLASSPATH=%CLASSPATH%;./libthrift-0.9.2.jar
export CLASSPATH=%CLASSPATH%;./slf4j-api-1.5.8.jar
export CLASSPATH=%CLASSPATH%;./slf4j-simple-1.5.5.jar
export CLASSPATH=%CLASSPATH%;./ThriftSample-0.0.1.jar
export CLASSPATH=%CLASSPATH%;./ThriftServer-0.0.1.jar

java -DFID=JavaServer -Dfile.encoding=euc-kr  -cp %CLASSPATH% com.thlriftSample.Server.JavaServer




