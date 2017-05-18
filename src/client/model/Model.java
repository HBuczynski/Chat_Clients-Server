package client.model;

/**
 * Model is a back-end of application.Its duties include connection with server and gathering
 * information about user interaction from controller.
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Model {
	
	public Model()
	{
		client_ = new Client();
	}

	public void redirectMessageToServer(String message)
	{
		client_.sendToServer(message);
	}
	
	public void setConnectionWithServer(String userName)
	{
		client_.connectToServer(userName);
	}
	
	public void getUpdateUserList()
	{
		
	}
	
	public void getMessageFromServer()
	{
		
	}
	private Client client_;
	
}
