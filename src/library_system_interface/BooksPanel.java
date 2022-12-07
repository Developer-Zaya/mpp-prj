package library_system_interface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextPane;

public class BooksPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BooksPanel() {
		setLayout(null);
		setBounds(126, 11, 389, 451);
		JTextPane textPane = new JTextPane();
		textPane.setBounds(63, 31, 219, 202);
		add(textPane);

	}
}
