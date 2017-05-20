package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import client.model.ChatMessage;

/**
 * Server manages connections between users. This part of application is responsible for authorized users and redirection
 * information between them.
 * Class was being written based on:
 * http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Server 
{
	private GuiServerCreator gui_;
	private static int uniqueId;
	private ArrayList<ClientThread> al;
	private SimpleDateFormat sdf;
	private boolean run;
	private int port;
		
	public Server(int portNumber)
	{
		port = portNumber;
		gui_ = new GuiServerCreator();
		run = true;
		al = new ArrayList<ClientThread>();
	}
		
	public void enable()
	{
		run = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(run) 
			{
				// format message saying we are waiting
				gui_.addMessage("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!run)
					break;
				ClientThread client = new ClientThread(socket);  // make a thread of it
				
				if(client.getNameStatus())
				{
					al.add(client);									// save it in the ArrayList
					updateList();
					client.start();
				}
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				gui_.addMessage("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            gui_.addMessage(msg);
		}
	}
	
	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) 
	{
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) 
		{
			ClientThread ct = al.get(i);
			if(ct.id == id) 
			{
				al.remove(i);
				return;
			}
		}
	}
	
	/*
	 *  to broadcast a message to all Clients
	 */
	private synchronized void broadcast(ChatMessage chat) 
	{
		ClientThread ct;
		for(int i = al.size(); --i >= 0;) 
		{
			ct = al.get(i);
			if(ct.username.equals(chat.getDestination()))
			{
				System.out.println("Broadcst:" +ct.username);
				if(!ct.writeMsg(chat)) {
					al.remove(i);
					gui_.addMessage("Disconnected Client " + ct.username + " removed from list.");
				}
			}
		}
	}
	
	private synchronized void updateList()
	{
		Vector<String> vect = new Vector<String>();
		
		for(int i=0; i<al.size(); ++i)
		{
			vect.add(al.get(i).username);
		}
		
		ChatMessage chat = new ChatMessage(ChatMessage.USERLIST, "", "", "");
		chat.setuserList(vect);
				
		//send message to all users
		for(int i=0; i<al.size(); ++i)
		{
			al.get(i).writeMsg(chat);
		}
	}
	
	public boolean checkUsername(String name)
	{
		for(int i = al.size(); --i >= 0;) 
		{
			if((al.get(i).username).equals(name))
				return false;
		}
		return true;
	}
		
	/** One instance of this thread will run for each client */
	class ClientThread extends Thread 
	{
		private Socket socket;
		private ObjectInputStream sInput;
		private ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		private int id;
		private boolean acceptedName = false;
		private String username;
		private ChatMessage chatMessage_;
		private String date;

		ClientThread(Socket socket) 
		{
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				username = (String) sInput.readObject();
				
				if(checkUsername(username))
				{
					gui_.addMessage(username + " just connected.");
					acceptedName = true;
					String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
					ChatMessage info = new ChatMessage(ChatMessage.MESSAGE, msg, username, "Server");
					writeMsg(info);
				}
				else
				{
					gui_.addMessage("Such name: " + username + " exists in database." );
					String msg = "Such name: " + username + " exists in database. Change your name!" ;
					ChatMessage info = new ChatMessage(ChatMessage.LOGOUT, msg, username, "Server");
					writeMsg(info);
					--uniqueId;
				}
			}
			catch (IOException e) 
			{
				gui_.addMessage("Exception creating new Input/output Streams: " + e);
				return;
			}

			catch (ClassNotFoundException e) {}
            
			date = new Date().toString() + "\n";
		}
		
		public boolean getNameStatus()
		{
			return acceptedName;
		}
		
		public void run() 
		{
			boolean keepGoing = true;
			while(keepGoing) 
			{
				try {
					chatMessage_ = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					gui_.addMessage(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				String message = chatMessage_.getMessage();

				if(chatMessage_.getType() == (ChatMessage.MESSAGE))
				{
					broadcast(chatMessage_);
				}
			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			remove(id);
			close();
		}
		
		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(ChatMessage msg) 
		{
			// if Client is still connected send the message to it
			if(!socket.isConnected()) 
			{
				close();
				return false;
			}
			// write the message to the stream
			try 
			{
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) 
			{
				gui_.addMessage("Error sending message to " + username);
				gui_.addMessage(e.toString());
			}
			return true;
		}
		
		private void close() 
		{
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}		
	}
}
