package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

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
	
	public JTextArea conversationArea;
	public JTextArea userArea;
	public JTextArea sendArea;
}
