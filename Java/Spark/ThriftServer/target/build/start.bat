@echo off
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\commons-codec-1.6.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\commons-logging-1.1.1.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\httpclient-4.2.5.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\httpcore-4.2.4.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\libthrift-0.9.2.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\slf4j-api-1.5.8.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\slf4j-simple-1.5.5.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\ThriftSample-0.0.1.jar
set CLASSPATH=%CLASSPATH%;E:\200.Spark\SparkWorkSpace\ThriftServer\target\build\ThriftServer-0.0.1.jar

java -DEXE=JavaServer -cp %CLASSPATH% com.thlriftSample.Server.JavaServer
pause



