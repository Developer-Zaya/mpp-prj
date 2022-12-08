package business;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckoutRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 2341690276685962829L;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    private LibraryMember libraryMember;
    private BookCopy bookCopy;

    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private String recordId;

    public CheckoutRecord(LibraryMember member, BookCopy copy) {
        libraryMember = member;
        bookCopy = copy;
        checkoutDate = LocalDateTime.now();
        dueDate = checkoutDate.plusDays(copy.getBook().getMaxCheckoutLength());
        recordId = member.getMemberId() + "_" +
                bookCopy.getBook().getIsbn() + "_" +
                bookCopy.getCopyNum() + "_" +
                DATE_TIME_FORMATTER.format(checkoutDate);
        copy.changeAvailability();
        System.out.println("Record Saved: " + recordId);
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }
}
