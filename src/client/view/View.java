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

public class View 
{	
	private Gui guiObjects;
	private GuiClientCreator guiCreator;
	private String username;
	private boolean connectionIsEstablished;
	private Vector<String> usersVector;
	
	public View()
	{
		initializeVariables();
		initializeComponents();
		createServerTab();
		showMainWindow();
	}
	
	private void initializeVariables()
	{
		guiObjects = new Gui();
		guiCreator = new GuiClientCreator(guiObjects);
		usersVector = new Vector<String>();
		
		connectionIsEstablished = false;
	}
	
	/**
	 * Initialize GUI and action listeners.
	 */
	private void initializeComponents()
	{
		guiCreator.initialize();
		this.conversationButtonAcion();			
	}
	
	/**
	 * Basic tab consists information from server.
	 */
	private void createServerTab()
	{
		addNewConversationTab("Server");
	}
	
	/**
	 * If new message is come, the new tab is created and the view is switched into this component.
	 * @param userName
	 */
	private void addNewConversationTab(String userName)
	{	
		guiObjects.conversationMap.put(userName, new JTextArea());
		guiObjects.conversationMap.get(userName).setLineWrap(true);	
		guiObjects.conversationMap.get(userName).setEditable(false);
		guiObjects.conversationMap.get(userName).setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects.conversationMap.get(userName).setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects.conversations.addTab(userName, new JScrollPane(guiObjects.conversationMap.get(userName)));
		int index = guiObjects.conversations.indexOfTab(userName);
		guiObjects.conversations.setSelectedIndex(index);
	}
	
	/**
	 * Method adds new users to vector.
	 * @param name
	 */
	public void addUsersToList(String name)
	{
		if(!name.equals(username))
		{
			usersVector.add(name);
			addUsersToPanel();
		}
	}
	
	/**
	 * Specific user is removed from users list.
	 * @param name
	 */
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
	
	public void clearUSerPanel()
	{
		guiObjects.usersPanel.removeAll();
		guiObjects.usersPanel.revalidate();
		guiObjects.usersPanel.repaint();
	}
	
	/**
	 * Users are added to panel situated on the left side of UI.
	 */
	private void addUsersToPanel()
	{
		MouseAdapterMod mouseAdapter = new MouseAdapterMod();
		
		guiObjects.usersPanel.removeAll();
		guiObjects.usersPanel.revalidate();
		
		for(int i=0; i < usersVector.size(); ++i)
		{
			JLabel newUser = new JLabel("  #  " + usersVector.get(i));
			
			newUser.setName(usersVector.get(i));
			newUser.setSize(140, 30);
			newUser.addMouseListener(mouseAdapter);
			newUser.setFont(new Font("Arial", Font.PLAIN, 20));
			
			guiObjects.usersPanel.add(newUser);
		}
		guiObjects.usersPanel.repaint();
	}
	
	/**
	 * New message is added to conversation.
	 * @param message
	 * @param user
	 */
	public void setAppendMessage(String message, String user)
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        int index = guiObjects.conversations.getSelectedIndex();
		String name = new String(guiObjects.conversations.getTitleAt(index));
       
        /**
         * Check if the tab exists.
         */
        if((guiObjects.conversationMap.get(user)==null) && !user.equals(username))
        	addNewConversationTab(user);
               
		if(connectionIsEstablished)
		{
			if(user.equals(username))
			{
				guiObjects.conversationMap.get(name).append(" >> "  + sdf.format(cal.getTime()) + "  #" + user  + "\n");
				guiObjects.conversationMap.get(name).append(message + "\n");
			}			
			else
			{
				guiObjects.conversationMap.get(user).append(" >> "  + sdf.format(cal.getTime()) + "  #" + user  + "\n");
				guiObjects.conversationMap.get(user).append(message + "\n");
			}
		}
		else if (guiObjects.conversations.getSelectedIndex() == 0)
		{
			guiObjects.conversationMap.get("Server").append(" >> " + " Oh my dear, you cannot write with server !" + "\n");
		}
		else
		{
			setTextMessage(" >> Connection is not established. "+ "\n >>  Please write your username and push the connect button.");
		}
	}
	
	public void setTextMessage(String message)
	{
		guiObjects.conversationMap.get("Server").setText(message);
	}
	
	public String getMessage()
	{
		return guiObjects.sendArea.getText();
	}
	
	public void clearUserArea()
	{
		guiObjects.sendArea.setText("");
	}
	
	public String getUsername()
	{
		return guiObjects.usernameField.getText();
	}
	
	public String getHostName()
	{
		return guiObjects.hostField.getText();
	}
	
	public String getPortName()
	{
		return guiObjects.portField.getText();
	}
	
	public String destinationUser()
	{
		int index = guiObjects.conversations.getSelectedIndex();
		String name = new String(guiObjects.conversations.getTitleAt(index));
		return name;
	}
	
	public void setUsername()
	{
		username = getUsername();
		guiObjects.usernameField.setEditable(false);
		guiObjects.hostField.setEditable(false);
		guiObjects.portField.setEditable(false);
		connectionIsEstablished = true;
		guiObjects.disconnect.setEnabled(true);
	}
	
	public void disableConnection()
	{
		guiObjects.usernameField.setEditable(true);
		connectionIsEstablished = false;
		guiObjects.disconnect.setEnabled(true);
	}
	
	public void loggAgain()
	{
		guiObjects.usernameField.setEditable(true);
		guiObjects.hostField.setEditable(true);
		guiObjects.portField.setEditable(true);
		guiObjects.usernameField.setText("");
		
		guiObjects.disconnect.setEnabled(false);
	}
				
	public void showMainWindow()
	{
		guiObjects.mainFrame.setVisible(true);
	}
	
	private void conversationButtonAcion()
	{
		guiObjects.endConversation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(guiObjects.conversations.getSelectedIndex() != guiObjects.conversations.indexOfTab("Server"))
				{			
					int index = guiObjects.conversations.getSelectedIndex();
					String name = new String(guiObjects.conversations.getTitleAt(index));
					
					guiObjects.conversationMap.remove(name);
					guiObjects.conversations.remove(guiObjects.conversations.getSelectedIndex());
				}
			}
		});
	}
	
	public void sendButtonListener(ActionListener listenForSendButton)
	{
		guiObjects.send.addActionListener(listenForSendButton);
	}
	
	public void connectButtonListener(ActionListener act)
	{
		guiObjects.connect.addActionListener(act);
	}
	
	public void disconnectButtonListener(ActionListener act)
	{
		guiObjects.disconnect.addActionListener(act);
	}
	
	private class MouseAdapterMod extends MouseAdapter 
	{   
		public void mousePressed(MouseEvent e) 
		{	
	       JLabel label = (JLabel)e.getSource();
	       
	       if(!guiObjects.conversationMap.containsKey(label.getName()))
	       {
	    	   	addNewConversationTab(label.getName());
	       }
	   }
	}	
}
