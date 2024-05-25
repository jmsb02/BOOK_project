package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.Book;
import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Transactional
    public void saveOrderBook(OrderBook orderBook) {
        OrderBook existingBook = orderBookRepository.findByIsbn(orderBook.getIsbn());
        if (existingBook != null) {
            //이미 주문 존재할 경우 오류 처리
            log.error("주문이 중복되었습니다, 해당 주문의 isbn 값 : {}", orderBook.getIsbn());
            throw new DuplicateOrderException("주문이 중복되었습니다. 다시 한 번 확인해주세요.");
        } else {
            log.info("상품을 저장합니다");
            orderBookRepository.save(orderBook);
        }
    }

    @Transactional
    public void deleteBook(String isbn) {
        OrderBook orderBook = orderBookRepository.findByIsbn(isbn);
        if (orderBook != null) {
            orderBookRepository.delete(orderBook);
        } else {
            throw new EntityNotFoundException("삭제할 책이 존재하지 않습니다.");
        }
    }

    @Transactional
    public void processOrderBook(String isbn) {

        Book book = bookRepository.findByIsbn(isbn);
        log.info("book.isbn = {}", book.getIsbn());
        if (book == null) {
            throw new NoSuchElementException("ISBN에 해당하는 책이 존재하지 않습니다.");
        }

        OrderBook orderBook = getOrderBook(book);

        if (orderBookRepository.findByIsbn(isbn) != null) {
            throw new DuplicateOrderException("이미 주문하기 화면에 해당 상품이 존재합니다.");
        }

        try {
            saveOrderBook(orderBook);
            bookService.deleteBook(book.getIsbn());
        } catch (Exception e) {
            log.error("주문 저장을 실패하였습니다");
            throw new IllegalStateException(e.getMessage());
        }
    }


    public int calculateTotalOrderAmount(List<OrderBook> orderBooks) {
        int totalOrderBookAmount = 0;
        for (OrderBook orderBook : orderBooks) {
            totalOrderBookAmount += orderBook.getPrice();
        }
        return totalOrderBookAmount;
    }

    public OrderBook getOrderBook(Book book) {
        OrderBook orderBook = new OrderBook();
        orderBook.setName(book.getTitle());
        orderBook.setPublisher(book.getPublisher());
        orderBook.setPrice(book.getPrice());
        orderBook.setAuthor(book.getAuthor());
        orderBook.setImage(book.getImage());
        orderBook.setLink(book.getLink());
        orderBook.setIsbn(book.getIsbn());
        orderBook.setCount(1); // 주문 수량은 1로 가정
        orderBook.setPubdate(book.getPubdate());
        return orderBook;
    }
}
