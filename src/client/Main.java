package client;
import client.model.*;
import client.view.*;
import client.controller.*;
/**
 * Main class of the project.
 * In main function MVC project pattern was distinguished. 
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);		
	}
}
