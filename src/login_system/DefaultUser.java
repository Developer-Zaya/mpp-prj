package login_system;

import windowindex.LibraryUserConstants;

public class DefaultUser implements User{
	private String[] windows = {LibraryUserConstants.LOGIN,/* LibraryUserConstants.BOOKS,LibraryUserConstants.MEMBERS */};
	public DefaultUser() {
	}
	@Override
	public boolean isPasswod(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUserID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "default";
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
