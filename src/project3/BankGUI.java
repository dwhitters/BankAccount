package project3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class BankGUI extends JFrame {
	public JMenuBar menuBar;
	private JMenu file;
	private JMenu sort;
	private JMenuItem[] fileItems;
	private JMenuItem[] sortItems;
	
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
	private JFormattedTextField[] textInputs = new JFormattedTextField[7];
	
	private ButtonListener buttonListener;
	private MenuListener menuListener;

	private JTable table = new JTable();
	private JScrollPane scrollPane;

	private BankModel model;
	
	private NumberFormat integerFormat  = NumberFormat.getIntegerInstance();
	private NumberFormat percentFormat  = NumberFormat.getNumberInstance();
	private NumberFormat currencyFormat  = NumberFormat.getNumberInstance();
	private MaskFormatter characterFormat;

	public BankGUI() {
		super("Bank Application");
		int i = 0;

		model = new BankModel();

		mainPanel = new JPanel();
		tablePanel = new JPanel();
		gridLabelPanel = new JPanel();
		gridTextPanel = new JPanel();
		boxPanel = new JPanel();
		
<<<<<<< HEAD
		//formats
		currencyFormat.setMaximumFractionDigits(2);
		percentFormat.setMaximumFractionDigits(3);
		try {
			characterFormat = new MaskFormatter("??????????????????????????????");
		} catch (ParseException e) {
			//do nothing
=======
		menuBar = new JMenuBar();
		
		//fileMenu
		file = new JMenu("File");
		file.getAccessibleContext().setAccessibleDescription(
		        "File Menu");
		sort = new JMenu("Sort");
		sort.getAccessibleContext().setAccessibleDescription(
		        "Sort Menu");
		
		fileItems = new JMenuItem[7];
		sortItems = new JMenuItem[3];
		
		//Instantiate all menu items
		for(i = 0; i < 7; i++){
			if(i < 3){
				sortItems[i] = new JMenuItem();
				sortItems[i].addActionListener(menuListener);
			}
			fileItems[i] = new JMenuItem();
			fileItems[i].addActionListener(menuListener);
>>>>>>> 96d72202c7bb79ecf62268cd93be5a753f37af56
		}
		
		
		
		//format text fields
		textInputs [0] = new JFormattedTextField(integerFormat);
		textInputs [1] = new JFormattedTextField(characterFormat);
		textInputs [2] = new JFormattedTextField();
		textInputs [3] = new JFormattedTextField(currencyFormat);
		textInputs [4] = new JFormattedTextField(currencyFormat);
		textInputs [5] = new JFormattedTextField(percentFormat);
		textInputs [6] = new JFormattedTextField(currencyFormat);

		savings = new JRadioButton("Savings", true);
		checking = new JRadioButton("Checking", false);
		group = new ButtonGroup();

		for (i = 0; i < 7; i++) {
			descriptLabels[i] = new JLabel();
			//textInputs[i] = new JFormattedTextField();
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
		
		fileItems[0].setText("Load From Binary...");
		fileItems[1].setText("Save As Binary...");
		fileItems[2].setText("Load From Text...");
		fileItems[3].setText("Save As Text...");
		fileItems[4].setText("Load From XML...");
		fileItems[5].setText("Save As XML...");
		fileItems[6].setText("Quit");
		
		sortItems[0].setText("Account Number");
		sortItems[1].setText("Account Owner");
		sortItems[2].setText("Date");

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
		
		for(i = 0; i < 7; i++){
			if(i < 3)
				sort.add(sortItems[i]);
			if(i == 2 || i == 4 || i == 6){
				file.addSeparator();
				file.add(fileItems[i]);
			}
			else
				file.add(fileItems[i]);
		}
		
		menuBar.add(file);
		menuBar.add(sort);
	}

	private void setupJTable() {

		// setup table
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
	}
	
	private void clear()
	{
		for (int i = 0; i <7; i++)
		{
			textInputs [i].setText("");
		}
	}
	
	private void update(Account accountData)
	{
		if (accountData instanceof SavingsAccount)
		{
			textInputs [0].setText(Integer.toString(accountData.getAccountNumber()));
			textInputs [1].setText(accountData.getOwner());
			textInputs [2].setText("");
			textInputs [3].setText(Double.toString(accountData.getBalance()));
			textInputs [4].setText("0");
			textInputs [5].setText(Double.toString(((SavingsAccount)accountData).getInterestRate()));
			textInputs [6].setText(Double.toString(((SavingsAccount)accountData).getMinimumBalance()));
		}
		if (accountData instanceof CheckingAccount)
		{
			textInputs [0].setText(Integer.toString(accountData.getAccountNumber()));
			textInputs [1].setText(accountData.getOwner());
			textInputs [2].setText("");
			textInputs [3].setText(Double.toString(accountData.getBalance()));
			textInputs [4].setText(Double.toString(((CheckingAccount)accountData).getMonthlyFee()));
			textInputs [5].setText("0");
			textInputs [6].setText("0");
		}
	}
	
	private class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == fileItems[0]){
				//Binary Load
			}
			if(event.getSource() == fileItems[1]){
					//Binary Save		
			}
			if(event.getSource() == fileItems[2]){
				//Text Load
			}
			if(event.getSource() == fileItems[3]){
				//text save
			}
			if(event.getSource() == fileItems[4]){
				//XML load
			}
			if(event.getSource() == fileItems[5]){
				//XML save
			}
			if(event.getSource() == fileItems[6]){
				//quit
			}
			if(event.getSource() == sortItems[0]){
				//sort by account number
			}
			if(event.getSource() == sortItems[0]){
				//sort by account owner
			}
			if(event.getSource() == sortItems[0]){
				//sort by date
			}
		}
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
				update(model.update());
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
