package library_system_interface;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import librarysystem.AddCopyWindow;
import librarysystem.AddMemberWindow;
import librarysystem.CheckoutBookWindow;
import librarysystem.MemberCheckoutRecordWindow;
import login_system.UserFactory;
import windowindex.LibraryUserConstants;
import windowindex.LibrarianConstants;
import windowindex.AdminContants;


public class WindowComponents {
    private static JList<String> list = new JList<String>(UserFactory.getUser().getWindows());
    private static JPanel panel = new JPanel(new CardLayout());

    public static JList<String> getJlist() {
        return list;
    }

    public static JPanel getJPanel() {
        return panel;
    }

    public static void changeList() {
        list.setListData(UserFactory.getUser().getWindows());
        changeJPanel(UserFactory.getUser().getWindows()[0]);
        list.setSelectedIndex(0);
    }

    public static void changeJPanel(String windowName) {
        if (!UserFactory.getUser().isAuthorized(windowName)) {
            windowName = UserFactory.getUser().getWindows()[0];
        }
        switch (windowName) {
            case LibrarianConstants.DETAIL:
                clearPanel();
                panel.add(windowName, new DetailPanel());
                return;
            case LibrarianConstants.CHECKOUT_MEMBER:
                clearPanel();
                //MemberCheckoutRecordWindow window = new MemberCheckoutRecordWindow();
                //panel.add(windowName, MemberCheckoutRecordWindow.INSTANCE.getJPanel());
                return;
            case LibrarianConstants.CHECKOUT_BOOK:
                clearPanel();
                CheckoutBookWindow checkoutBookWindow = new CheckoutBookWindow();
                checkoutBookWindow.initJPanel();
                panel.add(windowName, checkoutBookWindow.getPanel());
                return;
            case LibraryUserConstants.BOOKS:
                clearPanel();
                panel.add(windowName, new BooksPanel());
                return;
            case LibraryUserConstants.LOGIN:
                clearPanel();
                panel.add(windowName, new LoginPanel());
                return;
            case LibraryUserConstants.MEMBERS:
                clearPanel();
                panel.add(windowName, new MemberPanel());
                return;
            case AdminContants.ADD_MEMBER:
                clearPanel();
                AddMemberWindow.INSTANCE.initJPanel();
                panel.add(windowName, AddMemberWindow.INSTANCE.getPanel());
                return;
            case AdminContants.ADD_COPY:
                clearPanel();
                AddCopyWindow addCopyWindow = new AddCopyWindow();
                addCopyWindow.initJPanel();
                panel.add(windowName, addCopyWindow.getPanel());
                return;
            default:
                return;
        }
    }

    private static void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

}
