package login_system;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;


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
	private static DataAccess dao;
	/*
	 * delete this when implements data access method
	 * */
	public static void init() {
		dao = new DataAccessFacade();

	}
	public static boolean login(String userID, String password) {
		if(isLoggedIn()) {
			return true;
		}
		System.out.println(userID);
		dataaccess.User u =dao.readUserMap().get(userID);

		System.out.println(u);
		if(u != null) {
			System.out.println(u);
			if(u.getPassword().equals(password)) {
				switch (u.getAuthorization()){
					case ADMIN :
						loggedInUser= new Admin(u.getId(),u.getPassword());
						return true;
					case LIBRARIAN:
						loggedInUser=new Librarian(u.getId(),u.getPassword());
						return true;
				}
				System.out.println(loggedInUser);
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
