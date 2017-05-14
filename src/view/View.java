package view;

import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
		setVisibleLogDialog(true);
	}
	
	private void initializeComponents()
	{
		initializeMainWindow();
		initializeLogDialog();
	}
	
	private void initializeMainWindow()
	{
		mainFrame_ = new JFrame("Chatu-chatu");
		mainFrame_.setSize(400, 500);
	}
	
	private void initializeLogDialog()
	{
		logFrame_ = new JFrame("Log Dialog");
		logFrame_.setSize(450, 300);
	}
	
	public void showMainWindow()
	{
		mainFrame_.setVisible(true);
	}
	
	public void setVisibleLogDialog(boolean show)
	{
		logFrame_.setVisible(show);
	}
	
	private JFrame mainFrame_;
	private JFrame logFrame_;
	
}
