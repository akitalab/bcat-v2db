package jp.bcat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class BookCatalog {
	public abstract Book getBook(String no);
	public abstract Book[] getBooks();
	public abstract Book addBook(Book book);
	public abstract void deleteBook(String bookId);
	public abstract Book[] searchBooks(String word);

	protected String createUniqueBookId() {
		Date now = new Date();
		String id;
		id = new SimpleDateFormat("yyyyMMddHH").format(now);
		while (getBook(id) != null) {
			int intId = Integer.parseInt(id);
			id = Integer.toString(intId + 1);
		}
		return id;
	}

	static BookCatalog sharedInstance;
	static public BookCatalog getInstance() {
		if (sharedInstance == null) {
			sharedInstance = new DatabaseBookCatalog();
		}
		return sharedInstance;
	}
}