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
		guiObjects_.usernameField.setSize(120, 30);
		guiObjects_.usernameField.setLocation(140, 30);
		guiObjects_.usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects_.hostLabel = new JLabel("HOST :");
		guiObjects_.hostLabel.setSize(60, 35);
		guiObjects_.hostLabel.setLocation(270, 25);
		guiObjects_.hostLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.hostField = new JTextField();
		guiObjects_.hostField.setSize(60, 30);
		guiObjects_.hostField.setLocation(330, 30);
		guiObjects_.hostField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects_.portLabel = new JLabel("PORT :");
		guiObjects_.portLabel.setSize(60, 35);
		guiObjects_.portLabel.setLocation(370, 25);
		guiObjects_.portLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.portField = new JTextField();
		guiObjects_.portField.setSize(60, 30);
		guiObjects_.portField.setLocation(450, 30);
		guiObjects_.portField.setFont(new Font("Arial", Font.PLAIN, 16));
		
		guiObjects_.connect = new JButton("CONNECT");
		guiObjects_.connect.setSize(90, 30);
		guiObjects_.connect.setLocation(550, 30);
		
		guiObjects_.disconnect = new JButton("DISCONNECT");
		guiObjects_.disconnect.setEnabled(false);
		guiObjects_.disconnect.setSize(110, 30);
		guiObjects_.disconnect.setLocation(645, 30);
		
		guiObjects_.onlineUsers = new JLabel("__ONLINE USERS__");
		guiObjects_.onlineUsers.setSize(160, 35);
		guiObjects_.onlineUsers.setLocation(590, 60);
		guiObjects_.onlineUsers.setFont(new Font("Arial", Font.BOLD, 16));
		
		guiObjects_.panel.add(guiObjects_.userLabel);
		guiObjects_.panel.add(guiObjects_.usernameField);
		guiObjects_.panel.add(guiObjects_.connect);
		guiObjects_.panel.add(guiObjects_.disconnect);
		guiObjects_.panel.add(guiObjects_.onlineUsers);
		guiObjects_.panel.add(guiObjects_.hostLabel);
		guiObjects_.panel.add(guiObjects_.hostField);
		guiObjects_.panel.add(guiObjects_.portLabel);
		guiObjects_.panel.add(guiObjects_.portField);
	}
	
	private void initializeCenterPanel()
	{
		guiObjects_.conversations = new JTabbedPane();
		guiObjects_.conversationMap = new HashMap<String, JTextArea>();
		guiObjects_.conversations.setBounds(30, 70, 525, 350);	
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
