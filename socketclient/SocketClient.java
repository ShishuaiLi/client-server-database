package socketclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {
    
  public static void main(String[] args) throws Exception{
    String host = args[0];
    int port = Integer.parseInt(args[1]);  
    
    Scanner scanner = new Scanner(System.in);
    String command;
//    String host = "localhost";
//    int port = 61291;
    String help = "Man Page:\n"
            + "help: dispaly the man page.\n"
            + "put (name) (value) (type): add name record to the name "
            + "service database.\n"
            + "get (name) (type): query a name recod.\n"
            + "del (name) (type): remove a name record from the database.\n"
            + "browse: retrieve all current name records in the database.\n"
            + "exit: terminates the current TCP connection.\n";
    boolean stop = true;
    SocketClient CLIENT = new SocketClient();
    Socket SOCK = new Socket(host,port);
    
    do{
        System.out.print("Enter command...(Enter 'HELP' for help): ");
        command = scanner.nextLine();
        String[] para = command.split(" ");
        
        switch(para[0].toLowerCase()) {
            case "help": System.out.println(help); break;
            case "put": put(CLIENT.run(SOCK,command)); break; 
            case "get": get(CLIENT.run(SOCK,command)); break;
            case "del": del(CLIENT.run(SOCK,command)); break;
            case "browse": browse(CLIENT.run(SOCK,command)); break;
            case "exit": stop=false;CLIENT.run(SOCK,command); break; 
            default: System.out.println("Command does not exist. Please re-enter."); break;
        }
    }while(stop);
    
    if(!stop) System.out.println("Session terminated successfully!");
    SOCK.close();
  }

  public BufferedReader run(Socket SOCK,String command) throws Exception {
	InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
    BufferedReader BR = new BufferedReader(IR);
    
    PrintStream PS = new PrintStream(SOCK.getOutputStream());
    PS.println(command);    
    
    return BR;
  }

  public static void put(BufferedReader BR) throws IOException {
      String MESSAGE = BR.readLine();
	  BR.readLine();
      if(MESSAGE.indexOf("200") == 0) System.out.println("Record updated successfully!");
      else System.out.println(MESSAGE);
  }
  
  public static void get(BufferedReader BR) throws IOException {
      String MESSAGE = BR.readLine();
	  BR.readLine();
      if(MESSAGE.indexOf("200") == 0) {
          System.out.println(MESSAGE.split("_")[1]);
      }
      else System.out.println(MESSAGE);
  }
  
  public static void del(BufferedReader BR) throws IOException {
      String MESSAGE = BR.readLine();
      BR.readLine();	  
      if(MESSAGE.indexOf("200") == 0) System.out.println("Record deleted successfully!");
      else System.out.println(MESSAGE);
  }
  
  public static void browse(BufferedReader BR) throws IOException {
      String MESSAGE = BR.readLine();
      if(MESSAGE.indexOf("200") == 0) {
          String item;
        while ((item = BR.readLine()).length() != 0) {
            System.out.println(item);
        }         
      }
      else System.out.println(MESSAGE);
  }
  
}