package server;

public class Main {

	public static void main(String[] args) 
	{
		int portNumber = 1500;
		Server server = new Server(portNumber);
		
		server.enable();
	}

}
