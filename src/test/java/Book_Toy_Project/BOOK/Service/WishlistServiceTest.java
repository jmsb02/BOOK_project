package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.Wishlist;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WishlistServiceTest {

    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishlistRepository wishlistRepository;

    @Test
    public void saveWishlist_Success() {

        //given
        Wishlist wishlist = new Wishlist();
        wishlist.setIsbn("1234");

        //when
        wishlistService.saveWishlist(wishlist);

        //then
        assertThat(wishlistRepository.findByIsbn(wishlist.getIsbn())).isNotNull();
    }

    @Test
    public void saveWishlist_Fail() {

        //given
        Wishlist wishlist = new Wishlist();
        wishlist.setIsbn("1234");
        wishlistRepository.save(wishlist);

        //when
        Wishlist findWishlist = wishlistRepository.findByIsbn(wishlist.getIsbn());

        //then
        assertThatThrownBy(() -> wishlistService.saveWishlist(findWishlist))
                .isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    public void deleteWishlist_Success() {

        //given
        Wishlist wishlist = new Wishlist();
        wishlist.setIsbn("1234");
        wishlistRepository.save(wishlist);

        //when
        wishlistService.deleteWishlist(wishlist.getIsbn());

        //then
        assertThat(wishlistRepository.findByIsbn(wishlist.getIsbn())).isNull();
    }

    @Test
    public void deleteWishlist_Fail() {
        //given, when
        Wishlist wishlist = new Wishlist();
        wishlist.setIsbn("1234");

        //then
        assertThatThrownBy(() -> wishlistService.deleteWishlist(wishlist.getIsbn()))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    public void convertToEntityWishlistBook() {
        //given
        Book book = new Book();

        book.setTitle("title");
        book.setIsbn("1234");
        book.setAuthor("author");
        book.setPublisher("publisher");
        book.setImage("image");
        book.setPubdate("pubdate");
        book.setPrice(1);
        book.setLink("link");

        //when
        Wishlist wishlist = WishlistService.convertToEntityWishlistBook(book);

        //then
        assertThat(wishlist.getName()).isEqualTo("title");
        assertThat(wishlist.getIsbn()).isEqualTo("1234");
        assertThat(wishlist.getAuthor()).isEqualTo("author");
        assertThat(wishlist.getPublisher()).isEqualTo("publisher");
        assertThat(wishlist.getImage()).isEqualTo("image");
        assertThat(wishlist.getPubdate()).isEqualTo("pubdate");
        assertThat(wishlist.getPrice()).isEqualTo(1);
        assertThat(wishlist.getLink()).isEqualTo("link");

    }
}