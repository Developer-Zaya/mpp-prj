package librarysystem;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import java.awt.*;

public class CheckoutBookWindow {
    private static final String MEMBER_ID = "Member ID";
    private static final String BOOK_ISBN = "Book ISBN Number";
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame jFrame;
    private JTextField memberId;
    private JTextField bookIsbn;

    private CheckoutBookWindow() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CheckoutBookWindow window = new CheckoutBookWindow();
                window.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        // FRAME
        jFrame = new JFrame();
        jFrame.setTitle("Checkout Book");
        jFrame.getContentPane().setForeground(new Color(255, 255, 255));
        jFrame.setBounds(100, 100, 600, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);

        // PANEL
        JPanel panel = new JPanel();
        panel.setBackground(new Color(233, 150, 122));
        panel.setBounds(0, 6, 594, 466);
        panel.setLayout(null);
        jFrame.getContentPane().add(panel);

        // MemberId
        JLabel lMemberId = new JLabel(MEMBER_ID);
        lMemberId.setBounds(6, 20, 150, 20);
        panel.add(lMemberId);

        memberId = new JTextField();
        memberId.setBounds(130, 20, 150, 20);
        panel.add(memberId);

        // BookIsbn
        JLabel lBookIsbn = new JLabel(BOOK_ISBN);
        lBookIsbn.setBounds(6, 40, 150, 20);
        panel.add(lBookIsbn);

        bookIsbn = new JTextField();
        bookIsbn.setBounds(130, 40, 150, 20);
        panel.add(bookIsbn);

        // Button: Checkout
        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setBounds(300, 15, 117, 29);
        btnCheckout.addActionListener(e -> {
            // Validation: Null values
            if (memberId.getText().isEmpty() || bookIsbn.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                // Validation: Member ID exists
            else if (!ci.allMemberIds().contains(memberId.getText()))
                JOptionPane.showMessageDialog(null, "Member ID does not exist!");
                // Validation: Book ISBN exists
            else if (!ci.allBookIds().contains(bookIsbn.getText()))
                JOptionPane.showMessageDialog(null, "Book ISBN does not exist!");

            // Validation: Book Availability
            Book book = ci.getBook(bookIsbn.getText());
            System.out.println(book);
            if (!book.isAvailable())
                JOptionPane.showMessageDialog(null, String.format("Book: %s(%s) is not available now!", book.getTitle(), book.getIsbn()));

            // Create: CheckoutRecord
            LibraryMember member = ci.getMember(memberId.getText());
            BookCopy copy = book.getNextAvailableCopy();
            assert copy != null;
            CheckoutRecord record = new CheckoutRecord(member, copy);
            da.saveNewCheckoutRecord(record);
        });
        panel.add(btnCheckout);

        // show frame
        jFrame.setVisible(true);
    }
}
