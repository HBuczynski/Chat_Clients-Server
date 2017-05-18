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
public class Client implements Runnable
{
	// The client socket
	private static Socket clientSocket = null;
	// The output stream
	private static PrintStream os = null;
	// The input stream
	private static DataInputStream is = null;

	private static BufferedReader inputLine = null;
	private static boolean closed = false;
	
	private String username_;
	
	public Client()
	{
		
	}
	
	public void initialize()
	{
		int portNumber = 8000;
		// The default host.
		String host = "localhost";
		
		  /*
	     * Open a socket on a given host and port. Open input and output streams.
	     */
    try 
    {
    	clientSocket = new Socket(host, portNumber);
    	inputLine = new BufferedReader(new InputStreamReader(System.in));
    	os = new PrintStream(clientSocket.getOutputStream());
    	is = new DataInputStream(clientSocket.getInputStream());
    } 
    catch (UnknownHostException e) 
    {
    	System.err.println("Don't know about host " + host);
	} 
    catch (IOException e) 
    {
    	System.err.println("Couldn't get I/O for the connection to the host " + host);
	}

	/*
	* If everything has been initialized then we want to write some data to the
	* socket we have opened a connection to on the port portNumber.
	*/
	if (clientSocket != null && os != null && is != null) 
	{
		try 
		{
	    /* Create a thread to read from the server. */
			new Thread(new Client()).start();
			
	        while (!closed) 
	        {
	        	System.out.println(1);
	        	os.println(inputLine.readLine().trim());
	        	System.out.println(2);
	        }
	        /*
	         * Close the output stream, close the input stream, close the socket.
	         */
	        os.close();
	        is.close();
	        clientSocket.close();
	    } 
		catch (IOException e) 
		{
			System.err.println("IOException:  " + e);
	    }
		
		
	 }
	}
	
	
	public void sendToServer(String message)
	{
		
	}
	
	public void receiveMessageFromServer()
	{
		
	}
	
	public void receiveUserListFromServer()
	{
		
	}
	
	public void connectToServer(String username)
	{
		username_ = username;
		initialize();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() 
	{
	/*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
		
	    String responseLine;
	    try 
	    {
//tutaj stoi
	    	while ((responseLine = is.readLine()) != null) 
		      {
		    	  
		    	  System.out.println(responseLine);
		    	  if (responseLine.indexOf("*** Bye") != -1)
		    		  break;
		      }
		      closed = true;
		      System.out.println(3);
	    } 
	    catch (IOException e) 
	    {
	    	System.err.println("IOException:  " + e);
	    }
	}

}
