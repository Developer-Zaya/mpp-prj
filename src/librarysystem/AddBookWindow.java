package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;

public class AddBookWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	private boolean isInitialized = false;
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	ControllerInterface ci = new SystemController();

	private AddBookWindow() {
	}

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;

	private List<JTextField> authorTextFields = new ArrayList<>();
	private List<JTextField> bookTextFields = new ArrayList<>();
	private JTextField isbnText, titleText, numberOfCopyText, streetText, cityText,
			stateText, firstnameText, bioText, lastnameText, telephoneText, zipText;
	private JRadioButtonMenuItem maxLengthRadio21, maxLengthRadio7;
	private List<Author> savedAuthors = new ArrayList<>();
	private JLabel savedAuthorLabel;

	JButton addAuthorButton, saveBookButton, clearButton;

	private final Pattern zipPattern = Pattern.compile("\\d{5}");
	private static final Pattern telephonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
	private static final Pattern isbnPattern = Pattern.compile("\\d{2}-\\d{5}");

	@Override
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// mainPanel.setBounds(100, 100, 800, 500);
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Add Book");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setBackground(new Color(233, 150, 122));
		GridLayout gl = new GridLayout(20, 3, 5, 0);
		middlePanel.setLayout(gl);

		middlePanel.add(new JLabel());
		middlePanel.add(new JLabel("BOOK:"));
		middlePanel.add(new JLabel());
		// ISBN
		JLabel isbnlabel = new JLabel("ISBN");
		middlePanel.add(isbnlabel);
		isbnText = new JTextField();
		isbnText.setBounds(79, 15, 161, 26);
		bookTextFields.add(isbnText);
		middlePanel.add(isbnText);
		middlePanel.add(new JLabel());

		// Title
		JLabel titlelabel = new JLabel("Title");
		middlePanel.add(titlelabel);
		titleText = new JTextField(10);
		titleText.setBounds(79, 15, 161, 26);
		bookTextFields.add(titleText);
		middlePanel.add(titleText);
		middlePanel.add(new JLabel());

		// NumberOfCopies
		JLabel numOfCopiesLabel = new JLabel("Number of Copies");
		middlePanel.add(numOfCopiesLabel);
		numberOfCopyText = new JTextField();
		middlePanel.add(numberOfCopyText);
		bookTextFields.add(numberOfCopyText);
		middlePanel.add(new JLabel());

		// MaxCheckoutLength
		JLabel maxLengthlabel = new JLabel("Max Checkout Length");
		middlePanel.add(maxLengthlabel);
		ButtonGroup buttonGroup = new ButtonGroup();
		maxLengthRadio21 = new JRadioButtonMenuItem("21 days");
		maxLengthRadio21.setSelected(true);
		// maxLengthRadio21.setFont(maxLengthlabel.getFont());
		buttonGroup.add(maxLengthRadio21);
		maxLengthRadio7 = new JRadioButtonMenuItem("7 days");
		buttonGroup.add(maxLengthRadio7);
		middlePanel.add(maxLengthRadio21);
		middlePanel.add(maxLengthRadio7);

		middlePanel.add(new JLabel());
		middlePanel.add(new JLabel("AUTHOR:"));
		middlePanel.add(new JLabel());
		// Authors
		JLabel firstnameLabel = new JLabel("Author Firstname");
		middlePanel.add(firstnameLabel);
		firstnameText = new JTextField();
		firstnameText.setBounds(79, 15, 161, 26);
		authorTextFields.add(firstnameText);
		middlePanel.add(firstnameText);
		middlePanel.add(new JLabel());

		JLabel lastnameLabel = new JLabel("Author Lastname");
		middlePanel.add(lastnameLabel);
		lastnameText = new JTextField();
		lastnameText.setBounds(79, 15, 161, 26);
		authorTextFields.add(lastnameText);
		middlePanel.add(lastnameText);
		middlePanel.add(new JLabel());

		JLabel bioLabel = new JLabel("Author Bio");
		middlePanel.add(bioLabel);
		bioText = new JTextField();
		bioText.setBounds(79, 15, 250, 26);
		authorTextFields.add(bioText);
		middlePanel.add(bioText);
		middlePanel.add(new JLabel());

		JLabel telephoneLabel = new JLabel("Author telephone");
		middlePanel.add(telephoneLabel);
		telephoneText = new JTextField();
		telephoneText.setBounds(79, 15, 161, 26);
		authorTextFields.add(telephoneText);
		middlePanel.add(telephoneText);
		middlePanel.add(new JLabel());

		JLabel streetLabel = new JLabel("Author street");
		middlePanel.add(streetLabel);
		streetText = new JTextField();
		streetText.setBounds(79, 15, 161, 26);
		authorTextFields.add(streetText);
		middlePanel.add(streetText);
		middlePanel.add(new JLabel());

		JLabel cityLabel = new JLabel("Author city");
		middlePanel.add(cityLabel);
		cityText = new JTextField();
		cityText.setBounds(79, 15, 161, 26);
		authorTextFields.add(cityText);
		middlePanel.add(cityText);
		middlePanel.add(new JLabel());

		JLabel stateLabel = new JLabel("Author state");
		middlePanel.add(stateLabel);
		stateText = new JTextField();
		stateText.setBounds(79, 15, 161, 26);
		authorTextFields.add(stateText);
		middlePanel.add(stateText);
		middlePanel.add(new JLabel());

		JLabel zipLabel = new JLabel("Author zip");
		middlePanel.add(zipLabel);
		zipText = new JTextField();
		zipText.setBounds(79, 15, 161, 26);
		authorTextFields.add(zipText);
		middlePanel.add(zipText);
		middlePanel.add(new JLabel());

		savedAuthorLabel = new JLabel("Book Author(s): " + savedAuthors.size());
		middlePanel.add(savedAuthorLabel);
		addAuthorButton = new JButton("New Author");
		addAuthorButton.addActionListener(new AddAuthorListener());
		// addAuthorButton.setVisible(false);
		middlePanel.add(addAuthorButton);
		middlePanel.add(new JLabel());

		saveBookButton = new JButton("Save Book");
		saveBookButton.addActionListener(new SaveBookListener());
		middlePanel.add(saveBookButton);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearListener());
		middlePanel.add(clearButton);
	}

	public JPanel getPanel() {
		return middlePanel;
	}

	public void defineLowerPanel() {

		JButton backToMainButn = new JButton("Back");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
	}

	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			AddBookWindow.INSTANCE.setVisible(false);
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
	}

	class AddAuthorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			saveAuthor();
			for (JTextField tf : authorTextFields) {
				tf.setText("");
			}
			savedAuthorLabel.setText("Book Author(s): " + savedAuthors.size());
			JOptionPane.showMessageDialog(mainPanel,
					"Please fill Author fields again for an additional author and save book!");
		}
	}

	class SaveBookListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {

			// ISBN
			if (isbnText.getText().equals("")) {
				JOptionPane.showMessageDialog(mainPanel, "Please fill ISBN of the book");
				return;
			}
			if (!isbnPattern.matcher(isbnText.getText()).matches()) {
				JOptionPane.showMessageDialog(mainPanel, "ISBN is not valid. Please set XX-XXXXX number!");
				return;
			}
			if (checkBookUniqueness(isbnText.getText())) {
				JOptionPane.showMessageDialog(mainPanel, "There is a book with this ISBN. ISBN must be unique");
				return;
			}
			if (titleText.getText().equals("")) {
				JOptionPane.showMessageDialog(mainPanel, "Please fill Title of the book");
				return;
			}
			if (Integer.parseInt(numberOfCopyText.getText()) < 1) {
				JOptionPane.showMessageDialog(mainPanel, "Number of Copies must be greater than 0");
				return;
			}
			// author
			if (savedAuthors.size() == 0)
				saveAuthor();

			int selectedMaxCheckoutLength = maxLengthRadio21.isSelected() ? 21 : 7;
			Book book = new Book(isbnText.getText(), titleText.getText(),
					selectedMaxCheckoutLength, savedAuthors);
			int num = Integer.parseInt(numberOfCopyText.getText());
			for (int i = 0; i < num - 1; i++) {
				book.addCopy();
			}
			ci.addBook(book);
			JOptionPane.showMessageDialog(mainPanel, "Book added successfuly");
			for (JTextField tf : bookTextFields) {
				tf.setText("");
			}
			for (JTextField tf : authorTextFields) {
				tf.setText("");
			}
			maxLengthRadio21.setSelected(true);
		}
	}

	class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			for (JTextField tf : bookTextFields) {
				tf.setText("");
			}
			for (JTextField tf : authorTextFields) {
				tf.setText("");
			}
			savedAuthors.clear();
			savedAuthorLabel.setText("Book Author(s): " + savedAuthors.size());
		}
	}

	void saveAuthor() {
		String[] authorTexts = { firstnameText.getText(), lastnameText.getText(), bioText.getText(),
				telephoneText.getText(), streetText.getText(), stateText.getText(), cityText.getText(),
				zipText.getText() };
		for (String t : authorTexts) {
			if (t.equals("")) {
				JOptionPane.showMessageDialog(mainPanel, "Please fill all Author informations", "fill the blanks",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		if (!zipPattern.matcher(zipText.getText()).matches()) {
			JOptionPane.showMessageDialog(mainPanel, "Zip code is not valid. Please set 5 digit number!",
					"Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!telephonePattern.matcher(telephoneText.getText()).matches()) {
			JOptionPane.showMessageDialog(mainPanel,
					"Telephone is not valid. Please set 10 digit numbers, including hyphens '-'",
					"Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Address address = new Address(streetText.getText(), cityText.getText(),
				stateText.getText(),
				zipText.getText());
		Author author = new Author(firstnameText.getText(), lastnameText.getText(),
				telephoneText.getText(),
				address, bioText.getText());
		savedAuthors.add(author);
	}

	boolean checkBookUniqueness(String isbn) {
		List<String> bookList = ci.allBookIds();
		return bookList.contains(isbn);
	}
}
