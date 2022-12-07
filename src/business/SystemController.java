package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();

    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public HashMap<String, LibraryMember> allMemberMap() {
        return new DataAccessFacade().readMemberMap();
    }

    @Override
    public Book getBook(String bookIsbn) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> books = da.readBooksMap();
        if (books.containsKey(bookIsbn))
            return books.get(bookIsbn);
        return null;
    }

    @Override
    public LibraryMember getMember(String memberId) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> members = da.readMemberMap();
        if (members.containsKey(memberId))
            return members.get(memberId);
        return null;
    }
}
