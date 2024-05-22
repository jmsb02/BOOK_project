package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long> {


    Book findByIsbn(String isbn);

    void deleteByIsbn(String isbn);

    @Query("select case when count(b) > 0 THEN true else false end" +
            " from Book b where b.isbn =: isbn")
    boolean checkDuplicatefindByIsbn(String isbn);

}
