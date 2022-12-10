package library_system_interface;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;
import login_system.UserFactory;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LoginPanel extends JPanel {
	private JTextField userIDInput;
	private JTextField passwordInput;
	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(null);
		setSize(389, 451);
		userIDInput = new JTextField();
		userIDInput.setToolTipText("User ID");
		userIDInput.setBounds(155, 99, 167, 20);
		add(userIDInput);
		userIDInput.setColumns(10);
		
		passwordInput = new JPasswordField();
		passwordInput.setToolTipText("Password");
		passwordInput.setBounds(155, 159, 167, 20);
		add(passwordInput);
		passwordInput.setColumns(10);
		
		JButton logginBTN = new JButton("Login");
		logginBTN.setBounds(141, 207, 89, 23);
		add(logginBTN);
		
		JLabel userIDLabel = new JLabel("User ID :");
		userIDLabel.setBounds(63, 102, 69, 17);
		add(userIDLabel);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setBounds(56, 162, 76, 14);
		add(passwordLabel);
		
		JLabel errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(255, 0, 0));
		errorMessage.setFont(new Font("SansSerif", Font.BOLD, 10));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(119, 253, 167, 56);
		add(errorMessage);
		logginBTN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(userIDInput.getText().isEmpty() ){
					errorMessage.setText("Please fill the user id field");
					return;
				}
				if(passwordInput.getText().isEmpty()){
					errorMessage.setText("Please fill the password field");
					return;
				}
				if(!UserFactory.userIDExists(userIDInput.getText())){
					errorMessage.setText("UserID does not exist");
					return;
				}
//				}
				if(!UserFactory.login(userIDInput.getText(), passwordInput.getText())) {
					errorMessage.setText("Password wrong");
				} else {
					WindowComponents.changeList();
				}
			}
			
		});

	}
}
