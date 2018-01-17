SET home=%cd%

cd  %home%\bin\windows
start zookeeper-server-start.bat ../../config/zookeeper.properties
ping 127.0.0.1 -n 10 >nul

cd  %home%\bin\windows
start kafka-server-start.bat ../../config/server.properties

