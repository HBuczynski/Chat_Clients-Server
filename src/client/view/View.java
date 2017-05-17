package client.view;

import java.awt.Color;
import java.io.File;

import java.util.HashMap;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.view.Gui;


/**
 * View consists components responsible for communication with user.
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class View {
	
	public View()
	{
		initializeComponents();
		showMainWindow();
	}
	
	private void initializeComponents()
	{
		guiObjects_ = new Gui();
		connectionIsEstablished = false;
		
		initializeMainWindow();
		initializeNorthPanel();
		initializeCenterPanel();
		initializeEastPanel();
		initializeSouthPanel();
		
		//test
		addNewConversationTab("Kazik");
	}
	
	private void initializeMainWindow()
	{
		guiObjects_.mainFrame = new JFrame("Chatu-chatu");
		guiObjects_.mainFrame.setSize(800, 700);
		guiObjects_.mainFrame.setLocationRelativeTo(null);
		guiObjects_.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TO DO - make icon visible
		//System.out.println(new File("C:\\Users\\Hubert\\Documents\\Programowanie\\Java\\ClientsServer_Chat\\src\\client\\view\\images\\icon.jpg").exists());
		ImageIcon icon = new ImageIcon("C:\\Users\\Hubert\\Documents\\Programowanie\\Java\\ClientsServer_Chat\\src\\client\\view\\images\\icon.jpg");
		guiObjects_.mainFrame.setIconImage(icon.getImage());
		
		guiObjects_.panel = new JPanel();
		guiObjects_.panel.setOpaque(true);
		guiObjects_.panel.setBackground(Color.lightGray);
		guiObjects_.panel.setLayout(null);
		
		guiObjects_.mainFrame.setContentPane(guiObjects_.panel);
	}
	
	private void initializeNorthPanel()
	{
		guiObjects_.userLabel = new JLabel("USERNAME :");
		guiObjects_.userLabel.setSize(120, 35);
		guiObjects_.userLabel.setLocation(30, 25);
		guiObjects_.userLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.usernameField = new JTextField();
		guiObjects_.usernameField.setSize(160, 30);
		guiObjects_.usernameField.setLocation(140, 30);
		guiObjects_.usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects_.connect = new JButton("CONNECT");
		guiObjects_.connect.setSize(110, 30);
		guiObjects_.connect.setLocation(320, 30);
		
		guiObjects_.disconnect = new JButton("DISCONNECT");
		guiObjects_.disconnect.setSize(110, 30);
		guiObjects_.disconnect.setLocation(445, 30);
		
		guiObjects_.onlineUsers = new JLabel("ONLINE USERS");
		guiObjects_.onlineUsers.setSize(140, 35);
		guiObjects_.onlineUsers.setLocation(610, 60);
		guiObjects_.onlineUsers.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.panel.add(guiObjects_.userLabel);
		guiObjects_.panel.add(guiObjects_.usernameField);
		guiObjects_.panel.add(guiObjects_.connect);
		guiObjects_.panel.add(guiObjects_.disconnect);
		guiObjects_.panel.add(guiObjects_.onlineUsers);
	}
	
	private void initializeCenterPanel()
	{
		guiObjects_.conversations = new JTabbedPane();
		guiObjects_.conversationMap = new HashMap<String, JTextArea>();
		guiObjects_.conversations.setBounds(30, 70, 525, 350);
		addNewConversationTab("Server");
		
		guiObjects_.panel.add(guiObjects_.conversations);
	}
	
	private void initializeEastPanel()
	{
		guiObjects_.userArea = new JTextArea();
		guiObjects_.userArea.setLineWrap(true);
		guiObjects_.userArea.setEditable(false);
		guiObjects_.userArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.userArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		guiObjects_.userScroll = new JScrollPane(guiObjects_.userArea);
		guiObjects_.userScroll.setBounds(580, 90, 180, 290);
		
		endConversationButton();
					
		guiObjects_.panel.add(guiObjects_.userScroll);
		
	}
	
	private void initializeSouthPanel()
	{
		guiObjects_.send = new JButton("SEND");
		guiObjects_.send.setSize(110, 27);
		guiObjects_.send.setLocation(650, 618);
		
		guiObjects_.sendArea = new JTextArea();
		guiObjects_.sendArea.setLineWrap(true);
		guiObjects_.sendArea.setEditable(true);
		guiObjects_.sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.sendArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects_.sendScroll = new JScrollPane(guiObjects_.sendArea);
		guiObjects_.sendScroll.setBounds(30, 435, 730, 180);

		guiObjects_.panel.add(guiObjects_.sendScroll);
		guiObjects_.panel.add(guiObjects_.send);
	}
	
	private void addNewConversationTab(String userName)
	{	
		guiObjects_.conversationMap.put(userName, new JTextArea());
		guiObjects_.conversationMap.get(userName).setLineWrap(true);
		guiObjects_.conversationMap.get(userName).setText(" >> ");
		guiObjects_.conversationMap.get(userName).setEditable(false);
		guiObjects_.conversationMap.get(userName).setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.conversationMap.get(userName).setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects_.conversations.addTab(userName, new JScrollPane(guiObjects_.conversationMap.get(userName)));
	}
	
	public String getMessage()
	{
		return guiObjects_.sendArea.getText();
	}
	
	public void setAppendMessage(String message, String user)
	{
		if(connectionIsEstablished)
		{
			if(user.equals(username))
			{
				int index = guiObjects_.conversations.getSelectedIndex();
				String name = guiObjects_.conversations.getTabComponentAt(index).getName();
				//guiObjects_.conversationMap.get(name).setText(" >> " + user);
				//guiObjects_.conversationMap.get(name).setText("\t" + message);
			}			
			else
			{
				guiObjects_.conversationMap.get("Server").append(" >> " + user +"\n");
				guiObjects_.conversationMap.get("Server").append(message + "\n");
			}
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
	
	public void clearUserArea()
	{
		guiObjects_.userArea.setText(" ");
	}
	
	public String getUsername()
	{
		return guiObjects_.usernameField.getText();
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
	
	private void endConversationButton()
	{
		guiObjects_.endConversation = new JButton("END CONVERSATION");
		guiObjects_.endConversation.setSize(180,30);
		guiObjects_.endConversation.setLocation(580, 390);
		guiObjects_.panel.add(guiObjects_.endConversation);
		
		guiObjects_.endConversation.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(guiObjects_.conversations.getSelectedIndex() != guiObjects_.conversations.indexOfTab("Server"))
					guiObjects_.conversations.remove(guiObjects_.conversations.getSelectedIndex());
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
	
	private Gui guiObjects_;
	private boolean connectionIsEstablished;
	private String username;
	
}
