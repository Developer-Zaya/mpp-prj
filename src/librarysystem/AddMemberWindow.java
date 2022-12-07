package librarysystem;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


public class AddMemberWindow {
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();
    private static final String MEMBER_ID = "Member ID";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String TELEPHONE = "Telephone";
    private static final String STREET = "Street";
    private static final String CITY = "City";
    private static final String STATE = "State";
    private static final String ZIP = "Zip";
    private static final String[] COLUMN = {MEMBER_ID, FIRST_NAME, LAST_NAME, TELEPHONE, STREET, CITY, STATE, ZIP};

    private static final Pattern zipPattern = Pattern.compile("\\d{5}");
    private static final Pattern telephonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
    JFrame bframe;
    DefaultTableModel model;
    private JPanel panel;
    private HashMap<String, LibraryMember> members;
    private boolean isInitialized = false;
    // private JTextField memberId;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField telephone;
    private JTextField street;
    private JTextField city;
    private JTextField state;
    private JTextField zip;
    private JTable table;

    private AddMemberWindow() {
        members = ci.allMemberMap();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddMemberWindow window = new AddMemberWindow();
                    window.init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public JPanel getPanel(){
        return panel;
    }
    public void init() {
        // FRAME
        bframe = new JFrame();
        bframe.getContentPane().setForeground(new Color(255, 255, 255));
        bframe.setBounds(100, 100, 600, 500);
        bframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bframe.getContentPane().setLayout(null);
        initJPanel();
        // PANEL
        bframe.getContentPane().add(panel);
        JButton btnback = new JButton("Back");
        btnback.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
            bframe.setVisible(false);
        });
        btnback.setBounds(370, 107, 117, 29);
        panel.add(btnback);

