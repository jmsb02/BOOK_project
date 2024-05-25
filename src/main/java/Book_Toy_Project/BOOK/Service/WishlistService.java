package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Entity.Wishlist;
import Book_Toy_Project.BOOK.Exception.DuplicateIsbnException;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Exception.DuplicateWishlistException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
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
    private final OrderBookRepository orderBookRepository;

    private final BookRepository bookRepository;

    @Transactional
    public void saveWishlist(Wishlist wishlist) {

        Wishlist savedWishlist = wishlistRepository.findByIsbn(wishlist.getIsbn());

        if (savedWishlist != null) {
            log.error("해당 상품은 이미 찜 목록에 존재합니다 isbn = {}", wishlist.getIsbn());
            throw new DuplicateWishlistException("해당 상품은 이미 찜 목록에 존재합니다. 다시 한 번 확인해주세요.");
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
        } else {
            throw new EntityNotFoundException("상품이 존재하지 않습니다. 다시 한 번 확인해주세요.");
        }
    }

    @Transactional
    public void saveWishlistToOrderBookAndDeleteWishlist(String isbn) {
        Wishlist wishlist = getWishlistByIsbn(isbn);
        checkIfOrderBookAlreadyExists(wishlist.getIsbn());
        OrderBook orderBookbyWishlist = getOrderBookbyWishlist(wishlist);
        saveOrderBook(orderBookbyWishlist);
        deleteWishlist(isbn);
    }

    //wishlist.isbn을 통해 해당 상품이 이미 주문하기 화면에 존재하는지 확인하는 메서드
    private void checkIfOrderBookAlreadyExists(String isbn) {
        OrderBook orderBookbyIsbn = orderBookRepository.findByIsbn(isbn);
        if (orderBookbyIsbn != null) {
            throw new DuplicateOrderException("이미 주문하기 화면에 해당 상품이 존재합니다.");
        }
    }

    //wishlist 추출 후 값 있는지 확인 하는 검증 메서드
    private Wishlist getWishlistByIsbn(String isbn) {
        Wishlist wishlist = wishlistRepository.findByIsbn(isbn);
        if (wishlist == null) {
            throw new IllegalStateException("해당 isbn에 해당하는 책이 존재하지 않습니다.");
        }
        return wishlist;
    }


    private void saveOrderBook(OrderBook orderBook) {
        orderBookRepository.save(orderBook);
    }

    @Transactional
    public void addFromBasketToWishlist(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        log.info("book = {}", book);

        //책이 존재하지 않을 경우 예외 처리
        if (book == null) {
            log.error("해당 책이 존재하지 않습니다.");
            throw new EntityNotFoundException("해당 책이 존재하지 않습니다");
        }

        //찜 목록에 해당 책이 이미 존재하는지 확인
        Wishlist wishlistByIsbn = wishlistRepository.findByIsbn(book.getIsbn());
        if (wishlistByIsbn != null) {
            log.error("해당 책이 이미 찜 목록에 존재합니다.");
            throw new DuplicateIsbnException("이미 찜 목록에 해당 상품이 존재합니다.");
        }

        Wishlist wishlistBook = convertToEntityWishlistBook(book);
        log.info("wishlistBook = {}", wishlistBook);

        wishlistRepository.save(wishlistBook);
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
        wishlist.setCount(1); //주문 수량은 1로 가정
        return wishlist;
    }

    public static OrderBook getOrderBookbyWishlist(Wishlist wishlist) {
        OrderBook orderBook = new OrderBook();
        orderBook.setName(wishlist.getName());
        orderBook.setPublisher(wishlist.getPublisher());
        orderBook.setPrice(wishlist.getPrice());
        orderBook.setAuthor(wishlist.getAuthor());
        orderBook.setImage(wishlist.getImage());
        orderBook.setLink(wishlist.getLink());
        orderBook.setIsbn(wishlist.getIsbn());
        orderBook.setCount(1); // 주문 수량은 1로 가정
        orderBook.setPubdate(wishlist.getPubdate());
        return orderBook;
    }

}
