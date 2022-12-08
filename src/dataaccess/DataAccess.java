package dataaccess;

import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;

import java.util.HashMap;
import java.util.List;

public interface DataAccess {
    public HashMap<String, Book> readBooksMap();

    public void saveNewBook(Book book);

    public HashMap<String, User> readUserMap();

    public HashMap<String, LibraryMember> readMemberMap();

    public void saveNewMember(LibraryMember member);

    void saveNewCheckoutRecord(CheckoutRecord record);

    public HashMap<String, CheckoutRecord> readUserRecords();

    void saveAndUpdateBook(Book book);

    List<CheckoutRecord> readUserRecords(LibraryMember member);
}
