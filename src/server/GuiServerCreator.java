package server;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuiServerCreator extends JFrame
{
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	
	public GuiServerCreator()
	{
		initialize();
		makeWindowVisible();
	}
	
	void initialize()
	{
		initializeMainWindow();
		initializeComponents();
		initializeMessage();
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
	
	public void addMessage(String message)
	{
		textArea.append(" >> " + message + "\n");
	}
	
	public void makeWindowVisible()
	{
		this.setVisible(true);
	}
}
