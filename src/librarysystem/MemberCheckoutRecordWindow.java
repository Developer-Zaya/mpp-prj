package librarysystem;

import business.CheckoutRecord;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class MemberCheckoutRecordWindow {
    private static final String MEMBER_ID = "Member ID";
    private static final String BOOK_ISBN = "Book ISBN Number";
    private static final String CHECKOUT_DATE = "Checkout Date";
    private static final String DUE_DATE = "Due Date";
    private static final String COPY_NUM = "Copy Number";
    private static final String[] RECORD_COLUMN = {MEMBER_ID, BOOK_ISBN, COPY_NUM, CHECKOUT_DATE, DUE_DATE};
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final DefaultTableModel recordModel;
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame jFrame;
    private JTextField memberId;
    private HashMap<String, CheckoutRecord> records;

    private MemberCheckoutRecordWindow() {
        recordModel = new DefaultTableModel();
        recordModel.setColumnIdentifiers(RECORD_COLUMN);
        records = da.readUserRecords();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MemberCheckoutRecordWindow window = new MemberCheckoutRecordWindow();
                window.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        // FRAME
        jFrame = new JFrame();
        jFrame.setTitle("Member Checkout Record");
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

        // MemberId
        JLabel lMemberId = new JLabel(MEMBER_ID);
        lMemberId.setBounds(6, 20, 150, 20);
        panel.add(lMemberId);

        memberId = new JTextField();
        memberId.setBounds(130, 20, 150, 20);
        panel.add(memberId);

        // Button: Checkout
        JButton btnCheckoutRecord = new JButton("Checkout Record");
        btnCheckoutRecord.setBounds(300, 15, 117, 29);
        btnCheckoutRecord.addActionListener(e -> {
            // Validation: Null values
            if (memberId.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                // Validation: Member ID exists
            else if (!ci.allMemberIds().contains(memberId.getText()))
                JOptionPane.showMessageDialog(null, "Member ID does not exist!");
            else {
                LibraryMember member = ci.getMember(memberId.getText());
                System.out.println(member);

                // Create: CheckoutRecord
                List<CheckoutRecord> uRecords = da.readUserRecords(member);
                recordModel.setRowCount(0);
                for (CheckoutRecord record : uRecords) {
                    recordModel.addRow(new String[]{
                                    record.getLibraryMember().getMemberId(),
                                    record.getBookCopy().getBook().getIsbn(),
                                    String.valueOf(record.getBookCopy().getCopyNum()),
                                    DATE_TIME_FORMATTER.format(record.getCheckoutDate()),
                                    DATE_TIME_FORMATTER.format(record.getDueDate()),
                            }
                    );
                }
                memberId.setText("");
            }
        });
        panel.add(btnCheckoutRecord);

        // Table
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(6, 180, 800, 287);
        panel.add(jScrollPane);

        JTable jTable = new JTable();
        jTable.setBackground(new Color(255, 240, 245));
        jTable.setModel(recordModel);
        jTable.setEnabled(false);
        jScrollPane.setViewportView(jTable);

        // Button Clear
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(450, 15, 117, 29);
        btnClear.addActionListener(e -> {
            memberId.setText("");
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
