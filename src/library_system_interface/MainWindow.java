package library_system_interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;

import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import login_system.UserFactory;

import java.awt.Font;

public class MainWindow extends JFrame {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame..
     */
    public MainWindow() {
        UserFactory.init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 800);
        setTitle("GROUP-5");
        getContentPane().setLayout(null);
        setResizable(true);
        JList list = WindowComponents.getJlist();
        list.setFont(new Font("SansSerif", Font.BOLD, 17));
        list.setBounds(10, 11, 156, 740);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        list.addListSelectionListener(event -> {
            String value;
            if (list.getSelectedValue() == null) {
                value = UserFactory.getUser().getWindows()[0];
            } else {
                value = list.getSelectedValue().toString();
            }
		/*The CardLayout class manages two or more components
			(usually JPanel instances) that share the same display space.*/
            WindowComponents.changeJPanel(value);
        });
        getContentPane().add(list);

//		WindowComponents.changeList();
        JPanel panel = WindowComponents.getJPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(300, 180, 650, 740);
        getContentPane().add(panel);
        WindowComponents.changeJPanel(UserFactory.getUser().getWindows()[0]);

    }
}
