package client.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.*;

import javax.swing.JButton;
import client.view.*;

/**
 * Gui consists all objects occurring in the interface.
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Gui {
	public JFrame mainFrame;
	public JLabel userLabel;
	public JLabel onlineUsers;
	public JTextField usernameField;
	public JPanel panel;
	
	public JButton connect;
	public JButton disconnect;
	public JButton send;
	public JButton endConversation;
	
	public JTabbedPane conversations;
	public JTextArea userArea;
	public JTextArea sendArea;
	
	public JScrollPane sendScroll;
	public JScrollPane userScroll;
	public JScrollPane conversationScroll;
	
	public Map<String, JTextArea> conversationMap;
	
}
