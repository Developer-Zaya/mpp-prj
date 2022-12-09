package library_system_interface;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import librarysystem.*;
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

    /*
     * update list
     */
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
            /*
             * TODO
             * ADD THIS WINDOW
             */
            case LibraryUserConstants.BOOKS:
                clearPanel();
                panel.add(windowName, new BooksPanel());
                return;
            /*
             * TODO
             * CHANGE THE STYLE
             */
            case LibraryUserConstants.LOGIN:
                clearPanel();
                LoginPanel loginPanel = new LoginPanel();
                panel.add(windowName, loginPanel);
                return;
            /*
             * TODO
             * ADD THIS PAGE
             */
            case LibraryUserConstants.MEMBERS:
                clearPanel();
                panel.add(windowName, new MemberPanel());
                return;
            /*
             * TODO
             */
            case LibrarianConstants.DETAIL:
                clearPanel();
                panel.add(windowName, new DetailPanel());
                return;
            /*
             * TODO
             */
            case LibrarianConstants.CHECKOUT_MEMBER:
                clearPanel();
                MemberCheckoutRecordWindow memberCheckoutRecordWindow = new MemberCheckoutRecordWindow();
                memberCheckoutRecordWindow.initJPanel();
                panel.add(windowName, memberCheckoutRecordWindow.getPanel());
                panel.setSize(memberCheckoutRecordWindow.getPanel().getSize());
                panel.getTopLevelAncestor().setSize(panel.getWidth() + 170, panel.getHeight());
                return;
            /*
             * TODO
             */
            case LibrarianConstants.CHECKOUT_BOOK:
                clearPanel();
                CheckoutBookWindow checkoutBookWindow = new CheckoutBookWindow();
                checkoutBookWindow.initJPanel();
                panel.add(windowName, checkoutBookWindow.getPanel());
                panel.setSize(checkoutBookWindow.getPanel().getSize());
                panel.getTopLevelAncestor().setSize(panel.getWidth() + 170, panel.getHeight());
                return;
            /*
             * SUGGESTION
             * Maybe add author from slider ?
             * time stamp 11:00
             */
            case AdminContants.ADD_BOOK:
                clearPanel();
                AddBookWindow.INSTANCE.init();
                panel.add(windowName, AddBookWindow.INSTANCE.getPanel());
                panel.setSize(350,600);

                panel.getTopLevelAncestor().setSize(panel.getWidth() + 170, panel.getHeight());
                return;
            /*
             * WARNING USER ON CLICK EVENT MAYBE BUGGED
             * but this is working fine for req
             */
            case AdminContants.ADD_MEMBER:
                clearPanel();
                AddMemberWindow.INSTANCE.initJPanel();
                panel.add(windowName, AddMemberWindow.INSTANCE.getPanel());
                panel.setSize(AddMemberWindow.INSTANCE.getPanel().getSize());
                panel.getTopLevelAncestor().setSize(panel.getWidth() + 170, panel.getHeight());

                return;
            /*
             * ADD BOOKS SHOW ON LIST
             */
            case AdminContants.ADD_COPY:
                clearPanel();
                AddCopyWindow addCopyWindow = new AddCopyWindow();
                addCopyWindow.initJPanel();
                panel.add(windowName, addCopyWindow.getPanel());
                panel.setSize(addCopyWindow.getPanel().getSize());
                panel.getTopLevelAncestor().setSize(panel.getWidth() + 170, panel.getHeight());

                return;
            default:
                return;
        }
    }

    /*
     * TO REFRESH THE PAGE
     */
    private static void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

}
