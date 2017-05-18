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
public class Server 
{
	public Server()
	{
		gui_ = new GuiServerCreator();
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
	
	private GuiServerCreator gui_;;
}
