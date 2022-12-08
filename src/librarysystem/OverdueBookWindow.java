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

public class OverdueBookWindow {
    private static final String BOOK_ISBN = "Book ISBN Number";
    private static final String BOOK_TITLE = "Book Title";
    private static final String MEMBER_ID = "Member ID";
    private static final String DUE_DATE = "Due Date";
    private static final String COPY_NUM = "Copy Number";
    private static final String[] RECORD_COLUMN = { BOOK_ISBN, BOOK_TITLE, COPY_NUM, MEMBER_ID, DUE_DATE };
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final DefaultTableModel recordModel;
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame jFrame;
    private JTextField bookIsbn;
    private Book book;
    private HashMap<String, CheckoutRecord> records;

    private OverdueBookWindow() {
        recordModel = new DefaultTableModel();
        recordModel.setColumnIdentifiers(RECORD_COLUMN);
        // records = da.readRecordsMap();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                OverdueBookWindow window = new OverdueBookWindow();
                window.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        // FRAME
        jFrame = new JFrame();
        jFrame.setTitle("Overdue Book");
        jFrame.getContentPane().setForeground(new Color(255, 255, 255));
        jFrame.setBounds(100, 100, 800, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);

        // PANEL
        JPanel panel = new JPanel();
        panel.setBackground(new Color(233, 150, 122));
        panel.setBounds(0, 6, 900, 466);
        panel.setLayout(null);
        jFrame.getContentPane().add(panel);

        // BookIsbn
        JLabel lBookIsbn = new JLabel(BOOK_ISBN);
        lBookIsbn.setBounds(6, 40, 150, 20);
        panel.add(lBookIsbn);

        bookIsbn = new JTextField();
        bookIsbn.setBounds(130, 40, 150, 20);
        panel.add(bookIsbn);

        // Button: Search
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(300, 15, 117, 29);
        btnSearch.addActionListener(e -> {
            // Validation: Null values
            if (bookIsbn.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill ISBN field!");
            // Validation: Book ISBN exists
            else if (!ci.allBookIds().contains(bookIsbn.getText()))
                JOptionPane.showMessageDialog(null, "Book ISBN does not exist!");

            else {
                book = ci.getBook(bookIsbn.getText());
                System.out.println(book);

                bookIsbn.setText("");
            }
        });
        panel.add(btnSearch);

        // Table
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(6, 180, 800, 287);
        panel.add(jScrollPane);

        JTable jTable = new JTable();
        jTable.setBackground(new Color(255, 240, 245));
        jTable.setModel(recordModel);
        // Get the keys from the map and create a TreeSet for sorting
        // Set<String> keys = new TreeSet<>(records.keySet());
        // for (String k : keys) {
        // CheckoutRecord v = records.get(k);
        // String[] row = {
        // v.getLibraryMember().getMemberId(),
        // v.getBookCopy().getBook().getIsbn(),
        // String.valueOf(v.getBookCopy().getCopyNum()),
        // DATE_TIME_FORMATTER.format(v.getCheckoutDate()),
        // DATE_TIME_FORMATTER.format(v.getDueDate()),
        // };
        // recordModel.addRow(row);
        // }
        jScrollPane.setViewportView(jTable);

        // Button Clear
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(450, 15, 117, 29);
        btnClear.addActionListener(e -> {
            bookIsbn.setText("");
        });
        panel.add(btnClear);

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
}
