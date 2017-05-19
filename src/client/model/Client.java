package client.model;

import java.io.*;
import java.net.*;

import client.model.*;

/**
 *Client represents a user who sends the message to the server.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Client
{
	private Model model_;
	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;
	
	// the server, the port and the username
	private String server, username;
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
		// if it failed not much I can so
		catch(Exception ec) {
			model_.setMessageFromServer(("Error connectiong to server:" + ec), "Server");
		}
				
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		System.out.println(msg);
		model_.setMessageFromServer(msg, "Server");
		
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
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
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			model_.setMessageFromServer(("Exception doing login : " + eIO), "Server");
			disconnect();
		}
		// success we inform the caller that it worked

	}
	
	public void disconnect()
	{
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
	}
	
	public void sendMessage(ChatMessage msg)
	{
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
		}
	}
	
	public void connectToServer(String username)
	{
		initialize();
	}
	
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					ChatMessage msg = (ChatMessage) sInput.readObject();
					// if console mode print the message and add back the prompt
					if(msg.getType() == 1)
						model_.setMessageFromServer(msg.getMessage(), msg.getOrigin()); //TO DO user !!!!
					else if(msg.getType() == 3)
						model_.getUpdateUserList(msg.getUsers());
				}
				catch(IOException e) {
					model_.setMessageFromServer(("Server has close the connection: " + e), "Server");
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
