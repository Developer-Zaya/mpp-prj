package login_system;

import windowindex.AdminContants;
import windowindex.LibraryMemberConstants;

public class Admin implements User{
    private String userID ;
    private String password;

    private String[] windows = {LibraryMemberConstants.DETAIL,AdminContants.ADD_BOOK,AdminContants.ADD_MEMBER,};
    public Admin(String userID, String password){
        this.userID = userID;
        this.password = password;
    }
    @Override
    public boolean isPasswod(String password) {
        // TODO Auto-generated method stub
        if(this.password.equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public String getUserID() {
        // TODO Auto-generated method stub
        return userID;
    }
    @Override
    public String toString() {
        return this.userID;
    }
    @Override
    public String[] getWindows() {
        // TODO Auto-generated method stub
        return windows;
    }
    @Override
    public boolean isAuthorized(String panelName) {
        for(int i=0; i<windows.length;i++) {
            if(windows[i].equals(panelName)) {
                return true;
            }
        }
        return false;
    }


}
