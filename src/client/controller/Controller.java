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
	private Model model_;
	private View view_;
	
	public Controller(Model model, View view)
	{
		model_ = model;
		view_ = view;
		model.setView(view_);
		
		initizializeListeneres();
	}
	
	private void initizializeListeneres()
	{
		view_.connectButtonListener(new ConnectButton());
		view_.sendButtonListener(new SendButton());
		view_.disconnectButtonListener(new DisconnectButton());
	}
		
	class SendButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 model_.redirectMessageToServer(view_.getMessage(), view_.destinationUser());
			 view_.setAppendMessage(view_.getMessage(), view_.getUsername());
			 view_.clearUserArea();
		 }
	}
	
	class ConnectButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 //variable checks if the fields are filled correctly
			 boolean connectionCondition = true;
			 connectionCondition = connectionCondition && (!(view_.getUsername().contains(" ") || view_.getUsername().length() == 0));
			 connectionCondition = connectionCondition && (!(view_.getHostName().contains(" ") || view_.getHostName().length() == 0));
			 connectionCondition = connectionCondition && (!(view_.getPortName().contains(" ") || view_.getPortName().length() == 0));
			 			
			 try
			 {
				 int number = Integer.parseInt(view_.getPortName());
				 if(connectionCondition)
				 {
					view_.setUsername();
					model_.setConnectionWithServer(view_.getUsername(), view_.getHostName(), number);
				 }
				 else
				 {
					 view_.setTextMessage(" >>  Connection is not established. "	+ "\n >>  Please fill fields and push the connect button.\n");
				 }
			 }
			 catch(NumberFormatException nfe)
			 {
				 view_.setTextMessage(" >>  Connection is not established. "	+ "\n >>  Please port's field has to contain number.\n");
			 }
		 }
	}
	
	class DisconnectButton implements ActionListener
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 model_.disconnect();
			 view_.disableConnection();
			 view_.clearUSerPanel();
		 }
	}
}
