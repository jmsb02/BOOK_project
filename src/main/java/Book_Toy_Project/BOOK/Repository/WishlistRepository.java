package Book_Toy_Project.BOOK.Repository;

import Book_Toy_Project.BOOK.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("select w from Wishlist w where w.isbn = :isbn")
    public Wishlist findByIsbn(@Param("isbn") String isbn);

}