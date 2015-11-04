package project3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.*;

public class BankGUI extends JFrame {

	// menu variables
	public JMenuBar menuBar;
	private JMenu file;
	private JMenu sort;
	private JMenuItem[] fileItems;
	private JMenuItem[] sortItems;

	// panel variables
	private JPanel mainPanel;
	private JPanel tablePanel;
	private JPanel gridLabelPanel;
	private JPanel gridTextPanel;
	private JPanel boxPanel;

	// button variables
	private JRadioButton savings;
	private JRadioButton checking;
	private ButtonGroup group;
	private JButton[] modifyButton;
	private JLabel[] descriptLabels;
	private JFormattedTextField[] textInputs;

	// listener variables
	private ButtonListener buttonListener;
	private MenuListener menuListener;
	private TableListener tableListener;

	// table variables
	private JTable table;
	private JScrollPane scrollPane;

	// bank model
	private BankModel bankModel;

	// format variables
	private SimpleDateFormat dateFormat;

	public BankGUI() {
		// title application
		super("Bank Application");

		// setup BankModel
		bankModel = new BankModel();

		// setup instance variables
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		modifyButton = new JButton[4];
		descriptLabels = new JLabel[7];
		textInputs = new JFormattedTextField[7];
		table = new JTable();
		mainPanel = new JPanel();
		tablePanel = new JPanel();
		gridLabelPanel = new JPanel();
		gridTextPanel = new JPanel();
		boxPanel = new JPanel();
		menuBar = new JMenuBar();
		savings = new JRadioButton("Savings", true);
		checking = new JRadioButton("Checking", false);
		group = new ButtonGroup();

		// format text fields
		formatTextFields();

		// setup file menu
		file = new JMenu("File");
		file.getAccessibleContext()
				.setAccessibleDescription("File Menu");
		sort = new JMenu("Sort");
		sort.getAccessibleContext()
				.setAccessibleDescription("Sort Menu");
		fileItems = new JMenuItem[7];
		sortItems = new JMenuItem[3];
		menuListener = new MenuListener();

		// instantiate all menu items
		for (int i = 0; i < 7; i++) {
			if (i < 3) {
				sortItems[i] = new JMenuItem();
				sortItems[i].addActionListener(menuListener);
			}
			fileItems[i] = new JMenuItem();
			fileItems[i].addActionListener(menuListener);
		}

		// instantiate all JLabels and JButtons
		for (int i = 0; i < 7; i++) {
			descriptLabels[i] = new JLabel();
		}
		for (int i = 0; i < 4; i++) {
			modifyButton[i] = new JButton();
		}

		// setup GUI
		setupGUI();

		// setup table
		setupTable();

		// add panels to frame
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(tablePanel, BorderLayout.NORTH);
		mainPanel.add(gridLabelPanel, BorderLayout.WEST);
		mainPanel.add(gridTextPanel, BorderLayout.CENTER);
		mainPanel.add(boxPanel, BorderLayout.EAST);
		add(mainPanel);

		// display User Service Agreement
		JOptionPane.showMessageDialog(new JFrame(),
				"User Service Agreement:\n1. Leaving a field blank will"
				+ " result in a"
						+ " default 0 being applied to the empty field."
						+ "\n2. "
						+ "Date must be formatted in MM/DD/YYYY.\n3. "
						+ "If a "
						+ "field is filled with improperly fomatted "
						+ "information, the field will attempt to "
						+ "correct the "
						+ "issue,\n        but if no proper "
						+ "information can be "
						+ "parsed, it will clear itself.\n4. Negative"
						+ " values "
						+ "are not logical and therefore will not be "
						+ "accepted.\n5. When saving or loading a file,"
						+ " the file extension is not needed (it is "
						+ "automatically added).\n6. Account numbers "
						+ "must be 10 or less digits.");
	}

