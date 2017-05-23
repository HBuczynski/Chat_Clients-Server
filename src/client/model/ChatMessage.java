package client.model;
import java.io.*;
import java.util.Vector;

/**
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame.
 * In oder to send messages by socket, it implements Serializable interface.
 * Class was being written based on:
 * http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
 * 
 * @author HBuczynski
 * @version 1.0
 */

/** 
 * Implemetation of serializable interface allows to redirect objects in stream of bytes 
 * into different process or computer via network.
 * 
 */
public class ChatMessage implements Serializable {

	/** 
	 * SerialVersionUID  is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object 
	 * that are compatible with respect to serialization. If the receiver has loaded a class for the object that has a different serialVersionUID than 
	 * that of the corresponding sender's class, then deserialization will result in an  InvalidClassException.
	 * 
	 */
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

