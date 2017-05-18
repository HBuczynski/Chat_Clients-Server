package server;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Server manages connections between users. This part of application is responsible for authorized users and redirection
 * information between them.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Server 
{
	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;
	
	private static Vector<ClientThread> threads = new Vector<ClientThread>();
	private int portNumber;
	private GuiServerCreator gui_;
	private boolean run;
	
	public Server()
	{
		gui_ = new GuiServerCreator();
		run = true;
	}
		
	public void enable()
	{
		portNumber = 8000;
		
		try 
		{
			serverSocket = new ServerSocket(portNumber);
		} 
		catch (IOException e) 
		{
			gui_.addMessage(e.toString());
		}
		
		// Create a client socket for each connection and pass it to a new client
	    // thread.
		
		while (run) 
		{ 
			try
			{ 
				clientSocket = serverSocket.accept();
				threads.add(new ClientThread(clientSocket, threads));
		        threads.lastElement().start();
		        
		    } 
			catch (IOException e) 
			{
		        gui_.addMessage(e.toString()); 
		        gui_.addMessage("lolo");
		        
		      }
		    
		 }
	     
	}

}
