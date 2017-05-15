package view;

import java.awt.Color;
import java.io.PrintWriter;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
		setVisibleLogDialog(true);
	}
	
	private void initializeComponents()
	{
		guiObjects_ = new Gui();
		initializeMainWindow();
		initializeLogDialog();
		initializeLogComponents();
	}
	
	private void initializeMainWindow()
	{
		guiObjects_.mainFrame = new JFrame("Chatu-chatu");
		guiObjects_.mainFrame.setSize(400, 500);
		guiObjects_.mainFrame.setLocationRelativeTo(null);
		guiObjects_.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initializeLogDialog()
	{
		guiObjects_.logFrame = new JFrame("Log Dialog");
		guiObjects_.logFrame.setSize(350, 220);
		guiObjects_.logFrame.setResizable(false);
		guiObjects_.logFrame.setLocationRelativeTo(null);
		guiObjects_.logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Hubert\\Documents\\Programowanie\\Java\\ClientsServer_Chat\\src\\view\\images\\icon.jpg");
		guiObjects_.logFrame.setIconImage(icon);
		
		guiObjects_.logPanel = new JPanel();
		guiObjects_.logPanel.setOpaque(true);
		guiObjects_.logPanel.setBackground(Color.WHITE);
		guiObjects_.logPanel.setLayout(null);
		
		guiObjects_.logFrame.setContentPane(guiObjects_.logPanel);
	}
	
	private void initializeLogComponents()
	{
		guiObjects_.applyButton = new JButton("APPLY");
		guiObjects_.applyButton.setBounds((guiObjects_.logFrame.getWidth() -146), (guiObjects_.logFrame.getHeight()- 94), 80, 25);
		
		guiObjects_.cancelButton = new JButton("CANCEL");
		guiObjects_.cancelButton.setBounds((guiObjects_.logFrame.getWidth() -246), (guiObjects_.logFrame.getHeight()- 94), 80, 25);
		
		guiObjects_.usernameLabel = new JLabel("USERNAME");
		guiObjects_.usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		guiObjects_.usernameLabel.setBounds((guiObjects_.logFrame.getWidth() -284), (guiObjects_.logFrame.getHeight()- 190), 180, 25);
		
		guiObjects_.usernameField = new JTextField("");
		guiObjects_.usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
		guiObjects_.usernameField.setBounds((guiObjects_.logFrame.getWidth() -284), (guiObjects_.logFrame.getHeight()- 150), 220, 40);
		
		guiObjects_.logPanel.add(guiObjects_.applyButton);
		guiObjects_.logPanel.add(guiObjects_.cancelButton);	
		guiObjects_.logPanel.add(guiObjects_.usernameLabel);
		guiObjects_.logPanel.add(guiObjects_.usernameField);
	}
	
	public void showMainWindow()
	{
		guiObjects_.mainFrame.setVisible(true);
	}
	
	public void setVisibleLogDialog(boolean show)
	{
		guiObjects_.logFrame.setVisible(show);
	}
	
	private Gui guiObjects_;
	
}
