package server;

public class Main {

	//TO DO
	// akcpetowac tylko roznych uzytkownikow
	// dodac port i adres hosta do GUI
	// podlaczyc port i adres
	
	public static void main(String[] args) 
	{
		int portNumber = 1500;
		Server server = new Server(portNumber);	
		server.enable();
	}

}
