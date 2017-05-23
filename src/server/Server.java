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
	private GuiServerCreator gui;
	private static int uniqueId;
	private ArrayList<ClientThread> clientList;
	private SimpleDateFormat dataFormat;
	private boolean run;
	private int port;
		
	public Server(int portNumber)
	{
		port = portNumber;
		gui = new GuiServerCreator();
		run = true;
		clientList = new ArrayList<ClientThread>();
	}
		
	public void enable()
	{
		run = true;
		/**
		 *  Create socket server and wait for connection requests. 
		 */
		try 
		{
			/**
			 * The socket used by the server.
			 */
			ServerSocket serverSocket = new ServerSocket(port);

			/**
			 * Infinite loop to wait for connections.
			 */
			while(run) 
			{
				/** 
				 * Format message saying we are waiting.
				 */
				gui.addMessage("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  	
				if(!run)
					break;
				ClientThread client = new ClientThread(socket);  
				
				if(client.getNameStatus())
				{
					clientList.add(client);							
					updateList();
					client.start();
				}
			}
			try {
				serverSocket.close();
				for(int i = 0; i < clientList.size(); ++i) {
					ClientThread tc = clientList.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException ioE) {}
				}
			}
			catch(Exception e) {
				gui.addMessage("Exception closing the server and clients: " + e);
			}
		}
		catch (IOException e) {
            String msg = dataFormat.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            gui.addMessage(msg);
		}
	}
	
	synchronized void remove(int id) 
	{
		/** 
		 * Scan the array list until we found the Id.
		 */
		for(int i = 0; i < clientList.size(); ++i) 
		{
			ClientThread ct = clientList.get(i);
			if(ct.id == id) 
			{
				clientList.remove(i);
				return;
			}
		}
		System.out.println("\nW liscie jest; " + clientList.size());
		updateList();
	}
	
	/**
	 * Broadcast a message to all Clients.
	 * @param chat
	 */
	private synchronized void broadcast(ChatMessage chat) 
	{
		ClientThread ct;
		for(int i = clientList.size(); --i >= 0;) 
		{
			ct = clientList.get(i);
			/**
			 * Find the destination of the message.
			 */
			if(ct.username.equals(chat.getDestination()))
			{
				if(!ct.writeMsg(chat)) {
					clientList.remove(i);
					gui.addMessage("Disconnected Client " + ct.username + " removed from list.");
				}
			}
		}
	}
	
	/**
	 * Send information about active users to each client.
	 */
	private synchronized void updateList()
	{
		Vector<String> vect = new Vector<String>();
		
		for(int i=0; i<clientList.size(); ++i)
		{
			vect.add(clientList.get(i).username);
		}
		
		ChatMessage chat = new ChatMessage(ChatMessage.USERLIST, "", "", "");
		chat.setuserList(vect);
				
		/**
		 * Send message to all users.
		 */
		for(int i=0; i<clientList.size(); i++)
		{
			clientList.get(i).writeMsg(chat);
		}
	}
	
	/** 
	 * Check if name is currently occupied.
	 * @param name
	 * @return
	 */
	public boolean checkUsername(String name)
	{
		for(int i = clientList.size(); --i >= 0;) 
		{
			if((clientList.get(i).username).equals(name))
				return false;
		}
		return true;
	}
		
	/**
	 * One instance of this thread will run for each client.
	 * @author Hubert
	 *
	 */
	class ClientThread extends Thread 
	{
		private Socket socket;
		private ObjectInputStream sInput;
		private ObjectOutputStream sOutput;
		private int id;
		private boolean acceptedName = false;
		private String username;
		private ChatMessage chatMessage_;
		private String date;

		ClientThread(Socket socket) 
		{
			id = ++uniqueId;
			this.socket = socket;
			
			/**
			 * Creating both Data Stream.
			 */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				/**
				 * Create output first.
				 */
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				username = (String) sInput.readObject();
				
				if(checkUsername(username))
				{
					gui.addMessage(username + " just connected.");
					acceptedName = true;
					String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
					ChatMessage info = new ChatMessage(ChatMessage.MESSAGE, msg, username, "Server");
					writeMsg(info);
				}
				else
				{
					gui.addMessage("Such name: " + username + " exists in database." );
					String msg = "Such name: " + username + " exists in database. Change your name!" ;
					ChatMessage info = new ChatMessage(ChatMessage.LOGOUT, msg, username, "Server");
					writeMsg(info);
					--uniqueId;
				}
			}
			catch (IOException e) 
			{
				gui.addMessage("Exception creating new Input/output Streams: " + e);
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
					gui.addMessage(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}

				if(chatMessage_.getType() == (ChatMessage.MESSAGE))
				{
					broadcast(chatMessage_);
				}
				else if(chatMessage_.getType() == ChatMessage.LOGOUT)
				{
					remove(id);
					close();
				}
			}
			/**
			 * Remove myself from the arrayList containing the list of the connected Clients.
			 */
			remove(id);
			close();
		}
		
		/**
		 *  Write a String to the Client output stream
		 * @param msg
		 * @return
		 */
		private boolean writeMsg(ChatMessage msg) 
		{
			/**
			 * If Client is still connected send the message to it.
			 */
			if(!socket.isConnected()) 
			{
				close();
				return false;
			}
			/**
			 * Write the message to the stream.
			 */
			try 
			{
				sOutput.writeObject(msg);
			}
			/**
			 * If an error occurs, do not abort just inform the user.
			 */
			catch(IOException e) 
			{
				gui.addMessage("Error sending message to " + username);
				gui.addMessage(e.toString());
			}
			return true;
		}
		
		/**
		 * Try to close the connection.
		 */
		private void close() 
		{
			
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
