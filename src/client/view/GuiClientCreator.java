package client.view;

import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * GuiClientCreator consists all methods necessary to create GUI for client .
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class GuiClientCreator 
{
	public GuiClientCreator(Gui gui)
	{
		guiObjects = gui;
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
		guiObjects.mainFrame = new JFrame("Chatu-chatu");
		guiObjects.mainFrame.setSize(800, 700);
		guiObjects.mainFrame.setResizable(false);
		guiObjects.mainFrame.setLocationRelativeTo(null);
		guiObjects.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("C:\\Users\\Hubert\\Documents\\Programowanie\\Java\\ClientsServer_Chat\\src\\client\\view\\images\\icon.jpg");
		guiObjects.mainFrame.setIconImage(icon.getImage());
		
		guiObjects.panel = new JPanel();
		guiObjects.panel.setOpaque(true);
		guiObjects.panel.setBackground(Color.lightGray);
		guiObjects.panel.setLayout(null);
		
		guiObjects.mainFrame.setContentPane(guiObjects.panel);
	}
	
	private void initializeNorthPanel()
	{
		guiObjects.userLabel = new JLabel("USERNAME :");
		guiObjects.userLabel.setSize(120, 35);
		guiObjects.userLabel.setLocation(30, 10);
		guiObjects.userLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects.usernameField = new JTextField();
		guiObjects.usernameField.setSize(160, 30);
		guiObjects.usernameField.setLocation(140, 15);
		guiObjects.usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects.hostLabel = new JLabel("HOST :");
		guiObjects.hostLabel.setSize(60, 35);
		guiObjects.hostLabel.setLocation(340, 10);
		guiObjects.hostLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects.hostField = new JTextField("localhost");
		guiObjects.hostField.setSize(120, 30);
		guiObjects.hostField.setLocation(410, 15);
		guiObjects.hostField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects.portLabel = new JLabel("PORT :");
		guiObjects.portLabel.setSize(60, 35);
		guiObjects.portLabel.setLocation(560, 10);
		guiObjects.portLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects.portField = new JTextField("1500");
		guiObjects.portField.setSize(120, 30);
		guiObjects.portField.setLocation(635, 15);
		guiObjects.portField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects.connect = new JButton("CONNECT");
		guiObjects.connect.setSize(110, 25);
		guiObjects.connect.setLocation(330, 57);
		
		guiObjects.disconnect = new JButton("DISCONNECT");
		guiObjects.disconnect.setEnabled(false);
		guiObjects.disconnect.setSize(110, 25);
		guiObjects.disconnect.setLocation(445, 57);
		
		guiObjects.onlineUsers = new JLabel("__ONLINE USERS__");
		guiObjects.onlineUsers.setSize(160, 35);
		guiObjects.onlineUsers.setLocation(590, 60);
		guiObjects.onlineUsers.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects.panel.add(guiObjects.userLabel);
		guiObjects.panel.add(guiObjects.usernameField);
		guiObjects.panel.add(guiObjects.connect);
		guiObjects.panel.add(guiObjects.disconnect);
		guiObjects.panel.add(guiObjects.onlineUsers);
		guiObjects.panel.add(guiObjects.hostLabel);
		guiObjects.panel.add(guiObjects.hostField);
		guiObjects.panel.add(guiObjects.portLabel);
		guiObjects.panel.add(guiObjects.portField);
	}
	
	private void initializeCenterPanel()
	{
		guiObjects.conversations = new JTabbedPane();
		guiObjects.conversationMap = new HashMap<String, JTextArea>();
		guiObjects.conversations.setBounds(30, 70, 525, 350);	
		guiObjects.panel.add(guiObjects.conversations);
	}
	
	private void initializeEastPanel()
	{
		guiObjects.usersPanel = new JPanel();
		guiObjects.usersPanel.setLayout(new BoxLayout(guiObjects.usersPanel, BoxLayout.PAGE_AXIS));
		guiObjects.usersPanel.setBackground(Color.WHITE);
		
		guiObjects.userScroll = new JScrollPane(guiObjects.usersPanel);
		guiObjects.userScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		guiObjects.userScroll.setBounds(580, 90, 180, 290);		
		guiObjects.panel.add(guiObjects.userScroll);
		
		guiObjects.endConversation = new JButton("END CONVERSATION");
		guiObjects.endConversation.setSize(180,30);
		guiObjects.endConversation.setLocation(580, 390);
		guiObjects.panel.add(guiObjects.endConversation);
	}
	
	private void initializeSouthPanel()
	{
		guiObjects.send = new JButton("SEND");
		guiObjects.send.setSize(110, 27);
		guiObjects.send.setLocation(650, 618);
		
		guiObjects.sendArea = new JTextArea();
		guiObjects.sendArea.setLineWrap(true);
		guiObjects.sendArea.setEditable(true);
		guiObjects.sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
		guiObjects.sendArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		guiObjects.sendScroll = new JScrollPane(guiObjects.sendArea);
		guiObjects.sendScroll.setBounds(30, 435, 730, 180);

		guiObjects.panel.add(guiObjects.sendScroll);
		guiObjects.panel.add(guiObjects.send);
	}
	
	private Gui guiObjects;
}
