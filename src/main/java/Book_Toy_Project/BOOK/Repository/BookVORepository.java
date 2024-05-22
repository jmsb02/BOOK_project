package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.api.BookVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVORepository extends JpaRepository<BookVO, Long> {

}
