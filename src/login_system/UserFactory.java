package login_system;

import java.util.HashMap;

/*
 * TODO data access method delete add method
 * User factory class is for user loggin in and loggin out
 * to do implement method for reading file that contains users
 * */
public class UserFactory {
	/*
	 * login method will return user it
	 * keep it mind it will return the user with matching with userID and password with their role 
	 * if u need to check it u have to do it by your self 
	 * */
	private static User loggedInUser = new DefaultUser();
	private static User[] userList = {
			new LibraryMember("1001","2382"),
			new LibraryMember("1002","2385"),
			new Admin("1003","2386"),
	};
	private static HashMap<String, User> members = new HashMap<String, User>();
	/*
	 * delete this when implements data access method
	 * */
	public static void add() {
		for(int i=0;i<userList.length;i++) {
			members.put(userList[i].getUserID(),userList[i]);
		}
	}
	public static boolean login(String userID, String password) {
		if(isLoggedIn()) {
			return true;
		}
		System.out.println(userID);
		User u =members.get(userID);

		System.out.println(u);
		if(u != null) {
			System.out.println("User Found");
			if(u.isPasswod(password)) {
				loggedInUser = u;
				System.out.println(loggedInUser);
				return true;
			}
		}
	
		return false;
	}
	public static boolean isLoggedIn() {
		System.out.println(loggedInUser);
		if(loggedInUser instanceof DefaultUser) {
			return false;
		}
		return true;
	}
	public static User getUser() {
		return loggedInUser;
	}
	public static void logout() {
		if(!isLoggedIn()) {
			return;
		}
		loggedInUser = new DefaultUser();
	}
}
