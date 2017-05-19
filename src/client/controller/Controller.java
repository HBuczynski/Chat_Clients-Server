package client.controller;

import java.awt.event.*;

import client.model.Model;
import client.view.View;

/**
 * Controller manages a connection between model and view.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Controller 
{

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
		
	class SendButton implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 model_.redirectMessageToServer(view_.getMessage(), view_.destinationUser());
			 view_.setAppendMessage(view_.getMessage(), view_.getUsername());
			 view_.clearUserArea();
		 }
	}
	
	class ConnectButton implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 if(!(view_.getUsername().contains(" ") || view_.getUsername().length() == 0))
			 {
				view_.setUsername();
				 //TO DO
				 // Connection with server
				model_.setConnectionWithServer(view_.getUsername());
				//view_.setTextMessage(" >> Connection with " + view_.getUsername() + " was established :)\n");
			 }
			 else
			 {
				 view_.setTextMessage(" >>  Connection is not established. "	+ "\n >>  Please write correct username and push the connect button.");
			 }
		 }
	}
	
	class DisconnectButton implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 
		 }
	}
	
//	class NewConversation implements MouseListener{
//		public void mouseClicked (MouseEvent e) {
//			
//		}
//	}
			
	private Model model_;
	private View view_;
}
