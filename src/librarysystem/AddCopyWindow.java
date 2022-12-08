package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class AddCopyWindow {

    private static final String BOOK_ISBN = "ISBN";
    private static final String BOOK_TITLE = "Title";
    private static final String COPY_NUM = "Copy Number";
    private static final String COPY_AV_NUM = "Copy Available Number";

    private static final String[] RECORD_COLUMN = {BOOK_TITLE, BOOK_ISBN, COPY_NUM, COPY_AV_NUM};
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final DefaultTableModel bookModel;
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame jFrame;
    private JPanel panel;
    private JTextField bookIsbn;
    private Book book;

    public AddCopyWindow() {
        bookModel = new DefaultTableModel();
        bookModel.setColumnIdentifiers(RECORD_COLUMN);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AddCopyWindow window = new AddCopyWindow();
                window.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void initJPanel(){
        // PANEL
        panel = new JPanel();
        panel.setBackground(new Color(233, 150, 122));
        panel.setBounds(0, 6, 900, 466);
        panel.setLayout(null);


        // BookIsbn
        JLabel lBookIsbn = new JLabel(BOOK_ISBN);
        lBookIsbn.setBounds(6, 40, 150, 20);
        panel.add(lBookIsbn);

        bookIsbn = new JTextField();
        bookIsbn.setBounds(130, 40, 150, 20);
        panel.add(bookIsbn);

        // Button: Checkout
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(300, 15, 117, 29);
        btnSearch.addActionListener(e -> {
            // Validation: Null values
            if (bookIsbn.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                // Validation: Book ISBN exists
            else if (!ci.allBookIds().contains(bookIsbn.getText()))
                JOptionPane.showMessageDialog(null, "Book ISBN does not exist!");
            else {
                book = ci.getBook(bookIsbn.getText());
                System.out.println(book);
                // add to table
                if (book != null) {
                    if (bookModel.getRowCount() > 0)
                        bookModel.removeRow(0);
                    bookModel.addRow(new String[]{
                            book.getIsbn(),
                            book.getTitle(),
                            String.valueOf(book.getNumCopies()),
                            String.valueOf(book.getAvaialbeCopyNum()),
                    });
                }
                JOptionPane.showMessageDialog(null, String.format(
                        "Book '%s'(ISBN: %s, Copy Num: %s, Available Copy Num: %s)",
                        book.getTitle(), book.getIsbn(), book.getNumCopies(), book.getAvaialbeCopyNum()));
            }
        });
        panel.add(btnSearch);

        // Button: Checkout
        JButton btnAddCopy = new JButton("Add Copy");
        btnAddCopy.setBounds(300, 45, 117, 29);
        btnAddCopy.addActionListener(e -> {
            // Validation: Null values
            if (bookIsbn.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                // Validation: Book ISBN exists
            else if (!ci.allBookIds().contains(bookIsbn.getText()))
                JOptionPane.showMessageDialog(null, "Book ISBN does not exist!");
            else {
                book = ci.getBook(bookIsbn.getText());
                System.out.println("Book:" + book);
                book.addCopy();
                System.out.println("Added Copy: " + book);
                da.saveAndUpdateBook(book);
                // add to table
                if (book != null) {
                    if (bookModel.getRowCount() > 0)
                        bookModel.removeRow(0);
                    bookModel.addRow(new String[]{
                            book.getIsbn(),
                            book.getTitle(),
                            String.valueOf(book.getNumCopies()),
                            String.valueOf(book.getAvaialbeCopyNum()),
                    });
                }
                assert book != null;
                JOptionPane.showMessageDialog(null, String.format(
                        "New Copy added!\n" +
                                "Book '%s'(ISBN: %s, Copy Num: %s, Available Copy Num: %s)",
                        book.getTitle(), book.getIsbn(), book.getNumCopies(), book.getAvaialbeCopyNum()));
            }
        });
        panel.add(btnAddCopy);

        // Table
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(6, 180, 800, 287);
        panel.add(jScrollPane);

        JTable jTable = new JTable();
        jTable.setBackground(new Color(255, 240, 245));
        jTable.setModel(bookModel);
        jTable.setEnabled(false);
        jScrollPane.setViewportView(jTable);

        // Button Clear
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(450, 15, 117, 29);
        btnClear.addActionListener(e -> {
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
