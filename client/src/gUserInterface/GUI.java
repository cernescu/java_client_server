package gUserInterface;

import javax.swing.*;
import java.awt.*;

public class GUI implements Runnable{
	public JFrame mainWindow_ = null; 
	
	public GUI() {
		mainWindow_ = new JFrame("Client");
		mainWindow_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow_.setMinimumSize(new Dimension(600,400));
		mainWindow_.setResizable(false);
	}
	

	private void startGUI() {
		
				
		mainWindow_.pack();
		mainWindow_.setVisible(true);
	}
	
	@Override
	public void run() {
		System.out.println("Started GUI thread!");
		startGUI();
	}
	
}
