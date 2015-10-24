package project3;

import java.awt.*;

import javax.swing.*;

public class BankGUI extends JFrame {
	private JPanel mainPanel;
	private JPanel tablePanel;
	private JPanel gridLabelPanel;
	private JPanel gridTextPanel;
	private JPanel boxPanel;
	
	private JButton[] modifyButton = new JButton[4];
	private JLabel[] descriptLabels = new JLabel[7];
	private JTextField[] textInputs = new JTextField[7];
	
	private JTable table;
	
	public BankGUI(){
		super("Bank Application");
		int i = 0;
		
		mainPanel = new JPanel();
		tablePanel = new JPanel();
		gridLabelPanel = new JPanel();
		gridTextPanel = new JPanel();
		boxPanel = new JPanel();
		
		for(i = 0; i < 7; i++){
			descriptLabels[i] = new JLabel();
			textInputs[i] = new JTextField();
		}
		for(i = 0; i < 4; i++){
			modifyButton[i] = new JButton();
		}
		
		setup();
		
		
		
		
		
		
		mainPanel.add(tablePanel, BorderLayout.NORTH);
		mainPanel.add(gridLabelPanel, BorderLayout.WEST);
		mainPanel.add(gridTextPanel, BorderLayout.CENTER);
		mainPanel.add(boxPanel, BorderLayout.EAST);
		this.add(mainPanel);
	}
	
	private void setup(){
		int i = 0;
		
		mainPanel.setPreferredSize(new Dimension(500, 500));
		tablePanel.setPreferredSize(new Dimension(500, 250));
		gridLabelPanel.setPreferredSize(new Dimension(125, 250));
		gridTextPanel.setPreferredSize(new Dimension(275, 250));
		boxPanel.setPreferredSize(new Dimension(100, 250));
		
		mainPanel.setLayout(new BorderLayout());
		gridLabelPanel.setLayout(new GridLayout(8, 1));
		gridTextPanel.setLayout(new GridLayout(8, 1));
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		
		tablePanel.setBackground(Color.BLACK);
		boxPanel.setBackground(Color.DARK_GRAY);
		
		modifyButton[0].setText("Add");
		modifyButton[1].setText("Delete");
		modifyButton[2].setText("Update");
		modifyButton[3].setText("Clear");
		
		descriptLabels[0].setText("  Account Number:");
		descriptLabels[1].setText("  Account Owner:");
		descriptLabels[2].setText("  Date Opened:");
		descriptLabels[3].setText("  Account Balance:");
		descriptLabels[4].setText("  Monthly Fee:");
		descriptLabels[5].setText("  Interest Rate:");
		descriptLabels[6].setText("  Minimum Balance:");
		
		//Empty grid cells waiting for radio buttons
		gridLabelPanel.add(new JLabel());
		gridTextPanel.add(new JLabel());
		
		for(i = 0; i < 7; i++){
			gridLabelPanel.add(descriptLabels[i]);
			gridTextPanel.add(textInputs[i]);
		}
		
		//Format box layout
		for(i = 0; i < 4; i++){
			modifyButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			modifyButton[i].setMaximumSize(new Dimension(75, modifyButton[i].getMinimumSize().height));
		}
		boxPanel.add(Box.createRigidArea(new Dimension(1,40)));
		boxPanel.add(modifyButton[0]);
		boxPanel.add(Box.createRigidArea(new Dimension(1,5)));
		boxPanel.add(modifyButton[1]);
		boxPanel.add(Box.createRigidArea(new Dimension(1,5)));
		boxPanel.add(modifyButton[2]);
		boxPanel.add(Box.createRigidArea(new Dimension(1,5)));
		boxPanel.add(modifyButton[3]);
	}
}