	private void formatTextFields() {

		// format variables
		DecimalFormat integerFormat = new DecimalFormat();
		DecimalFormat percentFormat = new DecimalFormat();
		DecimalFormat currencyFormat = new DecimalFormat();

		// apply patters
		integerFormat.applyPattern("#");
		currencyFormat.applyPattern("#.##");
		percentFormat.applyPattern("#.###");

		// apply formats
		textInputs[0] = new JFormattedTextField(integerFormat);
		textInputs[1] = new JFormattedTextField();
		textInputs[2] = new JFormattedTextField(dateFormat);
		textInputs[3] = new JFormattedTextField(currencyFormat);
		textInputs[4] = new JFormattedTextField(currencyFormat);
		textInputs[5] = new JFormattedTextField(percentFormat);
		textInputs[6] = new JFormattedTextField(currencyFormat);
	}

	private void setupGUI() {

		int i = 0;

		mainPanel.setPreferredSize(new Dimension(500, 500));
		tablePanel.setPreferredSize(new Dimension(500, 250));
		gridLabelPanel.setPreferredSize(new Dimension(150, 250));
		gridTextPanel.setPreferredSize(new Dimension(240, 250));
		boxPanel.setPreferredSize(new Dimension(110, 250));

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
			modifyButton[i].setMaximumSize(new Dimension(95,
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

		// Start off with savings account checked, and Monthly Fee
		// grayed out
		textInputs[4].setText("");
		textInputs[4].setEnabled(false);

		for (i = 0; i < 7; i++) {
			if (i < 3)
				sort.add(sortItems[i]);
			if (i == 2 || i == 4 || i == 6) {
				file.addSeparator();
				file.add(fileItems[i]);
			} else
				file.add(fileItems[i]);
		}

		menuBar.add(file);
		menuBar.add(sort);
	}

	private void setupTable() {

		// setup table
		table = new JTable(bankModel);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		// listener
		tableListener = new TableListener();
		table.addMouseListener(tableListener);
	}

	private void clear() {

		// empty all text fields
		for (int i = 0; i < 7; i++) {
			textInputs[i].setValue(null);
			textInputs[1].setText("");
		}
	}

	private void updateFields(Account accountData) {

		if (accountData instanceof SavingsAccount) {
			textInputs[0]
					.setText(Integer.toString(accountData.getNumber()));
			textInputs[1].setText(accountData.getOwner());
			textInputs[2].setText(dateFormat
					.format(accountData.getDateOpened().getTime()));
			textInputs[3]
					.setText(Double.toString(accountData.getBalance()));
			textInputs[5].setText(Double.toString(
					((SavingsAccount) accountData).getInterestRate()));
			textInputs[6].setText(
					Double.toString(((SavingsAccount) accountData)
							.getMinimumBalance()));
			savings.doClick();
		}
		if (accountData instanceof CheckingAccount) {
			textInputs[0]
					.setText(Integer.toString(accountData.getNumber()));
			textInputs[1].setText(accountData.getOwner());
			textInputs[2].setText(dateFormat
					.format(accountData.getDateOpened().getTime()));
			textInputs[3]
					.setText(Double.toString(accountData.getBalance()));
			textInputs[4].setText(Double.toString(
					((CheckingAccount) accountData).getMonthlyFee()));
			checking.doClick();
		}
	}

	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == fileItems[0]) {
				try {
					// Binary Load
					String filename = getFileName(
							"Please enter filename to load:");
					File f = new File(filename + ".bin");

					if (f.exists() && !f.isDirectory()) {
						bankModel.loadBinary(filename + ".bin");
					} else
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: FILE DOES NOT EXIST");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(),
							"FILE NOT READABLE OR USER CANCEL");
				}
			}
			if (event.getSource() == fileItems[1]) {
				try {
					// Binary Save
					String filename = getFileName(
							"Please enter filename to save to:");
					bankModel.saveBinary(filename + ".bin");
				} catch (Exception e) {
				}
			}
			if (event.getSource() == fileItems[2]) {
				try {
					// Text Load
					String filename = getFileName(
							"Please enter filename to load");
					File f = new File(filename + ".txt");

					if (f.exists() && !f.isDirectory()) {
						bankModel.loadText(filename + ".txt");
					} else
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: FILE DOES NOT EXIST");
				} catch (Exception e) {
				}
			}
			if (event.getSource() == fileItems[3]) {
				try {
					// text save
					String filename = getFileName(
							"Please enter filename to save to:");
					bankModel.saveText(filename + ".txt");
				} catch (Exception e) {
					// do nothing for cancel
				}
			}
			if (event.getSource() == fileItems[4]) {
				// XML load
				try {
					String filename = getFileName(
							"Please enter filename to load:");
					File f = new File(filename + ".xml");

					if (f.exists() && !f.isDirectory()) {
						bankModel.loadXML(filename + ".xml");
					} else
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: FILE DOES NOT EXIST");
				} catch (Exception e) {
					// do nothing for cancel
				}
			}
			if (event.getSource() == fileItems[5]) {
				// XML save
				try {
					String filename = getFileName(
							"Please enter filename to save to:");
					bankModel.saveToXML(filename + ".xml");
				} catch (Exception e) {
				}
			}
			if (event.getSource() == fileItems[6]) {
				// quit
				System.exit(1);
			}
			if (event.getSource() == sortItems[0]) {
				// sort by account number
				bankModel.sortByNumber();
			}
			if (event.getSource() == sortItems[1]) {
				// sort by account owner
				bankModel.sortByName();
			}
			if (event.getSource() == sortItems[2]) {
				bankModel.sortByDateOpened();
			}
		}

		private String getFileName(String message) {

			try {
				String filename = "";
				filename = (String) JOptionPane.showInputDialog(
						new JFrame(), message, "File Name Input",
						JOptionPane.PLAIN_MESSAGE, null, null, null);
				return filename;
			} catch (Exception e) {
				return "";

			}
		}
	}

	private class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {

			if (table.getSelectedRow() >= 0)
				updateFields(
						bankModel.getAccount(table.getSelectedRow()));
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

			// do nothing
		}

		@Override
		public void mouseExited(MouseEvent arg0) {

			// do nothing
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

			if (table.getSelectedRow() >= 0)
				updateFields(
						bankModel.getAccount(table.getSelectedRow()));
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			// do nothing
		}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == modifyButton[0]) {
				// add new account
				try {
					// declare variables
					double monthlyFee;
					double interestRate;
					double minimumBalance;
					boolean isSavings = savings.isSelected();
					int errorCode;

					// get information from text fields
					int number = Integer
							.parseInt(textInputs[0].getText());
					String owner = textInputs[1].getText();
					String dateOpenedString = textInputs[2].getText();
					int month = Integer
							.parseInt(dateOpenedString.substring(0, 2));
					int day = Integer
							.parseInt(dateOpenedString.substring(3, 5));
					int year = Integer.parseInt(
							dateOpenedString.substring(6, 10));
					GregorianCalendar dateOpened = 
							new GregorianCalendar(
							year, month - 1, day);
					double accountBalance = Double
							.parseDouble(textInputs[3].getText());
					try {
						monthlyFee = Double
								.parseDouble(textInputs[4].getText());
					} catch (Exception e) {
						monthlyFee = 0;
					}
					try {
						interestRate = Double
								.parseDouble(textInputs[5].getText());
					} catch (Exception e) {
						interestRate = 0;
					}
					try {
						minimumBalance = Double
								.parseDouble(textInputs[6].getText());
					} catch (Exception e) {
						minimumBalance = 0;
					}

					// send collected data to bankModel
					errorCode = bankModel.add(number, owner, dateOpened,
							accountBalance, monthlyFee, interestRate,
							minimumBalance, isSavings);

					if (errorCode == 1)
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: BALANCE MUST BE GREATER THAN "
										+ "MINIMUM BALANCE");
					else if (errorCode == 2)
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: ACCOUNT NUMBER ALREADY EXISTS");
					else if (errorCode == 3)
						JOptionPane.showMessageDialog(new JFrame(),
							"ERROR: ACCOUNT OWNER MUST HAVE A NAME.");
					else if (errorCode == 4) {
						JOptionPane.showMessageDialog(new JFrame(),
						"ERROR: CANNOT CREATE AN ACCOUNT IN FUTURE.");
					} else if (errorCode == 5) {
						JOptionPane.showMessageDialog(new JFrame(),
								"ERROR: CANNOT HAVE NEGATIVE DATA.");
					}

				} catch (Exception e) {
					// display error message to user
					JOptionPane.showMessageDialog(null,
					"Data formatted incorrectly.\nPlease try again.",
							"Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (event.getSource() == modifyButton[1]) {
				// delete selected row
				try {
					int result = JOptionPane.showConfirmDialog(
							(Component) null, "Are you sure?",
							"Confirm", JOptionPane.OK_CANCEL_OPTION);
					if (result == 0)
						bankModel.delete(table.getSelectedRow());
				} catch (Exception e) {
					// display error message to user
					JOptionPane.showMessageDialog(null,
							"No account is selected."
							+ "\nPlease select an account and "
							+ "try again.",
							"Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (event.getSource() == modifyButton[2]) {
				// update selected account
				try {
					// declare variables
					double monthlyFee;
					double interestRate;
					double minimumBalance;
					boolean isSavings = savings.isSelected();

					// get information from text fields
					int number = Integer
							.parseInt(textInputs[0].getText());
					String owner = textInputs[1].getText();
					String dateOpenedString = textInputs[2].getText();
					int month = Integer
							.parseInt(dateOpenedString.substring(0, 2));
					int day = Integer
							.parseInt(dateOpenedString.substring(3, 5));
					int year = Integer.parseInt(
							dateOpenedString.substring(6, 10));
					GregorianCalendar dateOpened = 
							new GregorianCalendar(
							year, month - 1, day);
					double accountBalance = Double
							.parseDouble(textInputs[3].getText());
					try {
						monthlyFee = Double
								.parseDouble(textInputs[4].getText());
					} catch (Exception e) {
						monthlyFee = 0;
					}
					try {
						interestRate = Double
								.parseDouble(textInputs[5].getText());
					} catch (Exception e) {
						interestRate = 0;
					}
					try {
						minimumBalance = Double
								.parseDouble(textInputs[6].getText());
					} catch (Exception e) {
						minimumBalance = 0;
					}

					// send all collected data to bankModel
					int errorCode = bankModel.update(number, owner, dateOpened,
							accountBalance, monthlyFee, interestRate,
							minimumBalance, isSavings,
							table.getSelectedRow());
					
					if (errorCode == 1)
					{
						JOptionPane.showMessageDialog(null,
								"Invalid data for update. Please try again.",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					// display error message to user
					JOptionPane.showMessageDialog(null,
							"No account is selected to update.\nPlease "
							+ "select an account and try again.",
							"Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (event.getSource() == modifyButton[3]) {
				// clear all text fields
				clear();
			}
			if (event.getSource() == savings) {
				// adjust GUI for savings account
				for (int i = 0; i < 7; i++) {
					if (i != 4)
						textInputs[i].setEnabled(true);
					else {
						textInputs[i].setText("");
						textInputs[i].setEnabled(false);
					}
				}
			}
			if (event.getSource() == checking) {
				// adjust GUI for checking account
				for (int i = 0; i < 7; i++) {
					if (i == 5 || i == 6) {
						textInputs[i].setEnabled(false);
						textInputs[i].setText("");
					} else
						textInputs[i].setEnabled(true);
				}
			}
		}
	}
}
