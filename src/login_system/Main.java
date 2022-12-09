package login_system;

public class Main {
	public static void main(String[] args) {
	    	UserFactory.init();
	    	UserFactory.login("asdasd", "asdasd");
	    	UserFactory.login("1002", "2385");
			System.out.println(System.getProperty("os.name"));
	    	UserFactory.logout();
	   }
}
