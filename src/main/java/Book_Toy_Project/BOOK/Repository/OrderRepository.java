package Book_Toy_Project.BOOK.Repository;


import Book_Toy_Project.BOOK.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByIsbn(String isbn);
}
