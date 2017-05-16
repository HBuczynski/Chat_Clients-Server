package view;

import java.awt.Color;
import java.io.PrintWriter;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.JTextArea;

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
		initializeNorthPanel();
		initializeCenterPanel();
		initializeEastPanel();
		initializeSouthPanel();
		showMainWindow();
	}
	
	private void initializeComponents()
	{
		guiObjects_ = new Gui();
		initializeMainWindow();
	}
	
	private void initializeMainWindow()
	{
		guiObjects_.mainFrame = new JFrame("Chatu-chatu");
		guiObjects_.mainFrame.setSize(800, 700);
		guiObjects_.mainFrame.setLocationRelativeTo(null);
		guiObjects_.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		guiObjects_.panel = new JPanel();
		guiObjects_.panel.setOpaque(true);
		guiObjects_.panel.setBackground(Color.WHITE);
		guiObjects_.panel.setLayout(null);
		
		guiObjects_.mainFrame.setContentPane(guiObjects_.panel);
	}
	
	private void initializeNorthPanel()
	{
		guiObjects_.userLabel = new JLabel("USERNAME :");
		guiObjects_.userLabel.setSize(100, 35);
		guiObjects_.userLabel.setLocation(30, 25);
		guiObjects_.userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects_.usernameField = new JTextField(" ");
		guiObjects_.usernameField.setSize(140, 30);
		guiObjects_.usernameField.setLocation(130, 30);
		
		guiObjects_.connect = new JButton("CONNECT");
		guiObjects_.connect.setSize(110, 30);
		guiObjects_.connect.setLocation(300, 30);
		
		guiObjects_.disconnect = new JButton("DISCONNECT");
		guiObjects_.disconnect.setSize(110, 30);
		guiObjects_.disconnect.setLocation(450, 30);
		
		guiObjects_.onlineUsers = new JLabel("Online Users");
		guiObjects_.onlineUsers.setSize(100, 35);
		guiObjects_.onlineUsers.setLocation(620, 30);
		guiObjects_.onlineUsers.setFont(new Font("Arial", Font.PLAIN, 16));
		
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
		guiObjects_.conversationArea.setBounds(90, 90, 500, 400);
		guiObjects_.conversationArea.setText("siemanolo");
		guiObjects_.conversationArea.setBackground(Color.BLUE);
		guiObjects_.conversationArea.setEditable(true);
		guiObjects_.conversationArea.setVisible(true);
		guiObjects_.conversationArea.setOpaque(false);
		guiObjects_.conversationArea.setForeground(Color.white);
		guiObjects_.conversationArea.setCaretColor(Color.red);
		
		guiObjects_.panel.add(guiObjects_.conversationArea);
	}
	
	private void initializeEastPanel()
	{
		
	}
	
	private void initializeSouthPanel()
	{
		guiObjects_.send = new JButton("SEND");
		guiObjects_.send.setSize(110, 30);
		guiObjects_.send.setLocation(450, 590);
		
		guiObjects_.panel.add(guiObjects_.send);
		
	}
			
	public void showMainWindow()
	{
		guiObjects_.mainFrame.setVisible(true);
	}
	
	private Gui guiObjects_;
	
}
