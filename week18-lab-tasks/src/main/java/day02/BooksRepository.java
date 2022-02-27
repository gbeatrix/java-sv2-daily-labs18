package day02;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class BooksRepository {
    private JdbcTemplate jdbcTemplate;

    public BooksRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertBook(String author, String title, int price, int pieces){
        jdbcTemplate.update("INSERT INTO books (author, title, price, pieces) VALUES (?, ?, ?, ?)", author, title, price, pieces);
    }

    public List<Book> findBooksByWriter(String prefix) {
        return jdbcTemplate.query("SELECT * FROM books WHERE author LIKE ?",
                (rs, rowNum) -> new Book(rs.getLong("id"), rs.getString("author"), rs.getString("title"), rs.getInt("price"), rs.getInt("pieces")),
                prefix+"%");
    }

    public void updatePieces(Long id, int pieces){
        jdbcTemplate.update("UPDATE books SET pieces = pieces+? WHERE id = ?", pieces, id);
    }
}
