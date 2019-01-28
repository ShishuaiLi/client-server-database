package serversocket;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author superbrucegu
 */
public class ServerThread extends Thread{
    private Socket socket;
    private static ArrayList<Record> records = new ArrayList();
    
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {   
        try {
            BufferedReader BR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean terminate = false;
           do {
                String MESSAGE = BR.readLine();
                if(MESSAGE != null) {
                  PrintStream PS = new PrintStream(socket.getOutputStream());
                  System.out.println(MESSAGE);
                  String[] receivedInput = MESSAGE.split(" ");

                  switch(receivedInput[0].toLowerCase()) {
                    case "put": synchronized(records){put(MESSAGE, PS);}break;
                    case "get": get(MESSAGE, PS);break;
                    case "del": synchronized(records){del(MESSAGE, PS);}break;
                    case "browse": browse(PS);break;
                    case "exit": terminate = true; break;
                    default: PS.print("400 Bad Request\n\n");break;
                  } 
                }
                
            }while(!terminate);
         socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void put(String receivedInput, PrintStream PS) {
        String[] para = receivedInput.split(" ");
        int counter = 0;
        if(para.length == 4) {     
            for(Record record : records) {
                if(!record.compareTo(para[1], para[2], para[3])) counter++;
                else record.setValue(para[2]);
            }
            if(counter >= records.size() || records.size() == 0) records.add(new Record(para[1], para[2], para[3]));
            PS.print("200 OK\n\n");
        }
        else PS.print("400 Bad Request\n\n");
    }

    public void get(String receivedInput, PrintStream PS) {   
        String[] para = receivedInput.split(" "); 
        if(para.length == 3) {        
            for(Record record : records) {
                if(record.compareTo(para[1], para[2])) {
                    PS.print("200 OK_"+record.getValue()+"\n\n");
                    return;
                }
            }
			PS.print("404 Not Found\n\n");
			return;
        }
        PS.print("400 Bad Request\n\n");
    }
 
    public void del(String receivedInput, PrintStream PS) {
        String[] para = receivedInput.split(" ");
        
        if(records.isEmpty()) {
            PS.print("250 Database is Empty\n\n");
            return;
        }
        
        if(para.length == 3) {
            for(Record record : records) {
                if(record.compareTo(para[1], para[2])) {
                    records.remove(record);
                    PS.print("200 OK\n\n");
                    return;
                }
            }
			PS.print("404 Not Found\n\n");
			return;
        }
        else PS.print("400 Bad Request\n\n");
    }
   
    public void browse(PrintStream PS) {
        if(records.isEmpty()) {
            PS.print("250 Database is Empty\n\n");
            return;
        }       
        PS.println("200 OK");
        for(Record record : records) {
            PS.println(record.toString());
        }
        PS.println();

    }
}
