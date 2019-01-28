package serversocket;

import java.io.*;
import java.net.*;
import serversocket.ServerThread;
public class ScoketServer{

  public static void main(String[] args) throws Exception{
    ScoketServer server = new ScoketServer();
    server.run();
  }

  public void run() throws Exception {
        ServerSocket SRVSOCK = new ServerSocket(0);
        System.out.println("Please Enter this port: "+ SRVSOCK.getLocalPort());
        
        while(true) {
            Socket SOCK = SRVSOCK.accept();
            ServerThread thread = new ServerThread(SOCK);
            thread.start();
        }
 }
}