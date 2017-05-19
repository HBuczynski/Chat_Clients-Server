package client.view;

import java.awt.Color;
import java.util.Calendar;
import java.util.Vector;
import java.awt.Font;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.*;

/**
 * View consists components responsible for communication with user.
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class View {
	
	public View()
	{
		initializeVariables();
		initializeComponents();
		createServerTab();
		showMainWindow();
		
		//test
		
//		addUsersToList("Kazik");
//		addUsersToList("Karol");
//		addUsersToList("Staszek");
	}
	
	private void initializeVariables()
	{
		guiObjects_ = new Gui();
		guiCreator_ = new GuiCreator(guiObjects_);
		usersVector = new Vector<String>();
		
		connectionIsEstablished = false;
	}
	
	private void initializeComponents()
	{
		guiCreator_.initialize();
		this.conversationButtonAcion();			
	}
	
	private void createServerTab()
	{
		addNewConversationTab("Server");
	}
	
	private void addNewConversationTab(String userName)
	{	
		guiObjects_.conversationMap.put(userName, new JTextArea());
		guiObjects_.conversationMap.get(userName).setLineWrap(true);
		
		if(userName.equals("Server"))
				guiObjects_.conversationMap.get(userName).setText(" >> ");
		
		guiObjects_.conversationMap.get(userName).setEditable(false);
		guiObjects_.conversationMap.get(userName).setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.conversationMap.get(userName).setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects_.conversations.addTab(userName, new JScrollPane(guiObjects_.conversationMap.get(userName)));
	}
	
	public void addUsersToList(String name)
	{
		if(!name.equals(username))
		{
			usersVector.add(name);
			addUsersToPanel();
		}
	}
	
	public void removeUserFromList(String name)
	{
		int index = usersVector.indexOf(name);
		usersVector.remove(index);
		addUsersToPanel();
	}
	
	public void removeUsersList()
	{
		usersVector.removeAllElements();
	}
	
	private void addUsersToPanel()
	{
		MouseAdapterMod mouseAdapter = new MouseAdapterMod();
		
		guiObjects_.usersPanel.removeAll();
		guiObjects_.usersPanel.revalidate();
		
		for(int i=0; i < usersVector.size(); ++i)
		{
			JLabel newUser = new JLabel("  #  " + usersVector.get(i));
			
			newUser.setName(usersVector.get(i));
			newUser.setSize(140, 30);
			newUser.addMouseListener(mouseAdapter);
			newUser.setFont(new Font("Arial", Font.PLAIN, 20));
			
			guiObjects_.usersPanel.add(newUser);
		}
		
		guiObjects_.usersPanel.repaint();
	}
	
	public void setAppendMessage(String message, String user)
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        //check if the tab exists
        if(!guiObjects_.conversationMap.containsKey(user) && !user.equals(username))
        	addNewConversationTab(user);
        
        
        
		if(connectionIsEstablished)// && guiObjects_.conversations.getSelectedIndex() !=0)
		{
			if(user.equals(username))
			{
//				int index = guiObjects_.conversations.getSelectedIndex();
//				String name = new String(guiObjects_.conversations.getTitleAt(index));
//				guiObjects_.conversationMap.get(name).append(" >> "  + sdf.format(cal.getTime()) + "  #" + user  + "\n");
//				guiObjects_.conversationMap.get(name).append(message + "\n");
			}			
			else
			{
				guiObjects_.conversationMap.get(user).append(" >> "  + sdf.format(cal.getTime()) + "  #" + user  + "\n");
				guiObjects_.conversationMap.get(user).append(message + "\n");
			}
		}
		else if (guiObjects_.conversations.getSelectedIndex() == 0)
		{
			guiObjects_.conversationMap.get("Server").append(" >> " + " Oh my dear, you cannot write with server !" + "\n");
		}
		else
		{
			setTextMessage(" >> Connection is not established. "+ "\n >>  Please write your username and push the connect button.");
		}
	}
	
	public void setTextMessage(String message)
	{
		guiObjects_.conversationMap.get("Server").setText(message);
	}
	
	public String getMessage()
	{
		return guiObjects_.sendArea.getText();
	}
	
	public void clearUserArea()
	{
		guiObjects_.sendArea.setText("");
	}
	
	public String getUsername()
	{
		return guiObjects_.usernameField.getText();
	}
	
	public String destinationUser()
	{
		int index = guiObjects_.conversations.getSelectedIndex();
		String name = new String(guiObjects_.conversations.getTitleAt(index));
		return name;
	}
	
	public void setUsername()
	{
		username = getUsername();
		guiObjects_.usernameField.setEditable(false);
		connectionIsEstablished = true;
	}
				
	public void showMainWindow()
	{
		guiObjects_.mainFrame.setVisible(true);
	}
	
	private void conversationButtonAcion()
	{
		guiObjects_.endConversation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(guiObjects_.conversations.getSelectedIndex() != guiObjects_.conversations.indexOfTab("Server"))
				{			
					int index = guiObjects_.conversations.getSelectedIndex();
					String name = new String(guiObjects_.conversations.getTitleAt(index));
					guiObjects_.conversationMap.remove(name);
					
					guiObjects_.conversations.remove(guiObjects_.conversations.getSelectedIndex());
				}
			}
		});
	}
	
	public void sendButtonListener(ActionListener listenForSendButton)
	{
		guiObjects_.send.addActionListener(listenForSendButton);
	}
	
	public void connectButtonListener(ActionListener act)
	{
		guiObjects_.connect.addActionListener(act);
	}
	
	public void disconnectButtonListener(ActionListener act)
	{
		guiObjects_.connect.addActionListener(act);
	}
	
	private class MouseAdapterMod extends MouseAdapter {
	   
		public void mousePressed(MouseEvent e) {
			
	       JLabel label = (JLabel)e.getSource();
	       
	       if(!guiObjects_.conversationMap.containsKey(label.getName()))
	       {
	    	   	addNewConversationTab(label.getName());
	       }
	   }
	}
	
	private Gui guiObjects_;
	private GuiCreator guiCreator_;
	private String username;
	private boolean connectionIsEstablished;
	private Vector<String> usersVector;
	
}
