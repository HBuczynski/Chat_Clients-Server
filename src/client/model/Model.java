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
	
	public Model()
	{
		client_ = new Client("localhost", 1500, view_.getUsername(), this);
	}
	
	public void setView(View view)
	{
		view_ = view;
	}
	
	public void getUpdateUserList(Vector<String> users)
	{
		for(int i=0; i < users.size(); ++i)
		{
			view_.addUsersToList(users.get(i));
		}
	}
	
	public void redirectMessageToServer(String message)
	{
		//przekierowanie do strumienia
	}
	
	public void setConnectionWithServer(String userName)
	{
		client_.initialize();
		
		Scanner scan = new Scanner(System.in);
		
		// loop forever for message from the user
		while(true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client_.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				// break to do the disconnect
				break;
			}
			// message WhoIsIn
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				client_.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			}
			else {				// default to ordinary message
				client_.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}
		// done disconnect
		client_.disconnect();
	}
	
	public void setMessageFromServer(String msg, String user)
	{
		view_.setAppendMessage(msg, user);
	}
	
	private Client client_;
	private View view_;
	
}
