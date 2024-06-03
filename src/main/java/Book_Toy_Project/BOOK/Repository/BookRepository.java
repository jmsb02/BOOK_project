package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long> {


    Book findByIsbn(String isbn);

    void deleteByIsbn(String isbn);


}
