package librarysystem;

import java.awt.Color;
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

	// private JPanel mainPanel;
	private JPanel mainPanel;

	private List<JTextField> authorTextFields = new ArrayList<>();
	private List<JTextField> bookTextFields = new ArrayList<>();
	private JTextField isbnText, titleText, numberOfCopyText, streetText, cityText,
			stateText, firstnameText, bioText, lastnameText, telephoneText, zipText;
	private JRadioButtonMenuItem maxLengthRadio21, maxLengthRadio7;
	private List<Author> savedAuthors = new ArrayList<>();
	private JLabel savedAuthorLabel;

	JButton newAuthorBtn, saveBookButton, clearButton;

	private final Pattern zipPattern = Pattern.compile("\\d{5}");
	private static final Pattern telephonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
	private static final Pattern isbnPattern = Pattern.compile("\\d{2}-\\d{5}");

	@Override
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(233, 150, 122));
		// GridLayout gl = new GridLayout(20, 3, 5, 0);
		mainPanel.setLayout(null);

		JLabel bookLabel = new JLabel("Book details");
		bookLabel.setBounds(150, 20, 100, 20);
		mainPanel.add(bookLabel);
		// ISBN
		JLabel isbnlabel = new JLabel("ISBN");
		isbnlabel.setBounds(6, 40, 100, 20);
		mainPanel.add(isbnlabel);
		isbnText = new JTextField();
		isbnText.setBounds(130, 40, 160, 20);
		bookTextFields.add(isbnText);
		mainPanel.add(isbnText);

		// Title
		JLabel titlelabel = new JLabel("Title");
		titlelabel.setBounds(6, 60, 100, 20);
		mainPanel.add(titlelabel);
		titleText = new JTextField(10);
		titleText.setBounds(130, 60, 160, 20);
		bookTextFields.add(titleText);
		mainPanel.add(titleText);

		// NumberOfCopies
		JLabel numOfCopiesLabel = new JLabel("Number of Copies");
		numOfCopiesLabel.setBounds(6, 80, 130, 20);
		mainPanel.add(numOfCopiesLabel);
		numberOfCopyText = new JTextField();
		numberOfCopyText.setBounds(130, 80, 160, 20);
		mainPanel.add(numberOfCopyText);
		bookTextFields.add(numberOfCopyText);

		// MaxCheckoutLength
		JLabel maxLengthlabel = new JLabel("Max Checkout Length");
		maxLengthlabel.setBounds(6, 100, 100, 20);
		mainPanel.add(maxLengthlabel);
		ButtonGroup buttonGroup = new ButtonGroup();
		maxLengthRadio21 = new JRadioButtonMenuItem("21 days");
		maxLengthRadio21.setSelected(true);
		maxLengthRadio21.setBounds(130, 100, 80, 20);
		buttonGroup.add(maxLengthRadio21);
		maxLengthRadio7 = new JRadioButtonMenuItem("7 days");
		maxLengthRadio7.setBounds(210, 100, 80, 20);
		buttonGroup.add(maxLengthRadio7);
		mainPanel.add(maxLengthRadio21);
		mainPanel.add(maxLengthRadio7);

		JLabel authorLabel = new JLabel("Author's details");
		authorLabel.setBounds(150, 140, 130, 20);
		mainPanel.add(authorLabel);
		// Authors
		JLabel firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setBounds(6, 160, 100, 20);
		mainPanel.add(firstnameLabel);
		firstnameText = new JTextField();
		firstnameText.setBounds(130, 160, 160, 20);
		authorTextFields.add(firstnameText);
		mainPanel.add(firstnameText);

		JLabel lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setBounds(6, 180, 100, 20);
		mainPanel.add(lastnameLabel);
		lastnameText = new JTextField();
		lastnameText.setBounds(130, 180, 160, 20);
		authorTextFields.add(lastnameText);
		mainPanel.add(lastnameText);

		JLabel bioLabel = new JLabel("Bio");
		bioLabel.setBounds(6, 200, 100, 20);
		mainPanel.add(bioLabel);
		bioText = new JTextField();
		bioText.setBounds(130, 200, 160, 20);
		authorTextFields.add(bioText);
		mainPanel.add(bioText);

		JLabel telephoneLabel = new JLabel("telephone");
		telephoneLabel.setBounds(6, 220, 100, 20);
		mainPanel.add(telephoneLabel);
		telephoneText = new JTextField();
		telephoneText.setBounds(130, 220, 160, 20);
		authorTextFields.add(telephoneText);
		mainPanel.add(telephoneText);

		JLabel streetLabel = new JLabel("street");
		streetLabel.setBounds(6, 240, 100, 20);
		mainPanel.add(streetLabel);
		streetText = new JTextField();
		streetText.setBounds(130, 240, 160, 20);
		authorTextFields.add(streetText);
		mainPanel.add(streetText);

		JLabel cityLabel = new JLabel("city");
		cityLabel.setBounds(6, 260, 100, 20);
		mainPanel.add(cityLabel);
		cityText = new JTextField();
		cityText.setBounds(130, 260, 160, 20);
		authorTextFields.add(cityText);
		mainPanel.add(cityText);

		JLabel stateLabel = new JLabel("state");
		stateLabel.setBounds(6, 280, 100, 20);
		mainPanel.add(stateLabel);
		stateText = new JTextField();
		stateText.setBounds(130, 280, 160, 20);
		authorTextFields.add(stateText);
		mainPanel.add(stateText);

		JLabel zipLabel = new JLabel("zip");
		zipLabel.setBounds(6, 300, 100, 20);
		mainPanel.add(zipLabel);
		zipText = new JTextField();
		zipText.setBounds(130, 300, 160, 20);
		authorTextFields.add(zipText);
		mainPanel.add(zipText);

		savedAuthorLabel = new JLabel("Book Author(s): " + savedAuthors.size());
		savedAuthorLabel.setBounds(6, 340, 150, 20);
		mainPanel.add(savedAuthorLabel);
		newAuthorBtn = new JButton("New Author");
		newAuthorBtn.setBounds(130, 330, 100, 30);
		newAuthorBtn.addActionListener(new AddAuthorListener());
		mainPanel.add(newAuthorBtn);

		saveBookButton = new JButton("Save Book");
		saveBookButton.setBounds(6, 380, 100, 40);
		saveBookButton.addActionListener(new SaveBookListener());
		mainPanel.add(saveBookButton);

		clearButton = new JButton("Clear");
		clearButton.setBounds(130, 380, 100, 40);
		clearButton.addActionListener(new ClearListener());
		mainPanel.add(clearButton);

		isInitialized = true;
	}

	public JPanel getPanel() {
		return mainPanel;
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
