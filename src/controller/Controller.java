package controller;

import java.awt.event.ActionListener;

import model.Model;
import view.View;

/**
 * Controller manages a connection between model and view.
 * 
 * @author HBuczynski
 * @version 1.0
 */
public class Controller 
{

	public Controller(Model model, View view)
	{
		model_ = model;
		view_ = view;
		
		initializeListeners();
	}
	
	private void initializeListeners()
	{
		
	}
		
	private Model model_;
	private View view_;
}
