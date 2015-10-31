package project3;

import javax.swing.*;

public class BankApplication {
	public static void main(String [] args)
	{
		//Panels already present in BankGUI Frame
		BankGUI frame = new BankGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setJMenuBar(frame.menuBar);
	}

}
