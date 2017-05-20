package client.model;
import java.io.*;
import java.util.Vector;

/**
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 * Class was being written based on:
 * http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class ChatMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

	public static final int MESSAGE = 1;
	public static final int LOGOUT = 2;
	public static final int USERLIST = 3;
	
	private String userDestination;
	private String userOrigin;
	private int type;
	private String message;
	
	private Vector<String> userList;
	
	public ChatMessage(int type, String message, String user, String userOrigin) 
	{
		this.type = type;
		this.message = message;
		this.userDestination = user;
		this.userOrigin = userOrigin;
	}
	
	public int getType() 
	{
		return type;
	}
	
	public String getMessage() 
	{
		return message;
	}
	
	public String getDestination()
	{
		return userDestination;
	}
	
	public String getOrigin()
	{
		return userOrigin;
	}
	
	public Vector<String> getUsers()
	{
		return userList;
	}
	
	public void setuserList(Vector<String> list)
	{
		userList = list;
	}
}

