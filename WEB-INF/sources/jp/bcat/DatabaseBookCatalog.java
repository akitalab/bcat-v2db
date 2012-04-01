package jp.bcat;
import java.sql.*;
import java.util.Vector;

public class DatabaseBookCatalog extends BookCatalog {
    Connection connection;
    static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1/bcat";
    static final String DATABASE_USER = "bcat";
    static final String DATABASE_PASSWORD = "secret";

    public DatabaseBookCatalog() {
        connect();
    }

    protected boolean connect() {
        try {
            if (connection != null) {
                if (connection.getWarnings() == null)
                    return true;
                disconnect();
            }
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            if (connection.getWarnings() == null)
                return true;
            disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    protected void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            connection = null;
        }
    }

    public Book getBook(String bookId) {
        Book book = null;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bookcatalog WHERE bookid = ?");
            statement.setString(1, bookId);
            ResultSet result = statement.executeQuery();
            if (result.next()) 
                book = createBook(result);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return book;
    }

    public Book[] getBooks() {
        Book [] bookArray = null;
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM bookcatalog ORDER BY datacreateddate DESC");
            bookArray = createBooks(result);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookArray;
    }

    public synchronized Book addBook(Book book) {
        try {
            connect();
            String bookId = book.getBookId();
            if (bookId == null || bookId.length() == 0 || getBook(bookId) != null)
                bookId = createUniqueBookId();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bookcatalog (bookid,title,author,translator,publisher,publicationdate,code,status,keyword,memo,datacreator,datacreateddate) VALUES (?,?,?,?,?,to_date(?,'YYYY-MM-DD'),?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))");
            statement.setString(1, bookId);
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getTranslator());
            statement.setString(5, book.getPublisher());
            statement.setString(6, book.getPublicationDate());
            statement.setString(7, book.getCode());
            statement.setString(8, book.getStatus());
            statement.setString(9, book.getKeyword());
            statement.setString(10, book.getMemo());
            statement.setString(11, book.getDataCreator());
            statement.setString(12, book.getDataCreatedDate());
            int result = statement.executeUpdate();
            statement.close();
            return getBook(bookId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteBook(String bookId) {
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM bookcatalog WHERE bookid = ?");
            statement.setString(1, bookId);
            int result = statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
    public Book[] searchBooks(String word) {
        Book [] bookArray = null;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bookcatalog WHERE bookid LIKE ? OR UPPER(title) LIKE ? OR UPPER(author) LIKE ? OR UPPER(translator) LIKE ? OR UPPER(publisher) LIKE ? OR UPPER(code) LIKE ? OR UPPER(status) LIKE ? OR UPPER(keyword) LIKE ? OR UPPER(memo) LIKE ? OR UPPER(datacreator) LIKE ? ORDER BY datacreateddate DESC");
            String pattern = "%" + word.toUpperCase() + "%";
            for (int i = 1; i <= 10; i++)
            		statement.setString(i, pattern);
            ResultSet result = statement.executeQuery();
            bookArray = createBooks(result);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookArray;
    }

    protected Book createBook(ResultSet result) throws SQLException {
        Book book = new Book();
        book.setBookId(result.getString("bookid"));
        book.setTitle(result.getString("title"));
        book.setAuthor(result.getString("author"));
        book.setTranslator(result.getString("translator"));
        book.setPublicationDate(result.getTimestamp("publicationdate").toString().substring(0,10));
        book.setPublisher(result.getString("publisher"));
        book.setCode(result.getString("code"));
        book.setStatus(result.getString("status"));
        book.setKeyword(result.getString("keyword"));
        book.setMemo(result.getString("memo"));
        book.setDataCreatedDate(result.getTimestamp("datacreateddate").toString().substring(0,16));
        book.setDataCreator(result.getString("datacreator"));
        return book;
    }

    protected Book [] createBooks(ResultSet result) throws SQLException {
        Vector books = new Vector();
        while (result.next()) 
            books.add(createBook(result));
        Book [] bookArray = new Book[books.size()];
        books.toArray(bookArray);
        return bookArray;
    }
}
