import model.Model;
import view.View;
import controller.Controller;

/**
 * Main class of the project.
 * In main function MVC project pattern was distinguished. 
 * 
 * @author HBuczynski
 * @version 1.0
 */

public class Main {

	public static void main(String[] args) {
		Model model_ = new Model();
		View view_ = new View();
		Controller controller_ = new Controller(model_, view_);		
	}
}
