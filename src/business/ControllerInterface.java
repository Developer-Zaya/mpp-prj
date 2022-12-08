package business;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	public HashMap<String, LibraryMember> allMemberMap();

	Book getBook(String bookIsbn);

	LibraryMember getMember(String text);

	public void addBook(Book book);

}
