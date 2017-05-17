package server;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Server manages connections between users. This part of application is responsible for authorized users and redirection
 * information between them.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Server extends JFrame
{
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	
	public Server()
	{
		initializeMainWindow();
		initializeComponents();
		initializeMessage();
	}
	
	public void makeWindowVisible()
	{
		this.setVisible(true);
	}
	
	private void initializeMainWindow()
	{
		this.setTitle("Server Output");
		this.setSize(500, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void initializeComponents()
	{
		panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.lightGray);
		panel.setLayout(null);
		this.setContentPane(panel);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		scroll = new JScrollPane(textArea);
		scroll.setBounds(1, 2, 479, 750);
		
		panel.add(scroll);
	}
	
	private void initializeMessage()
	{
		addMessage("Initialization has been successful.");
		addMessage("Waiting on users ...");
		addMessage(" ");
	}
	
	private void addMessage(String message)
	{
		textArea.append(" >> " + message + "\n");
	}
	
	public void run()
	{
		
	}
	
	public void sendMessageToClient()
	{
		
	}

	public void getMessageFromClient()
	{
		
	}
}
