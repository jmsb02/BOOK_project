package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.Wishlist;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Transactional
    public void saveWishlist(Wishlist wishlist) {

            Wishlist savedWishlist = wishlistRepository.findByIsbn(wishlist.getIsbn());

            if (savedWishlist != null) {
                log.error("해당 상품은 이미 찜 목록에 존재합니다 isbn = {}", wishlist.getIsbn());
                throw new DuplicateIsbnException("해당 상품은 이미 찜 목록에 존재합니다. 다시 한 번 확인해주세요.");
            } else {
                log.info("상품을 저장합니다");
                wishlistRepository.save(wishlist);
            }
        }

    @Transactional
    public void deleteWishlist(String isbn) {
        Wishlist wishlist = wishlistRepository.findByIsbn(isbn);
        if (wishlist != null) {
            wishlistRepository.delete(wishlist);
        }else {
            throw new EntityNotFoundException("상품이 존재하지 않습니다. 다시 한 번 확인해주세요.");
        }
    }

    public static Wishlist convertToEntityWishlistBook(Book book) {
        Wishlist wishlist = new Wishlist();
        wishlist.setName(book.getTitle());
        wishlist.setIsbn(book.getIsbn());
        wishlist.setAuthor(book.getAuthor());
        wishlist.setPublisher(book.getPublisher());
        wishlist.setImage(book.getImage());
        wishlist.setPubdate(book.getPubdate());
        wishlist.setPrice(book.getPrice());
        wishlist.setLink(book.getLink());

        return wishlist;
    }

}
