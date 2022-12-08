package library_system_interface;

import login_system.UserFactory;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailPanel extends JPanel {
    public DetailPanel(){
        setLayout(null);
        setBounds(126, 11, 389, 451);
        JButton logout = new JButton("Logout");
        logout.setBounds(63, 31, 100, 20);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserFactory.logout();
                WindowComponents.changeList();
            }
        });
        add(logout);

    }
}
