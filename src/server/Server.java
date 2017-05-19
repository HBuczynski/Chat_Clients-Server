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
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Server 
{
	private GuiServerCreator gui_;
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;
	// to display time
	private SimpleDateFormat sdf;

	// the boolean that will be turned of to stop the server
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
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);									// save it in the ArrayList
				updateList();
				t.start();
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
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if(ct.id == id) {
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
		for(int i = al.size(); --i >= 0;) 
		{
			ClientThread ct = al.get(i);
			if(ct.username.equals(chat.getDestination()))
			{
			// try to write to the Client if it fails remove it from the list
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
		
	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// the only type of message a will receive
		ChatMessage chatMessage_;
		// the date I connect
		String date;

		// Constructore
		ClientThread(Socket socket) {
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
				// read the username
				username = (String) sInput.readObject();
				gui_.addMessage(username + " just connected.");
			}
			catch (IOException e) {
				gui_.addMessage("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException
			// but I read a String, I am sure it will work
			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			
			while(keepGoing) {
				// read a String (which is an object)
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
				// the messaage part of the ChatMessage
				String message = chatMessage_.getMessage();

				// Switch on the type of message receive
				switch(chatMessage_.getType()) {
					case ChatMessage.MESSAGE:
						broadcast(chatMessage_);
						break;
					case ChatMessage.WHOISIN:
						//writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
						// scan al the users connected
						for(int i = 0; i < al.size(); ++i) {
							ClientThread ct = al.get(i);
							//writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
						}
						break;
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
		private boolean writeMsg(ChatMessage msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
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
