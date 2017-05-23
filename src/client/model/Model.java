package client.model;

import java.util.Scanner;
import java.util.Vector;

import client.view.View;

/**
 * Model is a back-end of application.Its duties include connection with server and gathering
 * information about user interaction from controller.
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Model 
{	
	private Client client;
	private View view;
	
	public Model()
	{}
	
	/**
	 * method provide connection with view
	 * */
	public void setView(View view)
	{
		this.view = view;
	}
	
	/**
	 * Initialize connection with server after "Connect" button is pressed.
	 * @param userName
	 * @param hostName
	 * @param portNumber
	 */
	public void setConnectionWithServer(String userName, String hostName, int portNumber)
	{
		client = new Client(hostName, portNumber, view.getUsername(), this);
		client.initialize();
	}
	
	/**
	 * Send message from user to server. 
	 * Message object consists information about sender and recipient. 
	 * @param message
	 * @param dest
	 */
	public void redirectMessageToServer(String message, String dest)
	{
		client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, message, dest, view.getUsername()));
	}
	
	/**
	 * Update users list which is situated on the left side of user interface.
	 * @param users
	 */
	public void getUpdateUserList(Vector<String> users)
	{
		/**
		 * the old list is removed
		 */
		view.removeUsersList();
		view.clearUSerPanel();
		for(int i=0; i < users.size(); i++)
		{
			view.addUsersToList(users.get(i));
		}
	}
	
	/**
	 * Redirect message from server to GUI.
	 * Server send information about sender.
	 * @param msg
	 * @param user
	 */
	public void setMessageFromServer(String msg, String user)
	{
		view.setAppendMessage(msg, user);
	}
	
	/**
	 * Each user name should be unique.
	 * Server send information if some name exists in its database.
	 * @param msg
	 * @param user
	 */
	public void loggFailed(String msg, String user)
	{
		view.setAppendMessage(msg, user);
		view.loggAgain();
	}
	
	/**
	 * Break connection with server.
	 */
	public void disconnect()
	{
		client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "log", "Server", view.getUsername()));
		client.disconnect();
	}	
}
