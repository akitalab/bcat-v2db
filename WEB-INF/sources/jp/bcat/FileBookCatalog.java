package jp.bcat;
import java.io.*;
import java.util.*;

public class FileBookCatalog extends BookCatalog {
	static final String CATALOG_FILE_NAME = "books.ser";
	protected Hashtable books = new Hashtable();

	public FileBookCatalog() {
		load();
	}

	protected synchronized void load() {
		try {
			ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(CATALOG_FILE_NAME));
			books = (Hashtable)in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			// ファイルがない場合
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected synchronized void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(CATALOG_FILE_NAME));
			out.writeObject(books);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Book getBook(String no) {
		return (Book)books.get(no);
	}

	public Book[] getBooks() {
		Book resultArray[] = new Book[books.size()];
		books.values().toArray(resultArray);
		return resultArray;
	}

	public synchronized Book addBook(Book book) {
		String id = book.getBookId();
		if (id == null || books.containsKey(id)) {
			id = createUniqueBookId();
			book.setBookId(id);
		}
		books.put(id, book);
		save();
		return book;
	}

	public void deleteBook(String no) {
		books.remove(no);
		save();
	}

	public Book[] searchBooks(String word) {
		Vector result = new Vector();
		Iterator i = books.values().iterator();
		while (i.hasNext()) {
			Book book = (Book)i.next();
			if (book.getBookId().indexOf(word) != -1
			 || book.getTitle().indexOf(word) != -1
			 || book.getAuthor().indexOf(word) != -1
			 || book.getTranslator().indexOf(word) != -1
			 || book.getPublisher().indexOf(word) != -1
			 || book.getKeyword().indexOf(word) != -1
			 || book.getMemo().indexOf(word) != -1) {
				result.add(book);
			}
		}
		Book resultArray[] = new Book[result.size()];
		result.toArray(resultArray);
		return resultArray;
	}
}
