## COMP90015 Distributed Systems Project2

## Distributed Shared Whiteboard
A distributed whiteboard implemented for COMP90015 Distributed Systems. There are creativity features built, beyond the specification. For more details about the project, please check the spec.pdf. A total mark 24.5 achieved out of 25.<br>
### 1 Introduction
This project uses RMI for communication between data server and web server, client to web server. MQTT is used for publishing any changes and updates from the web server to all clients that subscribe to the specific topic. 

### 2 Instructions and Usages
### Run with jars
There are three jars to be built: runDataserver, runWbserver and runClient. 
Current build support java 8/java 1.8 and below only. After building the three jar files, 
use the following command to remove the digital signature in mqtt-library:
```zip -dr [jar-name] 'META-INF/.SF' 'META-INF/.RSA' 'META-INF/*SF'```<br>
Start the data server with the following command first: 
```java -jar [data-server] -ip [your-ip] -p [port-you-want-to-listen]```<br>
Then start the web server with:
```java -jar [web-server] -ip [your-ip]```<br>
Finally the client:
```java -jar [client]```
<br>
You can start as many client as you want. Make sure the port number is correctly entered. You can also use localhost for testing purpose. 
Remember to put security.policy with the jars. All jars must be placed with /storage directory as well, for authentication purpose. 
### Run with any idea
Add three libraries into the project first. Run sequence: data server -> wb server -> client. 
### 3 Group Information

#### 3.1 Group Name

​	A Team

#### 3.2 Group Member

- Zihan Zhang

- Guang Yang

- Chengeng Liu

- Xiuge Chen

  