        bframe.setVisible(true);
        bframe.setTitle("Add Member");
        isInitialized = true;

    }
    public void initJPanel(){
        panel = new JPanel();
        panel.setBackground(new Color(233, 150, 122));
        panel.setBounds(0, 6, 594, 466);
        panel.setLayout(null);

        // FIELDS: Label
//        JLabel memberId = new JLabel(MEMBER_ID);
//        memberId.setBounds(6, 20, 100, 20);
//        panel.add(memberId);

        JLabel lFirstName = new JLabel(FIRST_NAME);
        lFirstName.setBounds(6, 40, 100, 20);
        panel.add(lFirstName);

        JLabel lLastName = new JLabel(LAST_NAME);
        lLastName.setBounds(6, 60, 100, 20);
        panel.add(lLastName);

        JLabel lTelephone = new JLabel(TELEPHONE);
        lTelephone.setBounds(6, 80, 100, 20);
        panel.add(lTelephone);

        JLabel lStreet = new JLabel(STREET);
        lStreet.setBounds(6, 100, 100, 20);
        panel.add(lStreet);

        JLabel lCity = new JLabel(CITY);
        lCity.setBounds(6, 120, 100, 20);
        panel.add(lCity);

        JLabel lState = new JLabel(STATE);
        lState.setBounds(6, 140, 100, 20);
        panel.add(lState);

        JLabel lZip = new JLabel(ZIP);
        lZip.setBounds(6, 160, 100, 20);
        panel.add(lZip);

        // FIELDS: Text Field
//        this.memberId = new JTextField();
//        this.memberId.setBounds(110, 20, 160, 20);
//        panel.add(this.memberId);
//        this.memberId.setColumns(10);

        this.firstName = new JTextField();
        this.firstName.setBounds(110, 40, 160, 20);
        panel.add(this.firstName);
        this.firstName.setColumns(10);

        this.lastName = new JTextField();
        this.lastName.setBounds(110, 60, 160, 20);
        panel.add(this.lastName);
        this.lastName.setColumns(10);

        this.telephone = new JTextField();
        this.telephone.setBounds(110, 80, 160, 20);
        panel.add(this.telephone);
        this.telephone.setColumns(10);

        this.street = new JTextField();
        this.street.setBounds(110, 100, 160, 20);
        panel.add(this.street);
        this.street.setColumns(10);

        this.city = new JTextField();
        this.city.setBounds(110, 120, 160, 20);
        panel.add(this.city);
        this.city.setColumns(10);

        this.state = new JTextField();
        this.state.setBounds(110, 140, 160, 20);
        panel.add(this.state);
        this.state.setColumns(10);

        this.zip = new JTextField();
        this.zip.setBounds(110, 160, 160, 20);
        panel.add(this.zip);
        this.zip.setColumns(10);

        // SCROLL
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 180, 582, 287);
        panel.add(scrollPane);

        // TABLE
        table = new JTable();

        // Row Click -> Text Field render
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
//                memberId.setText(model.getValueAt(r, 0).toString());
                firstName.setText(model.getValueAt(r, 1).toString());
                lastName.setText(model.getValueAt(r, 2).toString());
                telephone.setText(model.getValueAt(r, 3).toString());
                street.setText(model.getValueAt(r, 4).toString());
                city.setText(model.getValueAt(r, 5).toString());
                state.setText(model.getValueAt(r, 6).toString());
                zip.setText(model.getValueAt(r, 7).toString());
            }
        });

        table.setBackground(new Color(255, 240, 245));
        model = new DefaultTableModel();
        model.setColumnIdentifiers(COLUMN);
        table.setModel(model);
        // Get the keys from the map and create a TreeSet for sorting
        Set<String> keys = new TreeSet<>(members.keySet());
        for (String k : keys) {
            String[] row = {k,
                    members.get(k).getFirstName(),
                    members.get(k).getLastName(),
                    members.get(k).getTelephone(),
                    members.get(k).getAddress().getStreet(),
                    members.get(k).getAddress().getCity(),
                    members.get(k).getAddress().getState(),
                    members.get(k).getAddress().getZip(),
            };
            model.addRow(row);
        }

        scrollPane.setViewportView(table);

        // ADD -> Save and Add row to table
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            // Validation empty values
            if (
//                        memberId.getText().equals("") ||
                    firstName.getText().equals("") ||
                            lastName.getText().equals("") ||
                            telephone.getText().equals("") ||
                            street.getText().equals("") ||
                            city.getText().equals("") ||
                            state.getText().equals("") ||
                            zip.getText().equals("")
            ) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
            } else if (!zipPattern.matcher(zip.getText()).matches()) {
                JOptionPane.showMessageDialog(null, "Zip code is not valid. Please set 5 digit number!");
            } else if (!telephonePattern.matcher(telephone.getText()).matches()) {
                JOptionPane.showMessageDialog(null, "Telephone is not valid. Please set 10 digit numbers, including hyphens '-'");
            } else {
                Address newAddress = new Address(
                        street.getText(),
                        city.getText(),
                        state.getText(),
                        zip.getText());
                String newMemberId = 1000 + members.size() + 1 + "";
                LibraryMember newMember = new LibraryMember(
                        newMemberId,
                        firstName.getText(),
                        lastName.getText(),
                        telephone.getText(),
                        newAddress);
                da.saveNewMember(newMember);
                members = ci.allMemberMap();

                // update table
                String[] row = {
                        newMemberId,
                        firstName.getText(),
                        lastName.getText(),
                        telephone.getText(),
                        street.getText(),
                        city.getText(),
                        state.getText(),
                        zip.getText()
                };
                model.addRow(row);
                JOptionPane.showMessageDialog(null, "Added Successfully");
                // clear all the text fields
//                    memberId.setText("");
                firstName.setText("");
                lastName.setText("");
                telephone.setText("");
                street.setText("");
                city.setText("");
                state.setText("");
                zip.setText("");
            }

        });
        btnAdd.setBounds(277, 15, 117, 29);
        panel.add(btnAdd);

        // DELETE
//        JButton btndel = new JButton("Delete");
//        btndel.addActionListener(e -> {
//            int r = table.getSelectedRow();
//            if (r >= 0) {
//                model.removeRow(r);
//                JOptionPane.showMessageDialog(null, "Deleted Successfully");
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Please select a row");
//            }
//        });
//        btndel.setBounds(461, 15, 117, 29);
//        panel.add(btndel);

        // Update
//        JButton btnupdate = new JButton("Update");
//        btnupdate.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int r = table.getSelectedRow();
//                if (r >= 0) {
////                    model.setValueAt(memberId.getText(), r, 0);
//                    model.setValueAt(firstName.getText(), r, 1);
//                    model.setValueAt(lastName.getText(), r, 2);
//                    JOptionPane.showMessageDialog(null, "Updated Successfully");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please select a row");
//                }
//            }
//        });
//        btnupdate.setBounds(277, 56, 117, 29);
//        panel.add(btnupdate);

        // Clear -> clear textfields
        JButton btnclear = new JButton("Clear");
        btnclear.addActionListener(e -> {
//                memberId.setText("");
            firstName.setText("");
            lastName.setText("");
            telephone.setText("");
            street.setText("");
            city.setText("");
            state.setText("");
            zip.setText("");
        });
        btnclear.setBounds(461, 56, 117, 29);
        panel.add(btnclear);

    }
    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }
}
