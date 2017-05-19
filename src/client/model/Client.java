package client.model;

import java.io.*;
import java.net.*;

import client.model.*;

/**
 * Client represents a user who sends the message to the server.
 * Class was being written based on:
 * http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Client
{
	private Model model_;
	private ObjectInputStream streamInput;		// to read from the socket
	private ObjectOutputStream streamOutput;		// to write on the socket
	private Socket socket;
	
	// the server, the port and the username
	public String username;
	private String server;
	private int port;
	
	public Client(String server, int port, String username, Model model)
	{
		this.server = server;
		this.port = port;
		this.username = username;
		model_= model;
	}
	
	public void initialize()
	{
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		catch(Exception ec) {
			model_.setMessageFromServer(("Error connectiong to server:" + ec), "Server");
		}
				
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		System.out.println(msg);
		model_.setMessageFromServer(msg, "Server");
		
		/* Creating both Data Stream */
		try
		{
			streamInput  = new ObjectInputStream(socket.getInputStream());
			streamOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			model_.setMessageFromServer(("Exception creating new Input/output Streams: " + eIO), "Server");
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			streamOutput.writeObject(username);
		}
		catch (IOException eIO) {
			model_.setMessageFromServer(("Exception doing login : " + eIO), "Server");
			disconnect();
		}
	}
	
	public void disconnect()
	{
		try { 
			if(streamInput != null) streamInput.close();
		}
		catch(Exception e) {}
		try {
			if(streamOutput != null) streamOutput.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
	}
	
	public void sendMessage(ChatMessage msg)
	{
		try {
			streamOutput.writeObject(msg);
		}
		catch(IOException e) {}
	}
	
	public void connectToServer(String username)
	{
		initialize();
	}
	
	class ListenFromServer extends Thread {

		public void run() 
		{
			while(true) 
			{
				try {
					ChatMessage msg = (ChatMessage) streamInput.readObject();
		
					if(msg.getType() == 1)
					{
						model_.setMessageFromServer(msg.getMessage(), msg.getOrigin()); //TO DO user !!!!
					}
					else if(msg.getType() == 3)
					{
						model_.getUpdateUserList(msg.getUsers());
					}
				}
				catch(IOException e) {
					model_.setMessageFromServer(("Server has close the connection: " + e), "Server");
					break;
				}
				catch(ClassNotFoundException e2) {}
			}
		}
	}
}
