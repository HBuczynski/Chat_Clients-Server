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
	private Client client_;
	private View view_;
	
	public Model()
	{}
	
	//method provide connection with view
	public void setView(View view)
	{
		view_ = view;
	}
	
	//initialize connection with server after "Connect" button is pressed
	public void setConnectionWithServer(String userName, String hostName, int portNumber)
	{
		client_ = new Client(hostName, portNumber, view_.getUsername(), this);
		client_.initialize();
	}
	
	//send message from user to server
	//message object consists information about sender and recipient 
	public void redirectMessageToServer(String message, String dest)
	{
		client_.sendMessage(new ChatMessage(ChatMessage.MESSAGE, message, dest, view_.getUsername()));
	}
	
	//update users list which is situated on the left side of user interface
	public void getUpdateUserList(Vector<String> users)
	{
		//the old list is removed
		view_.removeUsersList();
		view_.clearUSerPanel();
		for(int i=0; i < users.size(); i++)
		{
			view_.addUsersToList(users.get(i));
		}
	}
	
	//redirect message from server to GUI
	//server send information about sender
	public void setMessageFromServer(String msg, String user)
	{
		view_.setAppendMessage(msg, user);
	}
	
	//each user name should be unique
    //server send information if some name exists in its database
	public void loggFailed(String msg, String user)
	{
		view_.setAppendMessage(msg, user);
		view_.loggAgain();
	}
	
	//break connection with server
	public void disconnect()
	{
		client_.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "log", "Server", view_.getUsername()));
		client_.disconnect();
	}	
}
