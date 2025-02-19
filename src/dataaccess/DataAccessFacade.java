package dataaccess;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DataAccessFacade implements DataAccess {

    // For Mac Users path can use /
    public static final String OUTPUT_DIR = getDir();

    private static String getDir() {
        String dir = System.getProperty("user.dir");
        String os = System.getProperty("os.name");;
        switch (os){
            case "Windows 11":
                dir += "\\src\\dataaccess\\storage";
                break;
            default:
                dir +="/src/dataaccess/storage";
                break;
        }
        System.out.println(dir);
        return dir;
    }
    // Windows user can use

    /*
     * public static final String OUTPUT_DIR = System.getProperty("user.dir")
     * + "\\src\\dataaccess\\storage";
     */
    public static final String DATE_PATTERN = "MM/dd/yyyy";

    static void loadBookMap(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(StorageType.BOOKS, books);
    }
    static void loadCheckoutMap(List<CheckoutRecord> bookList) {
        HashMap<String, CheckoutRecord> books = new HashMap<String, CheckoutRecord>();
        bookList.forEach(book -> books.put(book.getRecordId(), book));
        saveToStorage(StorageType.RECORDS, books);
    }

    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    static void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
//            System.out.println(retVal);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

    // implement: other save operations
    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);
    }

    public void saveNewBook(Book book) {
        HashMap<String, Book> books = readBooksMap();
        String bookIsbn = book.getIsbn();
        books.put(bookIsbn, book);
        saveToStorage(StorageType.BOOKS, books);
    }
    ///// load methods - these place test data into the storage area
    ///// - used just once at startup

    @Override
    public void saveNewCheckoutRecord(CheckoutRecord record) {
        HashMap<String, CheckoutRecord> records = readUserRecords();
        if (records == null)
            records = new HashMap<>();

        String recordId = record.getRecordId();
        records.put(recordId, record);
        saveToStorage(StorageType.RECORDS, records);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, CheckoutRecord> readUserRecords() {
        // Returns a Map with name/value pairs being
        HashMap<String, CheckoutRecord> map = (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.RECORDS);
        if (map == null)
            map = new HashMap<>();
        return map;
    }

    @Override
    public void saveAndUpdateBook(Book book) {
        HashMap<String, Book> books = readBooksMap();
        if (books == null)
            books = new HashMap<>();

        String bookIsbn = book.getIsbn();
        books.put(bookIsbn, book);
        saveToStorage(StorageType.BOOKS, books);
    }

    @Override
    public List<CheckoutRecord> readUserRecords(LibraryMember member) {
        HashMap<String, CheckoutRecord> records = readUserRecords();
        List<CheckoutRecord> userRecords = new ArrayList<>();
        records.forEach((k, v) -> {
            if (v.getLibraryMember().getMemberId().equals(member.getMemberId()))
                userRecords.add(v);
        });
        Collections.sort(userRecords);
        return userRecords;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        // Returns a Map with name/value pairs being
        // isbn -> Book
        return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        // Returns a Map with name/value pairs being
        // memberId -> LibraryMember
        return (HashMap<String, LibraryMember>) readFromStorage(
                StorageType.MEMBERS);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        // Returns a Map with name/value pairs being
        // userId -> User
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    enum StorageType {
        BOOKS, MEMBERS, USERS, RECORDS;
    }

    final static class Pair<S, T> implements Serializable {

        private static final long serialVersionUID = 5399827794066637059L;
        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null)
                return false;
            if (this == ob)
                return true;
            if (ob.getClass() != getClass())
                return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }
    }

    @Override
    public CheckoutRecord readBookCopyRecords(BookCopy copy) {
        HashMap<String, CheckoutRecord> records = readUserRecords();
        CheckoutRecord result = null;
        for (CheckoutRecord record : records.values()) {
            if (record.getBookCopy().equals(copy) && record.getDueDate().isBefore(LocalDateTime.now()))
                result = record;
        }
        return result;
    }

}
