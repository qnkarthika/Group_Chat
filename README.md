# Group_Chat Using java in different machines

Group Chat Application:
The group chat application is a Java-based program that allows multiple clients to connect to a server and exchange messages in a group chat environment. The server acts as a central hub, receiving messages from clients and broadcasting them to all connected clients. Clients can send messages that are then distributed to all participants in the chat room.

Implementation Steps:
To implement the group chat application, follow these procedures:

1.Set up the development environment:

Install the Java Development Kit (JDK) on your machine.
Choose an integrated development environment (IDE) such as Eclipse, IntelliJ IDEA, or NetBeans for coding.

2.Change the IP address field in GroupChatClient.java to the server machine IP address.use `ipconfig` command in cmd to get the IP address.Ensure that both the system(server and client) are connected in the same network.if `request timed out` error occurs,try to check `ping <IP address>` command in cmd to verify both the machines are communicate properly.

3.Start the server using the following command in cmd: 
        1.cd Group_Chat
        2.javac GroupChatServer.java
        3.java GroupChatServer
your server is started with port 8080.

4.start the client using the following command in different window and different system in cmd:
        1.cd Group_Chat
        2.javac GroupChatClient.java
        3.java GroupChatClient
It displays `connected to the server` message.

5.start to send messages and try to run GroupChatClient.java in different machines for multiple machine communication.No need to have GroupChatServer.java file in the client machine to establish the communication.
