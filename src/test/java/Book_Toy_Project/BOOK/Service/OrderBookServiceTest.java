package Book_Toy_Project.BOOK.Service;

import Book_Toy_Project.BOOK.Entity.OrderBook;
import Book_Toy_Project.BOOK.Exception.DuplicateOrderException;
import Book_Toy_Project.BOOK.Repository.BookRepository;
import Book_Toy_Project.BOOK.Repository.OrderBookRepository;
import Book_Toy_Project.BOOK.Repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderBookServiceTest {

    @Autowired OrderBookRepository orderBookRepository;
    @Autowired OrderBookService orderBookService;
    @Autowired BookRepository bookRepository;
    @Autowired WishlistRepository wishlistRepository;

    @Test
    public void saveOrderBook_Success() {
        //given
        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");

        //when
        orderBookService.saveOrderBook(orderBook); //상품 저장

        //then
        OrderBook findOrder = orderBookRepository.findByIsbn(orderBook.getIsbn());
        assertThat(orderBook).isEqualTo(findOrder);
    }

    @Test
    public void saveOrderBook_Fail() {

        //given
        OrderBook orderBook1 = new OrderBook();
        orderBook1.setIsbn("1234");
        orderBookService.saveOrderBook(orderBook1);

        OrderBook orderBook2 = new OrderBook();
        orderBook2.setIsbn("1234");

        //when, then
        assertThatThrownBy(() -> orderBookService.saveOrderBook(orderBook2))
                .isInstanceOf(DuplicateOrderException.class);
    }

    @Test
    public void deleteBook_Success() {

        //given
        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");

        //when
        orderBookRepository.save(orderBook);
        orderBookService.deleteBook(orderBook.getIsbn());

        //then
        assertThat(orderBookRepository.findByIsbn(orderBook.getIsbn())).isNull();
    }

    @Test
    public void deleteBook_Fail() {

        //given, when
        OrderBook orderBook = new OrderBook();
        orderBook.setIsbn("1234");

        //then
        assertThatThrownBy(() ->  orderBookService.deleteBook(orderBook.getIsbn()))
                .isInstanceOf(EntityNotFoundException.class);
    }



    @Test
    public void calculateTotalOrderAmount() {

        //given
        OrderBook orderBook1 = new OrderBook();
        orderBook1.setPrice(1);
        OrderBook orderBook2 = new OrderBook();
        orderBook2.setPrice(2);
        OrderBook orderBook3 = new OrderBook();
        orderBook3.setPrice(3);
        OrderBook orderBook4 = new OrderBook();
        orderBook4.setPrice(4);

        List<OrderBook> orderBooks = new ArrayList<>();
        orderBooks.add(orderBook1);
        orderBooks.add(orderBook2);
        orderBooks.add(orderBook3);
        orderBooks.add(orderBook4);

        //when
        int total = orderBookService.calculateTotalOrderAmount(orderBooks);

        //then
        assertThat(total).isEqualTo(10);

    }

}