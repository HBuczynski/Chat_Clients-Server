package client.controller;

import java.awt.event.*;

import client.model.Model;
import client.view.View;

/**
 * Controller manages a connection between model and view. This class consists object of model and view.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Controller 
{
	private Model model;
	private View view;
	
	public Controller(Model model, View view)
	{
		this.model = model;
		this.view = view;
		this.model.setView(this.view);
		
		initizializeListeneres();
	}
	
	private void initizializeListeneres()
	{
		view.connectButtonListener(new ConnectButton());
		view.sendButtonListener(new SendButton());
		view.disconnectButtonListener(new DisconnectButton());
	}
		
	/**
	 *  If send button is clicked the appropriate methods are performed
	 */
	class SendButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 /**
			  * Send message to server via model class which represents the client.
			  */
			 model.redirectMessageToServer(view.getMessage(), view.destinationUser());
			 /**
			  * Redirect message from bottom text field to top conversation area.
			  */
			 view.setAppendMessage(view.getMessage(), view.getUsername());
			 /**
			  * Clear bottom text area after send button has been clicked.
			  */
			 view.clearUserArea();
		 }
	}
	
	class ConnectButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 /**
			  * variable checks if the fields are filled correctly
			  */
			 boolean connectionCondition = true;
			 connectionCondition = connectionCondition && (!(view.getUsername().contains(" ") || view.getUsername().length() == 0));
			 connectionCondition = connectionCondition && (!(view.getHostName().contains(" ") || view.getHostName().length() == 0));
			 connectionCondition = connectionCondition && (!(view.getPortName().contains(" ") || view.getPortName().length() == 0));
			 			
			 try
			 {
				 int number = Integer.parseInt(view.getPortName());
				 if(connectionCondition)
				 {
					/**
					 * Username and other fields should be disabled.
					 */
					view.setUsername();
					model.setConnectionWithServer(view.getUsername(), view.getHostName(), number);
				 }
				 else
				 {
					 view.setTextMessage(" >>  Connection is not established. "	+ "\n >>  Please fill fields and push the connect button.\n");
				 }
			 }
			 catch(NumberFormatException nfe)
			 {
				 view.setTextMessage(" >>  Connection is not established. "	+ "\n >>  Please port's field has to contain number.\n");
			 }
		 }
	}
	
	class DisconnectButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 
			 model.disconnect();
			 view.disableConnection();
			 view.clearUSerPanel();
		 }
	}
}
