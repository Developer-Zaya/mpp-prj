package login_system;

public interface User {
	public boolean isPasswod(String password);
	public boolean isAuthorized(String panelName);
	public String getUserID();
	public String[] getWindows();
}
