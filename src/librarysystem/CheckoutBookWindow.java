package librarysystem;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class CheckoutBookWindow {
    private static final String MEMBER_ID = "Member ID";
    private static final String BOOK_ISBN = "Book ISBN Number";
    private static final String CHECKOUT_DATE = "Checkout Date";
    private static final String DUE_DATE = "Due Date";
    private static final String COPY_NUM = "Copy Number";
    private static final String[] RECORD_COLUMN = {MEMBER_ID, BOOK_ISBN, COPY_NUM, CHECKOUT_DATE, DUE_DATE};
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final DefaultTableModel recordModel;
    private static JPanel panel;
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame jFrame;
    private JTextField memberId;
    private JTextField bookIsbn;
    private HashMap<String, CheckoutRecord> records;

    public CheckoutBookWindow() {
        recordModel = new DefaultTableModel();
        recordModel.setColumnIdentifiers(RECORD_COLUMN);
        records = da.readUserRecords();
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
    public void initJPanel(){
        // PANEL
        panel = new JPanel();
        panel.setBackground(new Color(0, 150, 122));
        panel.setBounds(0, 6, 900, 466);
        panel.setLayout(null);


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
            else if (!ci.getBook(bookIsbn.getText()).isAvailable()) {
                Book book = ci.getBook(bookIsbn.getText());
                JOptionPane.showMessageDialog(null, String.format("Book: %s(%s) is not available now!", book.getTitle(), book.getIsbn()));
            } else {
                Book book = ci.getBook(bookIsbn.getText());
                System.out.println(book);

                // Create: CheckoutRecord
                LibraryMember member = ci.getMember(memberId.getText());
                BookCopy copy = book.getNextAvailableCopy();
                assert copy != null;
                CheckoutRecord record = new CheckoutRecord(member, copy);
                book.updateCopies(copy);
                da.saveAndUpdateBook(book);
                da.saveNewCheckoutRecord(record);
                records = da.readUserRecords();
                recordModel.addRow(new String[]{
                                record.getLibraryMember().getMemberId(),
                                record.getBookCopy().getBook().getIsbn(),
                                String.valueOf(record.getBookCopy().getCopyNum()),
                                DATE_TIME_FORMATTER.format(record.getCheckoutDate()),
                                DATE_TIME_FORMATTER.format(record.getDueDate()),
                        }
                );

                JOptionPane.showMessageDialog(null, String.format(
                        "Book '%s'(ISBN: %s, Copy: %s) is checkout by Mr(s) '%s %s'(%s)",
                        copy.getBook().getTitle(), copy.getBook().getIsbn(), copy.getCopyNum(),
                        member.getFirstName(), member.getLastName(), member.getMemberId()));

                memberId.setText("");
                bookIsbn.setText("");
            }
        });
        panel.add(btnCheckout);

        // Table
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(6, 180, 800, 287);
        panel.add(jScrollPane);

        JTable jTable = new JTable();
        jTable.setBackground(new Color(255, 240, 245));
        jTable.setModel(recordModel);
        // Get the keys from the map and create a TreeSet for sorting
        Set<String> keys = new TreeSet<>(records.keySet());
        for (String k : keys) {
            CheckoutRecord v = records.get(k);
            String[] row = {
                    v.getLibraryMember().getMemberId(),
                    v.getBookCopy().getBook().getIsbn(),
                    String.valueOf(v.getBookCopy().getCopyNum()),
                    DATE_TIME_FORMATTER.format(v.getCheckoutDate()),
                    DATE_TIME_FORMATTER.format(v.getDueDate()),
            };
            recordModel.addRow(row);
        }
        jTable.setEnabled(false);
        jScrollPane.setViewportView(jTable);

        // Button Clear
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(450, 15, 117, 29);
        btnClear.addActionListener(e -> {
            memberId.setText("");
            bookIsbn.setText("");
        });
        panel.add(btnClear);

    }
    private void init() {
        // FRAME
        jFrame = new JFrame();
        jFrame.setTitle("Checkout Book");
        jFrame.getContentPane().setForeground(new Color(255, 255, 255));
        jFrame.setBounds(100, 100, 800, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);
        initJPanel();
        jFrame.getContentPane().add(panel);
        // Button Back
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(600, 15, 117, 29);
        btnBack.addActionListener(e -> {
            jFrame.setVisible(false);
        });
        panel.add(btnBack);

        // show frame
        jFrame.setVisible(true);
    }
    public JPanel getPanel(){
        return panel;
    }
}
