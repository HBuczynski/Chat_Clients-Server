package server;

public class Main {

	//TO DO
	// akcpetowac tylko roznych uzytkownikow
	// dodac port i adres hosta do GUI
	// usunac uml
	// zrefaktoryzowac klasy
	
	public static void main(String[] args) 
	{
		int portNumber = 1500;
		Server server = new Server(portNumber);	
		server.enable();
	}

}
