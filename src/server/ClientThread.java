package server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
public class ClientThread extends Thread
{
	private String clientName = null;
	private DataInputStream is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private Vector<ClientThread> threads;

	public ClientThread(Socket clientSocket, Vector<ClientThread> threads) 
	{
		this.clientSocket = clientSocket;
	    this.threads = threads;
	}
	
	//@SuppressWarnings("deprecation")
	@SuppressWarnings("deprecation")
	public void run()
	{
		Vector<ClientThread> threads = this.threads;

	    try {
	      /*
	       * Create input and output streams for this client.
	       */
	    	is = new DataInputStream(clientSocket.getInputStream());
	    	os = new PrintStream(clientSocket.getOutputStream());
	    	
	    	String name;
	    	while (true) 
	    	{
	    		name = is.readLine().trim();
	    		if (name.indexOf('@') == -1) 
	    		{
	    			break;
	    		} 
	    		else 
	    		{
	    			os.println("The name should not contain '@' character.");
	    		}
	      }

	      /* Welcome the new the client. */
	      os.println("Welcome " + name + " to our chat room.\n");
	      synchronized (this) 
	      {
	        for (int i = 0; i < threads.size(); i++) 
	        {
	          if (threads.get(i) != null && threads.get(i) == this) 
	          {
	        	  clientName = "@" + name;
	        	  break;
	          }
	        }
	        for (int i = 0; i < threads.size(); i++) 
	        {
	        	if (threads.get(i) != null && threads.get(i) == this)
	        	{
	        		threads.get(i).os.println("*** A new user " + name + " entered the chat room !!! ***");
	        	}
	        }
	      }
	      
	      /* Start the conversation. */
	      while (true) 
	      {
	        @SuppressWarnings("deprecation")
			String line = is.readLine();
	        if (line.startsWith("/quit")) 
	        {
	          break;
	        }
	        
	        /* If the message is private sent it to the given client. */
	        if (line.startsWith("@")) 
	        {
	        	String[] words = line.split("\\s", 2);
	        	if (words.length > 1 && words[1] != null) 
	        	{
	        		words[1] = words[1].trim();
	        		if (!words[1].isEmpty()) 
	        		{
	        			synchronized (this) {
	        				for (int i = 0; i < threads.size(); i++) 
	        				{
	        					if (threads.get(i) != null && threads.get(i) != this
	        							&& threads.get(i).clientName != null
	        							&& threads.get(i).clientName.equals(words[0])) 
	        					{
	        						threads.get(i).os.println("<" + name + "> " + words[1]);
	        						/*
	        						 * Echo this message to let the client know the private
	        						 * message was sent.
	        						 */
	        						this.os.println(">" + name + "> " + words[1]);
	        						break;
	        					}
	        				}
	        			}
	        		}
	        	}
	        } 
	        else 
	        {
	          /* The message is public, broadcast it to all other clients. */
	        	synchronized (this) 
	        	{
	        		for (int i = 0; i < threads.size(); i++)  
	        		{
	        			if (threads.get(i) != null && threads.get(i).clientName != null) 
	        			{
	        				threads.get(i).os.println("<" + name + "> " + line);
	        			}
	        		}
	        	}
	        }
	      }
	      
	      synchronized (this) 
	      {
	    	  for (int i = 0; i < threads.size(); i++) 
	    	  {
	    		  if (threads.get(i) != null && threads.get(i) != this
	    				  && threads.get(i).clientName != null) 
	    		  {
	    			  threads.get(i).os.println("*** The user " + name + " is leaving the chat room !!! ***");
	    		  }
	    	  }
	      }
	      os.println("*** Bye " + name + " ***");

	      /*
	       * Clean up. Set the current thread variable to null so that a new client
	       * could be accepted by the server.
	       */
	      synchronized (this) 
	      {
	    	  for (int i = 0; i < threads.size(); i++) 
	    	  {
	    		  if (threads.get(i) == this) 
	    		  {
	    			  threads.remove(i);
	    		  }
	    	  }
	      }
	      /*
	       * Close the output stream, close the input stream, close the socket.
	       */
	      is.close();
	      os.close();
	      clientSocket.close();
	    } 
	    catch (IOException e) 
	    {
	    }
	}
}

