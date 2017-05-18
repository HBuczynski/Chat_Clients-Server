package client.view;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.view.*;

public class GuiCreator {

	public GuiCreator(Gui gui)
	{
		guiObjects_ = gui;
		
		initialize();
	}
	
	public void initialize()
	{
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
		//addNewConversationTab("Server");
		
		guiObjects_.panel.add(guiObjects_.conversations);
	}
	
	private void initializeEastPanel()
	{
		guiObjects_.usersPanel = new JPanel();
		guiObjects_.usersPanel.setLayout(new BoxLayout(guiObjects_.usersPanel, BoxLayout.PAGE_AXIS));
		guiObjects_.usersPanel.setBackground(Color.WHITE);
		
		guiObjects_.userScroll = new JScrollPane(guiObjects_.usersPanel);
		guiObjects_.userScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		guiObjects_.userScroll.setBounds(580, 90, 180, 290);		
		guiObjects_.panel.add(guiObjects_.userScroll);
		
		guiObjects_.endConversation = new JButton("END CONVERSATION");
		guiObjects_.endConversation.setSize(180,30);
		guiObjects_.endConversation.setLocation(580, 390);
		guiObjects_.panel.add(guiObjects_.endConversation);
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
	
	private Gui guiObjects_;
}
