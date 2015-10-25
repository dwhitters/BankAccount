package project3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.*;

public class BankGUI extends JFrame {
	private JPanel mainPanel;
	private JPanel tablePanel;
	private JPanel gridLabelPanel;
	private JPanel gridTextPanel;
	private JPanel boxPanel;
	
	private JRadioButton savings;
	private JRadioButton checking;
	private ButtonGroup group;

	private JButton[] modifyButton = new JButton[4];
	private JLabel[] descriptLabels = new JLabel[7];
	private JTextField[] textInputs = new JTextField[7];
	
	private ButtonListener buttonListener;

	private JTable table = new JTable();
	private JScrollPane scrollPane;

	private BankModel model;

	public BankGUI() {
		super("Bank Application");
		int i = 0;

		model = new BankModel();

		mainPanel = new JPanel();
		tablePanel = new JPanel();
		gridLabelPanel = new JPanel();
		gridTextPanel = new JPanel();
		boxPanel = new JPanel();
		
		savings = new JRadioButton("Savings", true);
		checking = new JRadioButton("Checking", false);
		group = new ButtonGroup();

		for (i = 0; i < 7; i++) {
			descriptLabels[i] = new JLabel();
			textInputs[i] = new JTextField();
		}
		for (i = 0; i < 4; i++) {
			modifyButton[i] = new JButton();
		}

		setup();

		setupJTable();

		tablePanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(tablePanel, BorderLayout.NORTH);
		mainPanel.add(gridLabelPanel, BorderLayout.WEST);
		mainPanel.add(gridTextPanel, BorderLayout.CENTER);
		mainPanel.add(boxPanel, BorderLayout.EAST);
		this.add(mainPanel);
	}

	private void setup() {

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
		tablePanel.setLayout(new BorderLayout());

		tablePanel.setBackground(Color.BLACK);
		boxPanel.setBackground(Color.DARK_GRAY);

		modifyButton[0].setText("Add");
		modifyButton[1].setText("Delete");
		modifyButton[2].setText("Update");
		modifyButton[3].setText("Clear");

		// add action listeners
		buttonListener = new ButtonListener();
		modifyButton[0].addActionListener(buttonListener);
		modifyButton[1].addActionListener(buttonListener);
		modifyButton[2].addActionListener(buttonListener);
		modifyButton[3].addActionListener(buttonListener);
		
		savings.addActionListener(buttonListener);
		checking.addActionListener(buttonListener);

		descriptLabels[0].setText("  Account Number:");
		descriptLabels[1].setText("  Account Owner:");
		descriptLabels[2].setText("  Date Opened:");
		descriptLabels[3].setText("  Account Balance:");
		descriptLabels[4].setText("  Monthly Fee:");
		descriptLabels[5].setText("  Interest Rate:");
		descriptLabels[6].setText("  Minimum Balance:");

		// Empty grid cells waiting for radio buttons
		gridLabelPanel.add(checking);
		gridTextPanel.add(savings);

		for (i = 0; i < 7; i++) {
			gridLabelPanel.add(descriptLabels[i]);
			gridTextPanel.add(textInputs[i]);
		}

		// Format box layout
		for (i = 0; i < 4; i++) {
			modifyButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			modifyButton[i].setMaximumSize(new Dimension(75,
					modifyButton[i].getMinimumSize().height));
		}
		boxPanel.add(Box.createRigidArea(new Dimension(1, 40)));
		boxPanel.add(modifyButton[0]);
		boxPanel.add(Box.createRigidArea(new Dimension(1, 5)));
		boxPanel.add(modifyButton[1]);
		boxPanel.add(Box.createRigidArea(new Dimension(1, 5)));
		boxPanel.add(modifyButton[2]);
		boxPanel.add(Box.createRigidArea(new Dimension(1, 5)));
		boxPanel.add(modifyButton[3]);
		
		group.add(savings);
		group.add(checking);
		
		//Start off with savings account checked, and Monthly Fee grayed out
		textInputs[4].setEnabled(false);
	}

	private void setupJTable() {

		// setup table
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
	}
	
	private void clear()
	{
		
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == modifyButton[0])
			{
				int number = Integer.parseInt(textInputs [0].getText());
				String owner = textInputs [1].getText();
				String dateOpenedString = textInputs [2].getText();
				int month = Integer.parseInt(dateOpenedString.substring(0, 2));
				int day = Integer.parseInt(dateOpenedString.substring(3, 5));
				int year = Integer.parseInt(dateOpenedString.substring(6, 10));
				GregorianCalendar dateOpened = new GregorianCalendar(year, month, day);
				double accountBalance = Double.parseDouble(textInputs [3].getText());
				double monthlyFee = Double.parseDouble(textInputs [4].getText());
				double interestRate = Double.parseDouble(textInputs [5].getText());
				double minimumBalance = Double.parseDouble(textInputs [6].getText());
				model.add(number, owner, dateOpened, accountBalance, monthlyFee, interestRate, minimumBalance);
			}
			if (event.getSource() == modifyButton[1])
			{
				model.delete(table.getSelectedRow());
			}
			if (event.getSource() == modifyButton[2])
			{
				model.update();
			}
			if (event.getSource() == modifyButton[3])
			{
				clear();
			}
			if(event.getSource() == savings)
			{
				int i = 0;
				for(i = 0; i < 7; i++){
					if(i != 4)
						textInputs[i].setEnabled(true);
					else
						textInputs[i].setEnabled(false);
				}
			}
			if(event.getSource() == checking){
				int i = 0;
				for(i = 0; i < 7; i++){
					if(i == 5 || i == 6)
						textInputs[i].setEnabled(false);
					else
						textInputs[i].setEnabled(true);
				}
			}
		}
	}
}
