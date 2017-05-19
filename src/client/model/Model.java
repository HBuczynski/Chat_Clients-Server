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

public class Model {
	
	private int port = 1500;
	private String host = "localhost";
	
	public Model()
	{
		
	}
	
	public void setView(View view)
	{
		view_ = view;
	}
	
	public void getUpdateUserList(Vector<String> users)
	{
		view_.removeUsersList();
		
		for(int i=0; i < users.size(); ++i)
		{
			view_.addUsersToList(users.get(i));
		}
	}
	
	public void redirectMessageToServer(String message, String dest)
	{
		client_.sendMessage(new ChatMessage(ChatMessage.MESSAGE, message, dest, view_.getUsername()));
	}
	
	public void setConnectionWithServer(String userName)
	{
		client_ = new Client(host, port, view_.getUsername(), this);
		client_.initialize();
	}
	
	public void setMessageFromServer(String msg, String user)
	{
		view_.setAppendMessage(msg, user);
	}
	
	public void disconnect()
	{
		client_.disconnect();
	}
	
	private Client client_;
	private View view_;
	
}
