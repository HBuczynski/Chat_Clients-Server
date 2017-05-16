package view;

import java.awt.Color;
import java.io.PrintWriter;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import view.Gui;

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
		initializeMainWindow();
		initializeNorthPanel();
		initializeCenterPanel();
		initializeEastPanel();
		initializeSouthPanel();
	}
	
	private void initializeMainWindow()
	{
		guiObjects_.mainFrame = new JFrame("Chatu-chatu");
		guiObjects_.mainFrame.setSize(800, 700);
		guiObjects_.mainFrame.setLocationRelativeTo(null);
		guiObjects_.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		guiObjects_.usernameField = new JTextField(" ");
		guiObjects_.usernameField.setSize(140, 30);
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
		guiObjects_.onlineUsers.setLocation(600, 30);
		guiObjects_.onlineUsers.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.panel.add(guiObjects_.userLabel);
		guiObjects_.panel.add(guiObjects_.usernameField);
		guiObjects_.panel.add(guiObjects_.connect);
		guiObjects_.panel.add(guiObjects_.disconnect);
		guiObjects_.panel.add(guiObjects_.onlineUsers);
	}
	
	private void initializeCenterPanel()
	{
		guiObjects_.conversationArea = new JTextArea();
		guiObjects_.conversationArea.setLineWrap(true);
		guiObjects_.conversationArea.setBounds(30, 70, 525, 350);
		guiObjects_.conversationArea.setText(" >> ");
		guiObjects_.conversationArea.setEditable(false);
		guiObjects_.conversationArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.conversationArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects_.panel.add(guiObjects_.conversationArea);
	}
	
	private void initializeEastPanel()
	{
		guiObjects_.userArea = new JTextArea();
		guiObjects_.userArea.setLineWrap(true);
		guiObjects_.userArea.setBounds(580, 70, 180, 350);
		guiObjects_.userArea.setEditable(false);
		guiObjects_.userArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.userArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
		guiObjects_.panel.add(guiObjects_.userArea);
	}
	
	private void initializeSouthPanel()
	{
		guiObjects_.send = new JButton("SEND");
		guiObjects_.send.setSize(110, 30);
		guiObjects_.send.setLocation(650, 605);
		
		guiObjects_.sendArea = new JTextArea();
		guiObjects_.sendArea.setLineWrap(true);
		guiObjects_.sendArea.setBounds(30, 435, 730, 150);
		guiObjects_.sendArea.setEditable(true);
		guiObjects_.sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects_.sendArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects_.panel.add( guiObjects_.sendArea);
		guiObjects_.panel.add(guiObjects_.send);
		
	}
			
	public void showMainWindow()
	{
		guiObjects_.mainFrame.setVisible(true);
	}
	
	private Gui guiObjects_;
	
}
